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

public class LegacyOfTheOrcWarlordTest {

    private LegacyOfTheOrcWarlord quest;
    private HeroQuest game;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new LegacyOfTheOrcWarlord(game);
        game.setMap(quest);
    }

    @Test
    public void shouldReturnFalseIfEquipmentHasNotBeenFound() {
        game.getPosition((byte) 5, (byte) 5).setVisible(false);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnFalseIfEquipmentHasNotBeenFoundAndHeroesAreNotOnStairs() {
        game.getPosition((byte) 5, (byte) 5).setVisible(false);

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
    public void shouldReturnFalseIfEquipmentHasBeenFoundAndHeroesAreNotOnStairs() {
        game.getPosition((byte) 5, (byte) 5).setVisible(true);

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
    public void shouldReturnFalseIfEquipmentHasBeenFoundAndOneHeroIsNotOnStairs() {
        game.getPosition((byte) 5, (byte) 5).setVisible(true);

        List<Creature> creatureQueue = game.getCreatureQueue();
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 9));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(25, 25));
        creatureQueue.add(wizard);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueIfEquipmentHasBeenFoundAndHeroesAreOnStairs() {
        game.getPosition((byte) 5, (byte) 5).setVisible(true);

        List<Creature> creatureQueue = game.getCreatureQueue();
        Barbarian barbarian = new Barbarian();
        barbarian.setID((byte)20);
        barbarian.setCurrentPosition(new Position(20, 9));
        creatureQueue.add(barbarian);
        Wizard wizard = new Wizard();
        wizard.setID((byte)21);
        wizard.setCurrentPosition(new Position(20, 10));
        creatureQueue.add(wizard);

        assertTrue(quest.verifyWinningConditions(game));
    }
}
