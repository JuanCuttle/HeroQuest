package quests;

import entities.Creature;
import services.HeroQuest;
import entities.Position;
import entities.players.Barbarian;
import entities.players.Wizard;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TheMazeTest {

    private TheMaze quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new TheMaze(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfHeroesAreNotOnStairs() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 20));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfOneHeroIsNotOnStairs() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(13, 18));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfHeroesAreOnStairs() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(13, 18));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(14, 19));
        creatureQueue.add(wizard);

        assertTrue(quest.verifyWinningConditions(game));
    }
}
