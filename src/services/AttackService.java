package services;

import entities.Creature;
import entities.Position;
import entities.actions.Attack;
import entities.enemies.Monster;
import entities.players.PlayableCharacter;
import entities.tiles.Pit;
import entities.utils.Strings;
import enums.ItemEnum;
import enums.StatusEnum;

import java.util.ArrayList;
import java.util.Random;

public class AttackService {

    private HeroQuest heroQuest;

    public AttackService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public String attack() {
        if (heroQuest.verifyIfItIsCurrentPlayersTurn()) {
            Creature attacker = heroQuest.getCurrentCreature();

            if (StatusEnum.SLEEPING.equals(attacker.getStatus())) {
                return Strings.SLEEP_NO_ATTACK.toString();
            } else {
                Position attackerPosition = attacker.getCurrentPosition();
                boolean hasSpear = checkIfAttackerIsAHeroAndHasSpear(attacker);
                ArrayList<Creature> availableTargets = heroQuest.getAvailableTargets(1,
                        attackerPosition);
                Creature selectedTarget = heroQuest.selectTarget(availableTargets);
                Position selectedTargetPosition = selectedTarget.getCurrentPosition();
                boolean isTargetReachable = checkIfAttackerReachesTarget(
                        attackerPosition, selectedTargetPosition, hasSpear);
                if (isTargetReachable) {
                    byte damage = calculateAttackDamage(attacker, selectedTarget);
                    Attack action = new Attack();
                    action.setValue(damage);
                    action.setTargetID(selectedTarget.getID());
                    heroQuest.processAction(action);
                    heroQuest.sendAction(action);
                } else {
                    return Strings.TARGET_OUT_OF_RANGE.toString();
                }
            }
        } else {
            return Strings.NOT_YOUR_TURN.toString();
        }
        return null;
    }

    public boolean checkIfAttackerIsAHeroAndHasSpear(Creature attacker) {
        return attacker instanceof PlayableCharacter &&
                ((PlayableCharacter) attacker).getItems(heroQuest.getMap()).contains(ItemEnum.Spear);
    }

    public boolean checkIfAttackerReachesTarget(Position attackerPosition, Position targetPosition, boolean attackerHasSpear) {
        int attackerRow = attackerPosition.getRow();
        int attackerColumn = attackerPosition.getColumn();
        int targetRow = targetPosition.getRow();
        int targetColumn = targetPosition.getColumn();
        boolean result = false;
        if (attackerHasSpear) {
            // add diagonal
            result = (attackerRow == targetRow-1 && attackerColumn == targetColumn-1)
                    || (attackerRow == targetRow+1 && attackerColumn == targetColumn+1)
                    || (attackerRow == targetRow+1 && attackerColumn == targetColumn-1)
                    || (attackerRow == targetRow-1 && attackerColumn == targetColumn+1);
        }
        return result
                || (attackerRow == targetRow && attackerColumn == targetColumn - 1)
                || (attackerRow == targetRow && attackerColumn == targetColumn + 1)
                || (attackerColumn == targetColumn && attackerRow == targetRow - 1)
                || (attackerColumn == targetColumn && attackerRow == targetRow + 1)
                || (attackerPosition.equals(targetPosition));
    }

    public byte calculateAttackDamage(Creature attacker, Creature defender) {
        boolean hit;
        int damage = 0;
        int defence = 0;
        int atkDiceAmount = attacker.getAttackDiceAmount();
        if (isCreatureInAPit(attacker)) {
            atkDiceAmount--;
        }
        int defDiceAmount = defender.getDefenceDiceAmount();
        if (isCreatureInAPit(defender)) {
            defDiceAmount--;
        }
        if (StatusEnum.ROCK_SKIN.equals(defender.getStatus())) {
            defDiceAmount++;
        }
        if (StatusEnum.COURAGE.equals(attacker.getStatus())) {
            atkDiceAmount += 2;
        }
        int probability = 2;
        for (byte i = 1; i <= atkDiceAmount; i++) {
            hit = new Random().nextInt(probability) == 0;
            if (hit) {
                damage++;
            }
        }
        if (defender instanceof Monster) {
            probability = 6;
        } else {
            probability = 3;
        }
        if (StatusEnum.SLEEPING.equals(defender.getStatus())) {
            defence = 0;
        } else {
            for (byte i = 1; i <= defDiceAmount; i++) {
                hit = new Random().nextInt(probability) == 0;
                if (hit) {
                    defence++;
                }
            }
        }
        byte result = (byte) (damage - defence);

        if (result >= 0) {
			/*if(alvo.getStatus() == Status.ROCK_SKIN){
				alvo.setStatus(Status.NEUTRAL);
			}*/
            return result;
        } else {
            return 0;
        }
    }

    public static boolean isCreatureInAPit(Creature creature) {
        return creature.getCurrentPosition().getTrap() != null
                && creature.getCurrentPosition().getTrap() instanceof Pit;
    }
}
