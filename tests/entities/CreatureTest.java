package entities;

import entities.enemies.*;
import entities.players.Elf;
import entities.players.Wizard;
import enums.StatusEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatureTest {

    private Creature creature;

    @Before
    public void before() {
        creature = new Creature(1, 1, 1, 1);
    }

    @Test
    public void shouldSetStatusFromAgilityUpToNeutralOnDiceRoll() {
        creature.setStatus(StatusEnum.AGILITY_UP);
        creature.setMovement();
        assertTrue(StatusEnum.NEUTRAL.equals(creature.getStatus()));
    }

    @Test
    public void shouldSetStatusFromAgilityDownToNeutralOnDiceRoll() {
        creature.setStatus(StatusEnum.AGILITY_DOWN);
        creature.setMovement();
        assertTrue(StatusEnum.NEUTRAL.equals(creature.getStatus()));
    }

    @Test
    public void shouldRemoveSpellFromSpellBookWhenSpellIsCastByElf() {
        Elf elf = new Elf();
        elf.createSpells();
        Spell healBody = elf.getSpells().get(0);

        elf.spendSpell(healBody);

        assertFalse(elf.getSpells().contains(healBody));
    }

    @Test
    public void shouldDecreaseMindWhenSpellIsCastByElf() {
        Elf elf = new Elf();
        elf.createSpells();
        Spell healBody = elf.getSpells().get(0);

        elf.spendSpell(healBody);

        assertEquals(3, elf.getMind());
    }

    @Test
    public void shouldRemoveSpellFromSpellBookWhenSpellIsCastByWizard() {
        Wizard wizard = new Wizard();
        wizard.createSpells();
        Spell swiftWind = wizard.getSpells().get(0);

        wizard.spendSpell(swiftWind);

        assertFalse(wizard.getSpells().contains(swiftWind));
    }

    @Test
    public void shouldDecreaseMindWhenSpellIsCastByWizard() {
        Wizard wizard = new Wizard();
        wizard.createSpells();
        Spell swiftWind = wizard.getSpells().get(0);

        wizard.spendSpell(swiftWind);

        assertEquals(5, wizard.getMind());
    }

    @Test
    public void shouldReturnGoblinMovementAsTen() {
        creature = new Goblin();
        int expectedGoblinMovement = 10;

        assertEquals(expectedGoblinMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnOrcMovementAsEight() {
        creature = new Orc();
        int expectedOrcMovement = 8;

        assertEquals(expectedOrcMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnFimirMovementAsSix() {
        creature = new Fimir();
        int expectedFimirMovement = 6;

        assertEquals(expectedFimirMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnSkeletonMovementAsSix() {
        creature = new Skeleton();
        int expectedSkeletonMovement = 6;

        assertEquals(expectedSkeletonMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnZombieMovementAsFour() {
        creature = new Zombie();
        int expectedZombieMovement = 4;

        assertEquals(expectedZombieMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnMummyMovementAsFour() {
        creature = new Mummy();
        int expectedMummyMovement = 4;

        assertEquals(expectedMummyMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnChaosWarriorMovementAsSix() {
        creature = new ChaosWarrior();
        int expectedChaosWarriorMovement = 6;

        assertEquals(expectedChaosWarriorMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnGargoyleMovementAsSix() {
        creature = new Gargoyle();
        int expectedGargoyleMovement = 6;

        assertEquals(expectedGargoyleMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnPolarWarbearMovementAsSix() {
        creature = new PolarWarbear();
        int expectedPolarWarbearMovement = 6;

        assertEquals(expectedPolarWarbearMovement, creature.getMonsterMovement());
    }

    @Test
    public void shouldReturnChaosSorcererMovementAsEight() {
        creature = new ChaosSorcerer();
        int expectedChaosSorcererMovement = 8;

        assertEquals(expectedChaosSorcererMovement, creature.getMonsterMovement());
    }
}
