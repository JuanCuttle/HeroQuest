package services;

import entities.Creature;
import entities.Position;
import entities.actions.OpenDoor;
import entities.players.PlayableCharacter;
import entities.tiles.Door;
import entities.utils.Strings;

import java.util.ArrayList;
import java.util.Objects;

public class OpenDoorService {

    private final HeroQuest heroQuest;

    public OpenDoorService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }


    public String openDoor(int doorId) {
        Creature creature = heroQuest.getCurrentCreature();
        if (creature instanceof PlayableCharacter) {
            boolean isItTheCurrentPlayersTurn = heroQuest.verifyIfItIsCurrentPlayersTurn();
            if (isItTheCurrentPlayersTurn) {
                Door door = this.getDoorById(doorId);
                boolean isDoorReachable = verifyIfPlayerIsNearDoor((PlayableCharacter) creature, Objects.requireNonNull(door));
                boolean isDoorHidden = door.isSecret();
                if (!isDoorHidden) {
                    if (isDoorReachable) {
                        OpenDoor action = new OpenDoor();
                        action.setDoorId(doorId);
                        heroQuest.processAction(action);
                        heroQuest.sendAction(action);
                    } else {
                        return Strings.DOOR_OUT_OF_RANGE.toString();
                    }
                }
            } else {
                return Strings.NOT_YOUR_TURN.toString();
            }
        } else {
            return Strings.CANT_OPEN_DOOR.toString();
        }
        return null;
    }

    public Door getDoorById(int doorId) {
        for (Door door : heroQuest.getDoors()) {
            if (door.getID() == doorId)
                return door;
        }
        return null;
    }

    public boolean verifyIfPlayerIsNearDoor(PlayableCharacter hero, Door door) {
        byte heroRow = hero.getCurrentPosition().getRow();
        byte heroColumn = hero.getCurrentPosition().getColumn();
        byte doorRow = door.getRow();
        byte doorColumn = door.getColumn();
        return (heroRow == doorRow && heroColumn == doorColumn - 1)
                || (heroRow == doorRow && heroColumn == doorColumn + 1)
                || (heroColumn == doorColumn && heroRow == doorRow - 1)
                || (heroColumn == doorColumn && heroRow == doorRow + 1);
    }

    public String openDoorWithKeyboard() {
        Creature creature = heroQuest.getCurrentCreature();
        if (creature instanceof PlayableCharacter) {
            boolean isItTheCurrentPlayersTurn = heroQuest.verifyIfItIsCurrentPlayersTurn();
            if (isItTheCurrentPlayersTurn) {
                byte creatureRow = creature.getCurrentPosition().getRow();
                byte creatureColumn = creature.getCurrentPosition().getColumn();

                Position northTile = heroQuest.getPosition((byte)(creatureRow-1), creatureColumn);
                Position eastTile = heroQuest.getPosition(creatureRow, (byte)(creatureColumn+1));
                Position southTile = heroQuest.getPosition((byte)(creatureRow+1), creatureColumn);
                Position westTile = heroQuest.getPosition(creatureRow, (byte)(creatureColumn-1));

                ArrayList<String> directionsWithOpenableDoors = new ArrayList<>();
                ArrayList<String> openableDoorIds = new ArrayList<>();
                String doorId;

                if (northTile instanceof Door) {
                    if (!((Door) northTile).isSecret()) {
                        doorId = "" + northTile.getRow() + northTile.getColumn();
                        openableDoorIds.add(doorId);
                        directionsWithOpenableDoors.add(Strings.NORTH.toString());
                    }
                } if (eastTile instanceof Door) {
                    if (!((Door) eastTile).isSecret()) {
                        doorId = "" + eastTile.getRow() + eastTile.getColumn();
                        openableDoorIds.add(doorId);
                        directionsWithOpenableDoors.add(Strings.EAST.toString());
                    }
                } if (southTile instanceof Door) {
                    if (!((Door) southTile).isSecret()) {
                        doorId = "" + southTile.getRow() + southTile.getColumn();
                        openableDoorIds.add(doorId);
                        directionsWithOpenableDoors.add(Strings.SOUTH.toString());
                    }
                } if (westTile instanceof Door) {
                    if (!((Door) westTile).isSecret()) {
                        doorId = "" + westTile.getRow() + westTile.getColumn();
                        openableDoorIds.add(doorId);
                        directionsWithOpenableDoors.add(Strings.WEST.toString());
                    }
                }

                if (!directionsWithOpenableDoors.isEmpty()) {

                    int chosenDoorDirection = heroQuest.selectDoorToOpenOrClose(directionsWithOpenableDoors);
                    int selectedDoorId = Integer.parseInt(openableDoorIds.get(chosenDoorDirection));

                    OpenDoor action = new OpenDoor();
                    action.setDoorId(selectedDoorId);
                    heroQuest.processAction(action);
                    heroQuest.sendAction(action);
                } else {
                    return Strings.DOOR_OUT_OF_RANGE.toString();
                }
            } else {
                return Strings.NOT_YOUR_TURN.toString();
            }
        } else {
            return Strings.CANT_OPEN_DOOR.toString();
        }
        return null;
    }

    public void processOpenDoor(OpenDoor action) {
        int doorId = action.getDoorId();
        Door door = this.getDoorById(doorId);
        if (door.isOpen()) {
            door.closeDoor();
        } else {
            door.openDoor();
        }
    }
}
