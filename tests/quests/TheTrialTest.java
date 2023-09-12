package quests;

import entities.Creature;
import services.HeroQuest;
import entities.enemies.Gargoyle;
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
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.NEUTRAL);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsAsleep() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.SLEEPING);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsAgilityDown() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.AGILITY_DOWN);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsAgilityUp() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.AGILITY_UP);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsCourage() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.COURAGE);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsCursed() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.CURSED);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfVeragIsRockSkin() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.ROCK_SKIN);
        creatureQueue.add(verag);
        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfVeragIsDead() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Gargoyle verag = new Gargoyle();
        verag.setID((byte)24);
        verag.setStatus(StatusEnum.DEAD);
        creatureQueue.add(verag);
        assertTrue(quest.verifyWinningConditions(game));
    }
}
