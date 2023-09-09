package quests;

import services.HeroQuest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrinceMagnusGoldTest {

    private PrinceMagnusGold quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new PrinceMagnusGold(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseWhenNoGoldHasBeenFound() {
        quest.generateTreasures();
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseWhenNotAllGoldHasBeenFound() {
        quest.generateTreasures();
        game.getPosition((byte) 12, (byte) 17).getTreasure().setGoldAmount(-1);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenAllGoldHasBeenFound() {
        quest.generateTreasures();
        game.getPosition((byte) 12, (byte) 17).getTreasure().setGoldAmount(-1);
        game.getPosition((byte) 13, (byte) 16).getTreasure().setGoldAmount(-1);
        game.getPosition((byte) 16, (byte) 16).getTreasure().setGoldAmount(-1);

        assertTrue(quest.verifyWinningConditions(game));
    }
}
