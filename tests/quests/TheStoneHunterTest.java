package quests;

import entities.Creature;
import services.HeroQuest;
import entities.enemies.Orc;
import enums.StatusEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TheStoneHunterTest {
    
    private TheStoneHunter quest;
    private HeroQuest game;
    
    @Before
    public void before() {
        game = new HeroQuest();
        quest = new TheStoneHunter(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfKarlenIsNeutral() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc karlen = new Orc();
        karlen.setID((byte)6);
        karlen.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(karlen);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfKarlenIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc karlen = new Orc();
        karlen.setID((byte)6);
        karlen.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(karlen);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfKarlenIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc karlen = new Orc();
        karlen.setID((byte)6);
        karlen.setStatus(StatusEnum.DEAD);
        creatureQueue.add(karlen);
        assertTrue(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfKarlenHasNotBeenFound() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc karlen = new Orc();
        karlen.setID((byte)6);
        karlen.setVisible(false);
        creatureQueue.add(karlen);
        quest.specialOccurrence(game);
        assertFalse(quest.getFoundKarlen());
    }
}