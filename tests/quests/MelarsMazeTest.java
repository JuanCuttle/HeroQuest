package quests;

import services.HeroQuest;
import entities.tiles.Treasure;
import enums.ItemEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MelarsMazeTest {

    private MelarsMaze quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new MelarsMaze(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseWhenTaslismanOfLoreHasNotBeenFound() {
        Treasure treasure = game.getPosition((byte) 14, (byte) 17).getTreasure();
        treasure.setItem(ItemEnum.TalismanOfLore);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenTaslismanOfLoreHasBeenFound() {
        Treasure treasure = game.getPosition((byte) 14, (byte) 17).getTreasure();
        treasure.setItem(null);

        assertTrue(quest.verifyWinningConditions(game));
    }
}
