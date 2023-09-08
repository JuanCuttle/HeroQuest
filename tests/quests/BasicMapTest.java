package quests;

import entities.Creature;
import entities.HeroQuest;
import entities.Position;
import entities.Zargon;
import entities.enemies.PolarWarbear;
import entities.players.Barbarian;
import entities.players.Wizard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicMapTest {

    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
    }

    @Test
    public void shouldReturnFalseWhenNoCreaturesAreOnMap() {
        BasicMap basicMap = new BasicMap();
        assertFalse(basicMap.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseWhenHeroesAreOutsideStairs() {
        BasicMap map = new BasicMap();
        game.setMap(map);
        List<Creature> creatureQueue = game.getCreatureQueue();
        creatureQueue.add(new PolarWarbear());
        creatureQueue.get(0).setID((byte)1);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 20));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(19, 20));
        creatureQueue.add(wizard);
        assertFalse(map.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenOneHeroIsOnStairs() {
        BasicMap map = new BasicMap();
        game.setMap(map);
        List<Creature> creatureQueue = game.getCreatureQueue();
        creatureQueue.add(new PolarWarbear());
        creatureQueue.get(0).setID((byte)1);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 20));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);
        assertTrue(map.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenAllHeroesAreOnStairs() {
        BasicMap map = new BasicMap();
        game.setMap(map);
        List<Creature> creatureQueue = game.getCreatureQueue();
        creatureQueue.add(new PolarWarbear());
        creatureQueue.get(0).setID((byte)1);
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(24, 25));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);
        assertTrue(map.verifyWinningConditions(game));
    }
}
