package quests;

import entities.Creature;
import entities.HeroQuest;
import entities.Position;
import entities.enemies.PolarWarbear;
import entities.players.Barbarian;
import entities.players.Wizard;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicMapTest {

    private BasicMap basicMap;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        basicMap = new BasicMap();
    }

    @Test
    public void shouldReturnFalseWhenNoCreaturesAreOnMap() {
        assertFalse(basicMap.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseWhenHeroesAreOutsideStairs() {
        game.setMap(basicMap);
        List<Creature> creatureQueue = game.getCreatureQueue();
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);
        creatureQueue.add(polarWarbear);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 20));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(19, 20));
        creatureQueue.add(wizard);
        assertFalse(basicMap.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenOneHeroIsOnStairs() {
        game.setMap(basicMap);
        List<Creature> creatureQueue = game.getCreatureQueue();
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);
        creatureQueue.add(polarWarbear);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 20));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);
        assertTrue(basicMap.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenAllHeroesAreOnStairs() {
        game.setMap(basicMap);
        List<Creature> creatureQueue = game.getCreatureQueue();
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);
        creatureQueue.add(polarWarbear);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(24, 25));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);
        assertTrue(basicMap.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseWhenHeroIsOutsideStairs() {
        Barbarian barbarian = new Barbarian();
        barbarian.setCurrentPosition(new Position(20, 20));

        assertFalse(basicMap.onStairs(barbarian.getCurrentPosition(), 24, 24));
    }

    @Test
    public void shouldReturnTrueWhenHeroIsOnStairsTile1() {
        Barbarian barbarian = new Barbarian();
        barbarian.setCurrentPosition(new Position(24, 24));

        assertTrue(basicMap.onStairs(barbarian.getCurrentPosition(), 24, 24));
    }

    @Test
    public void shouldReturnTrueWhenHeroIsOnStairsTile2() {
        Barbarian barbarian = new Barbarian();
        barbarian.setCurrentPosition(new Position(24, 25));

        assertTrue(basicMap.onStairs(barbarian.getCurrentPosition(), 24, 24));
    }
}
