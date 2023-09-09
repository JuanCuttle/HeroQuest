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

public class TheTrialTest {

    private TheTrial quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new TheTrial(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfVeragIsNeutral() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc verag = new Orc();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc verag = new Orc();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfVeragIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc verag = new Orc();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.DEAD);
        creatureQueue.add(verag);
        assertTrue(quest.verifyWinningConditions(game));
    }
}
