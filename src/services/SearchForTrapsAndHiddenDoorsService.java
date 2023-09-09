package services;

import entities.Position;
import entities.actions.SearchForTrapsAndHiddenDoors;
import entities.players.Dwarf;
import entities.tiles.Door;
import entities.tiles.Pit;
import entities.utils.Strings;
import quests.MelarsMaze;

public class SearchForTrapsAndHiddenDoorsService {

    private HeroQuest heroQuest;

    public SearchForTrapsAndHiddenDoorsService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public void processSearchForTrapsAndHiddenDoors(SearchForTrapsAndHiddenDoors action) {
        int sourceRow = action.getSourceRow();
        int sourceColumn = action.getSourceColumn();
        boolean isActionSourceADwarf = heroQuest.getPosition((byte)sourceRow, (byte)sourceColumn).getCreature() instanceof Dwarf;
        Position position;

        boolean removedTraps = false;
        for (int i = sourceRow - 2; i <= sourceRow + 2; i++) {
            for (int j = sourceColumn - 2; j <= sourceColumn + 2; j++) {
                if (i >= 0 && i < heroQuest.getTotalNumberOfRows() && j >= 0 && j < heroQuest.getTotalNumberOfColumns()) {
                    position = heroQuest.getPosition((byte)i, (byte)j);
                    if (position.getTrap() != null) {
                        position.makeTrapVisible();

                        if (position.getTrap() instanceof Pit) {
                            position.getTrap().setTriggered(true);
                        }

                        if (isActionSourceADwarf) {
                            position.removeTrap();
                            removedTraps = true;
                        }

                    }
                    if (position.getTreasure() != null && position.getTreasure().isTrap()) {
                        position.getTreasure().setAsTrap(false);
                        heroQuest.showMessagePopup(Strings.DISARMED_TREASURE_TRAP.toString());
                    }
                    if (position instanceof Door && ((Door) position).isSecret()) {
                        ((Door) position).setSecret(false);
                        if (heroQuest.getMap() instanceof MelarsMaze) {
                            if (((Door) position).getID() == 118) { // Throne room door
                                ((MelarsMaze) heroQuest.getMap()).moveThrone(heroQuest);
                            }
                        }
                    }
                }
            }
        }

        if (removedTraps) {
            heroQuest.showTrapRemovalMessage();
        }
    }
}
