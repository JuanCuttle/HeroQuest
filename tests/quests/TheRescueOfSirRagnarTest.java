package quests;

import entities.Creature;
import services.HeroQuest;
import entities.Position;
import entities.SirRagnar;
import entities.tiles.Door;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TheRescueOfSirRagnarTest {

    private TheRescueOfSirRagnar quest;
    private HeroQuest game;
    private GUI gui;

    @Before
    public void before() {
        game = new HeroQuest();
        quest = new TheRescueOfSirRagnar(game);
        game.setMap(quest);
        gui = new GUI();
        gui.setHeroQuest(game);
        gui.generateCreatureButtons();
        gui.generateBoardButtons();
        game.setGui(gui);
    }

    @Test
    public void shouldReturnTrueWhenAllDoorsAreOpenFromFindingSirRagnar() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        SirRagnar sirRagnar = new SirRagnar();
        sirRagnar.setID((byte) 14);
        sirRagnar.setVisible(true);
        creatureQueue.add(sirRagnar);

        quest.specialOccurrence(game);

        boolean areAllDoorsOpen = true;
        ArrayList<Door> doors = game.getDoors();
        for (Door d : doors) {
            if (!d.isOpen()) {
                areAllDoorsOpen = false;
            }
        }
        assertTrue(areAllDoorsOpen);
    }

    @Test
    public void shouldReturnTrueWhenAllPositionsAreVisibleFromFindingSirRagnar() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        SirRagnar sirRagnar = new SirRagnar();
        sirRagnar.setID((byte) 14);
        sirRagnar.setVisible(true);
        creatureQueue.add(sirRagnar);

        quest.specialOccurrence(game);

        boolean areAllPositionsVisible = true;
        Position[][] positions = quest.getPositions();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                Position p = positions[i][j];
                if (!p.isVisible()) {
                    areAllPositionsVisible = false;
                }
            }
        }
        assertTrue(areAllPositionsVisible);
    }

    @Test
    public void shouldReturnTrueWhenAllCreaturesAreVisibleFromFindingSirRagnar() {
        game.getCreatureQueue().addAll(quest.createMonsters(game));
        List<Creature> creatureQueue = game.getCreatureQueue();
        Creature sirRagnar = game.getCreatureByID(14);
        sirRagnar.setVisible(true);
        quest.specialOccurrence(game);

        boolean areaAllCreaturesVisible = true;
        for (Creature creature : creatureQueue) {
            if (!creature.isVisible()) {
                areaAllCreaturesVisible = false;
            }
        }
        assertTrue(areaAllCreaturesVisible);
    }

    @Test
    public void shouldReturnFalseWhenSirRagnarIsNotOnStairs() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        SirRagnar sirRagnar = new SirRagnar();
        sirRagnar.setID((byte) 14);
        sirRagnar.setCurrentPosition(new Position((byte)11, (byte) 15));
        creatureQueue.add(sirRagnar);

        assertFalse(quest.verifyWinningConditions(game));
    }

    @Test
    public void shouldReturnTrueWhenSirRagnarIsOnStairs() {
        List<Creature> creatureQueue = game.getCreatureQueue();
        SirRagnar sirRagnar = new SirRagnar();
        sirRagnar.setID((byte) 14);
        sirRagnar.setCurrentPosition(new Position((byte)12, (byte) 16));
        creatureQueue.add(sirRagnar);

        assertTrue(quest.verifyWinningConditions(game));
    }
}
