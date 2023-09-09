package services;

import entities.Creature;
import entities.Position;
import entities.actions.EndTurn;
import entities.actions.Movement;
import entities.tiles.*;
import entities.utils.Strings;
import enums.DirectionEnum;
import enums.StatusEnum;
import enums.TrapEvasionMovementEnum;
import exceptions.PositionNotEmptyException;

import static enums.TrapEvasionMovementEnum.FALLEN_INTO_PIT;

public class MovementService {

    private HeroQuest heroQuest;
    private EndTurnService endTurnService;

    public MovementService(HeroQuest heroQuest, EndTurnService endTurnService) {
        this.heroQuest = heroQuest;
        this.endTurnService = endTurnService;
    }

    public String move(DirectionEnum direction) {
        if (heroQuest.verifyIfItIsCurrentPlayersTurn()) {
            Creature creature = heroQuest.getCurrentCreature();

            if (StatusEnum.SLEEPING.equals(creature.getStatus())) {
                return Strings.SLEEP_FREEZE.toString();
            } else {
                byte remainingMovement = creature.getMovement();
                if (remainingMovement > 0) {
                    Position currentPosition = creature.getCurrentPosition();
                    byte row = currentPosition.getRow();
                    byte column = currentPosition.getColumn();
                    Position newPosition;
                    try {
                        newPosition = getNewPosition(direction, row, column);

                        Movement movement = new Movement();
                        movement.setSourceRow(row);
                        movement.setSourceColumn(column);
                        movement.setDestinationRow(newPosition.getRow());
                        movement.setDestinationColumn(newPosition.getColumn());

                        Trap trap = newPosition.getTrap();

                        if (trap != null) {
                            byte trapDamage = trap.getDeliveredDamage();
                            movement.setDamage(trapDamage);

                            if (trap instanceof FallingRock) {
                                TrapEvasionMovementEnum evasionOption = heroQuest.showFallingRockMovementOptions();

                                switch(direction){
                                    case UP: {
                                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), movement.getDestinationColumn());
                                        }
                                        break;
                                    }
                                    case DOWN: {
                                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), movement.getDestinationColumn());
                                        }
                                        break;
                                    }
                                    case LEFT: {
                                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                            newPosition = heroQuest.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                                        }
                                        break;
                                    }
                                    default: {
                                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
                                            newPosition = heroQuest.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                                        }
                                        break;
                                    }
                                }
                                if (newPosition instanceof Wall) {
                                    evasionOption = TrapEvasionMovementEnum.BACKWARD;
                                }
                                movement.setDirection(direction);
                                movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(evasionOption));

                            } else if (trap instanceof Pit) {

                                if (trap.getVisible()) {

                                    TrapEvasionMovementEnum evasionOption = heroQuest.showPitJumpingOptions();

                                    switch(direction) {
                                        case UP: {
                                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), movement.getDestinationColumn());
                                            }
                                            break;
                                        }
                                        case DOWN: {
                                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), movement.getDestinationColumn());
                                            }
                                            break;
                                        }
                                        case LEFT: {
                                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                                newPosition = heroQuest.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                                            }
                                            break;
                                        }
                                        default: {
                                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                                newPosition = heroQuest.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                                            }
                                            break;
                                        }
                                    }
                                    if (newPosition instanceof Wall) {
                                        evasionOption = TrapEvasionMovementEnum.BACKWARD;
                                    }
                                    movement.setDirection(direction);
                                    movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(evasionOption));

                                } else {
                                    movement.setDirection(direction);
                                    movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(FALLEN_INTO_PIT));
                                }
                            }
                        }
                        heroQuest.processAction(movement);
                        heroQuest.sendAction(movement);
                    } catch (PositionNotEmptyException e) {
                        return Strings.PHYSICS_LAWS.toString();
                    }
                } else {
                    return Strings.NO_MOVE_LEFT.toString();
                }
            }
        } else {
            return Strings.NOT_YOUR_TURN.toString();
        }
        return null;
    }

    public Position getNewPosition(DirectionEnum direction, byte row, byte column) throws PositionNotEmptyException {
        Position newPosition = null;
        switch (direction) {
            case DOWN:
                newPosition = heroQuest.getPosition((byte) (row + 1), column);
                break;
            case UP:
                newPosition = heroQuest.getPosition((byte) (row - 1), column);
                break;
            case LEFT:
                newPosition = heroQuest.getPosition(row, (byte) (column - 1));
                break;
            case RIGHT:
                newPosition = heroQuest.getPosition(row, (byte) (column + 1));
                break;
        }
        if (newPosition.getCreature() != null
                || newPosition instanceof Wall
                || newPosition instanceof Furniture
                || isNewPositionAClosedDoor(newPosition)
                || doesNewPositionHaveATriggeredFallingRock(newPosition)) {
            throw new PositionNotEmptyException();
        } else {
            return newPosition;
        }
    }

    public boolean doesNewPositionHaveATriggeredFallingRock(Position newPosition) {
        return newPosition.getTrap() != null && newPosition.getTrap() instanceof FallingRock && newPosition.getTrap().getTriggered();
    }

    public boolean isNewPositionAClosedDoor(Position newPosition) {
        return newPosition instanceof Door && !((Door) newPosition).isOpen();
    }

    public void processMovement(Movement action) {
        Creature creature = heroQuest.getCurrentCreature();
        byte creatureCurrentBody;
        byte newCreatureRow;
        byte newCreatureColumn;

        Position currentPosition = heroQuest.getPosition(action.getSourceRow(), action.getSourceColumn());
        Position newPosition = heroQuest.getPosition(action.getDestinationRow(), action.getDestinationColumn());

        currentPosition.removeCreature();
        heroQuest.updatePosition(currentPosition);
        creature.decreaseMovement();

        newPosition.setCreature(creature);
        creature.setCurrentPosition(newPosition);
        heroQuest.updatePosition(newPosition);

        heroQuest.setAreaVisible(newPosition.getRow(), newPosition.getColumn());

        byte damageTaken = action.getDamage();
        if (newPosition.getTrap() != null) {
            Trap trap = newPosition.getTrap();

            if (!trap.getTriggered()) {
                creature.decreaseBody(damageTaken);
                heroQuest.showTrapActivationMessage(damageTaken, creature);
                trap.makeTrapVisible();
                trap.makeTrapTriggered();
            }

            if (trap instanceof Pit) {
                if (!FALLEN_INTO_PIT.equals(TrapEvasionMovementEnum.getEnumById(action.getTrapEvasionMovementId()))) {
                    newPosition.removeCreature();

                    TrapEvasionMovementEnum evasionOption = TrapEvasionMovementEnum.getEnumById(action.getTrapEvasionMovementId());
                    switch(action.getDirection()) {
                        case UP: {
                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), action.getDestinationColumn());
                            } else {
                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), action.getDestinationColumn());
                            }
                            break;
                        }
                        case DOWN: {
                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), action.getDestinationColumn());
                            } else {
                                newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), action.getDestinationColumn());
                            }
                            break;
                        }
                        case LEFT: {
                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                            } else {
                                newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                            }
                            break;
                        }
                        default: {
                            if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                                newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                            } else {
                                newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                            }
                            break;
                        }
                    }
                    newPosition.setCreature(creature);
                    creature.setCurrentPosition(newPosition);

                    newCreatureRow = newPosition.getRow();
                    newCreatureColumn = newPosition.getColumn();
                    heroQuest.updatePosition(newPosition);

                    heroQuest.setAreaVisible(newCreatureRow, newCreatureColumn);
                }
            }

            if (trap instanceof FallingRock) {
                newPosition.removeCreature();

                TrapEvasionMovementEnum evasionOption = TrapEvasionMovementEnum.getEnumById(action.getTrapEvasionMovementId());
                switch(action.getDirection()) {
                    case UP: {
                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), action.getDestinationColumn());
                        } else {
                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), action.getDestinationColumn());
                        }
                        break;
                    }
                    case DOWN: {
                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()+1), action.getDestinationColumn());
                        } else {
                            newPosition = heroQuest.getPosition((byte) (newPosition.getRow()-1), action.getDestinationColumn());
                        }
                        break;
                    }
                    case LEFT: {
                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                            newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                        } else {
                            newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                        }
                        break;
                    }
                    default: {
                        if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
                            newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()+1));
                        } else {
                            newPosition = heroQuest.getPosition(action.getDestinationRow(), (byte) (newPosition.getColumn()-1));
                        }
                        break;
                    }
                }
                newPosition.setCreature(creature);
                creature.setCurrentPosition(newPosition);

                newCreatureRow = newPosition.getRow();
                newCreatureColumn = newPosition.getColumn();
                heroQuest.updatePosition(newPosition);

                heroQuest.setAreaVisible(newCreatureRow, newCreatureColumn);
            }

            creatureCurrentBody = creature.getBody();
            if (creatureCurrentBody <= 0) {
                creature.setStatus(StatusEnum.DEAD);
                heroQuest.announceUnfortunateDeath(creature);
                heroQuest.killCreature(creature.getID());

                endTurnService.processEndTurn();
            }
        }
    }
}
