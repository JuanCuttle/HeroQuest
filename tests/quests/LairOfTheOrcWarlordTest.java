package quests;

import entities.Creature;
import entities.HeroQuest;
import entities.enemies.Orc;
import entities.enemies.PolarWarbear;
import enums.StatusEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LairOfTheOrcWarlordTest {

    private LairOfTheOrcWarlord quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new LairOfTheOrcWarlord(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfUlagIsNeutral() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc ulag = new Orc();
        ulag.setID((byte)6);
        ulag.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(ulag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfUlagIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc ulag = new Orc();
        ulag.setID((byte)6);
        ulag.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(ulag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfUlagIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Orc ulag = new Orc();
        ulag.setID((byte)6);
        ulag.setStatus(StatusEnum.DEAD);
        creatureQueue.add(ulag);
        assertTrue(quest.verifyWinningConditions(game));
    }
}
