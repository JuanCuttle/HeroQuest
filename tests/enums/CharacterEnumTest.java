package enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterEnumTest {

    @Test
    public void shouldReturnZargonEnum() {
        CharacterEnum expectedEnum = CharacterEnum.ZARGON;
        assertEquals(expectedEnum, CharacterEnum.getEnumById(0));
    }

    @Test
    public void shouldReturnBarbarianEnum() {
        CharacterEnum expectedEnum = CharacterEnum.BARBARIAN;
        assertEquals(expectedEnum, CharacterEnum.getEnumById(1));
    }

    @Test
    public void shouldReturnWizardEnum() {
        CharacterEnum expectedEnum = CharacterEnum.WIZARD;
        assertEquals(expectedEnum, CharacterEnum.getEnumById(2));
    }

    @Test
    public void shouldReturnElfEnum() {
        CharacterEnum expectedEnum = CharacterEnum.ELF;
        assertEquals(expectedEnum, CharacterEnum.getEnumById(3));
    }

    @Test
    public void shouldReturnDwarfEnum() {
        CharacterEnum expectedEnum = CharacterEnum.DWARF;
        assertEquals(expectedEnum, CharacterEnum.getEnumById(4));
    }

    @Test
    public void shouldReturnZeroForZargon() {
        int expectedId = 0;
        assertEquals(expectedId, CharacterEnum.getIdByName("Zargon"));
    }

    @Test
    public void shouldReturnOneForBarbarian() {
        int expectedId = 1;
        assertEquals(expectedId, CharacterEnum.getIdByName("Barbarian"));
    }

    @Test
    public void shouldReturnTwoForWizard() {
        int expectedId = 2;
        assertEquals(expectedId, CharacterEnum.getIdByName("Wizard"));
    }

    @Test
    public void shouldReturnThreeForElf() {
        int expectedId = 3;
        assertEquals(expectedId, CharacterEnum.getIdByName("Elf"));
    }

    @Test
    public void shouldReturnFourForDwarf() {
        int expectedId = 4;
        assertEquals(expectedId, CharacterEnum.getIdByName("Dwarf"));
    }
}
