package quests;

import entities.Creature;
import services.HeroQuest;
import entities.enemies.ChaosSorcerer;
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
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfUlagIsAgilityDown() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.AGILITY_DOWN);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsAgilityUp() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.AGILITY_UP);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsCourage() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.COURAGE);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsCursed() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.CURSED);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfBalurIsRockSkin() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.ROCK_SKIN);
        creatureQueue.add(balur);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfBalurIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        ChaosSorcerer balur = new ChaosSorcerer();
        balur.setID((byte)15);
        balur.setStatus(StatusEnum.DEAD);
        creatureQueue.add(balur);
        assertTrue(quest.verifyWinningConditions(game));
    }
}
