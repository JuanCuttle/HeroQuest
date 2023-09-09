package services;

import entities.Creature;
import entities.actions.EndTurn;
import entities.players.Elf;
import entities.players.Wizard;
import entities.utils.Strings;
import enums.StatusEnum;

public class EndTurnService {

    private HeroQuest heroQuest;

    public EndTurnService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public String endTurn() {
        if (heroQuest.verifyIfItIsCurrentPlayersTurn()) {
            EndTurn action = new EndTurn();
            heroQuest.processAction(action);
            heroQuest.sendAction(action);
        } else {
            return Strings.NOT_YOUR_TURN.toString();
        }
        return null;
    }

    public void processEndTurn() {
        heroQuest.updatePlayerSurroundings(); // added for GUI refresh

        Creature endingCreature = heroQuest.removeCreatureFromQueue();
        heroQuest.getCreatureQueue().trimToSize();
        heroQuest.insertCreatureIntoQueue(endingCreature);

        if (!(endingCreature instanceof Wizard) && !(endingCreature instanceof Elf)) {
            StatusEnum creatureWhoEndedTurnStatus = endingCreature.getStatus();
            if (StatusEnum.AGILITY_UP.equals(creatureWhoEndedTurnStatus)
                    || StatusEnum.AGILITY_DOWN.equals(creatureWhoEndedTurnStatus)) {

                endingCreature.setStatus(StatusEnum.NEUTRAL);
            }
        }

        Creature newCurrentCreature = heroQuest.getCurrentCreature();
        StatusEnum newCurrentCreatureStatus = newCurrentCreature.getStatus();

        byte verifiedCreatures = 0;
        while ((StatusEnum.DEAD.equals(newCurrentCreatureStatus)
                || !newCurrentCreature.isVisible()
                || StatusEnum.CURSED.equals(newCurrentCreatureStatus)
                || StatusEnum.SLEEPING.equals(newCurrentCreatureStatus)) && verifiedCreatures <= heroQuest.getCreatureQueue().size()) {

            if (StatusEnum.CURSED.equals(newCurrentCreatureStatus)
                    || StatusEnum.AGILITY_UP.equals(newCurrentCreatureStatus)
                    || StatusEnum.AGILITY_DOWN.equals(newCurrentCreatureStatus)) {

                newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
            }

            if (StatusEnum.SLEEPING.equals(newCurrentCreatureStatus)) {
                byte roundsToSleep = (byte)(newCurrentCreature.getRoundsToSleep()-1);
                newCurrentCreature.setRoundsToSleep(roundsToSleep);
                if (roundsToSleep == 0){
                    newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
                    heroQuest.showMessagePopup(Strings.THE_CREATURE + newCurrentCreature.getClass().getSimpleName()+ Strings.WOKE_UP);
                }
            }
            endingCreature = heroQuest.removeCreatureFromQueue();
            heroQuest.getCreatureQueue().trimToSize();
            heroQuest.insertCreatureIntoQueue(endingCreature);
            newCurrentCreature = heroQuest.getCurrentCreature();
            newCurrentCreatureStatus = newCurrentCreature.getStatus();

            verifiedCreatures++;
        }
        newCurrentCreature.setMovement();

        if (StatusEnum.COURAGE.equals(newCurrentCreature.getStatus()) && areThereNoEnemiesOnSight(newCurrentCreature)) {
            newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
        }
        heroQuest.specialOccurrence();
        heroQuest.endTheGame();
    }

    private boolean areThereNoEnemiesOnSight(Creature sourceCreature) {
        return heroQuest.getAvailableTargets(1, sourceCreature.getCurrentPosition()).size() == 1;
    }
}
