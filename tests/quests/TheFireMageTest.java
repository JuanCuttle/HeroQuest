package quests;

import entities.Creature;
import entities.HeroQuest;
import entities.enemies.Orc;
import enums.StatusEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TheFireMageTest {

    private TheFireMage quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new TheFireMage(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfBalurIsNeutral() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc balur = new Orc();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc balur = new Orc();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfBalurIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc balur = new Orc();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.DEAD);
        creatureQueue.add(balur);
        assertTrue(quest.verifyWinningConditions(game));
    }
}
