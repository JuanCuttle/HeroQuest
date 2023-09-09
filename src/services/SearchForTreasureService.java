package services;

import entities.Creature;
import entities.Position;
import entities.actions.SearchForTreasure;
import entities.players.PlayableCharacter;
import entities.tiles.Treasure;
import entities.utils.Strings;
import enums.ItemEnum;

public class SearchForTreasureService {

    private HeroQuest heroQuest;

    public SearchForTreasureService(HeroQuest heroQuest) {
        this.heroQuest = heroQuest;
    }

    public String searchForTreasure() {
        if (heroQuest.verifyIfItIsCurrentPlayersTurn()) {
            Creature caster = heroQuest.getCurrentCreature();
            if (caster instanceof PlayableCharacter) {
                Position source = caster.getCurrentPosition();
                SearchForTreasure action = new SearchForTreasure();
                action.setSourceRow(source.getRow());
                action.setSourceColumn(source.getColumn());
                heroQuest.processAction(action);
                heroQuest.sendAction(action);
            } else {
                return Strings.MONSTER_CANT_UNDERSTAND_COMMAND.toString();
            }
        } else {
            return Strings.NOT_YOUR_TURN.toString();
        }
        return null;
    }

    public void processSearchForTreasure(SearchForTreasure action) {
        boolean foundGold = false;
        boolean foundItem = false;
        String itemName = "";

        byte sourceRow = action.getSourceRow();
        byte sourceColumn = action.getSourceColumn();

        Position position = heroQuest.getPosition(sourceRow, sourceColumn);
        PlayableCharacter character = (PlayableCharacter) position.getCreature();
        for (int i = sourceRow - 2; i <= sourceRow + 2; i++) {
            for (int j = sourceColumn - 2; j <= sourceColumn + 2; j++) {
                if (i >= 0 && i < heroQuest.getTotalNumberOfRows() && j >= 0 && j < heroQuest.getTotalNumberOfColumns()) {
                    position = heroQuest.getPosition((byte)i, (byte)j);
                    Treasure treasureInPosition = position.getTreasure();
                    if (treasureInPosition != null) {
                        int gold = treasureInPosition.getGoldAmount();
                        ItemEnum item = treasureInPosition.getItem();

                        if (treasureInPosition.isTrap()) {
                            character.decreaseBody((byte) 1);
                            treasureInPosition.setAsTrap(false);
                            heroQuest.showTrapActivationMessage((byte) 1, character);
                        } else {
                            if (gold >= 0) {
                                treasureInPosition.setGoldAmount(-1);
                                character.increaseGold(gold);
                                foundGold = true;
                            }
                            if (item != null) {
                                character.addItemToBag(item);
                                treasureInPosition.setItem(null);
                                foundItem = true;
                                itemName = item.name();
                            }
                        }
                    }
                }
            }
        }
        if (foundGold) {
            heroQuest.showMessagePopup(Strings.THE_PLAYER
                    + character.getClass().getSimpleName()
                    + Strings.FOUND_GOLD);
        }
        if (foundItem) {
            heroQuest.showMessagePopup(Strings.THE_PLAYER
                    + character.getClass().getSimpleName()
                    + Strings.FOUND_ITEM + itemName);
        }
    }
}
