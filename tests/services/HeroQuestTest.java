package services;

import entities.Creature;
import entities.Player;
import entities.enemies.Gargoyle;
import entities.enemies.Monster;
import entities.enemies.PolarWarbear;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HeroQuestTest {

    private HeroQuest heroQuest;

    @Before
    public void before() {
        heroQuest = new HeroQuest();
    }

    @Test
    public void shouldSortCreatureQueueById() {
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);
        Gargoyle gargoyle = new Gargoyle();
        gargoyle.setID((byte)2);

        heroQuest.insertCreatureIntoQueue(gargoyle);
        heroQuest.insertCreatureIntoQueue(polarWarbear);

        ArrayList<Monster> sortedCreatureQueue = new ArrayList<>();
        sortedCreatureQueue.add(polarWarbear);
        sortedCreatureQueue.add(gargoyle);

        heroQuest.sortCreatureQueueById();
        assertEquals(sortedCreatureQueue, heroQuest.getCreatureQueue());
    }

    @Test
    public void shouldRemoveCreatureFromQueue() {
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);
        Gargoyle gargoyle = new Gargoyle();
        gargoyle.setID((byte)2);

        heroQuest.insertCreatureIntoQueue(gargoyle);
        heroQuest.insertCreatureIntoQueue(polarWarbear);

        Creature removedCreature = heroQuest.removeCreatureFromQueue();

        assertFalse(heroQuest.getCreatureQueue().contains(removedCreature));
    }

    @Test
    public void shouldInsertCreatureIntoQueue() {
        PolarWarbear polarWarbear = new PolarWarbear();
        polarWarbear.setID((byte)1);

        heroQuest.insertCreatureIntoQueue(polarWarbear);

        assertTrue(heroQuest.getCreatureQueue().contains(polarWarbear));
    }

    public void shouldRemovePlayerFromQueue() {
        Player player = new Player();
        heroQuest.insertPlayerIntoQueue(player);

        Player removedPlayer = heroQuest.removePlayerFromQueue();

        assertFalse(heroQuest.getPlayerQueue().contains(removedPlayer));
    }

    @Test
    public void shouldInsertPlayerIntoQueue() {
        Player player = new Player();
        heroQuest.insertPlayerIntoQueue(player);

        assertTrue(heroQuest.getPlayerQueue().contains(player));
    }
}
