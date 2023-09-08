package enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MonsterEnumTest {

    @Test
    public void shouldReturnGoblinEnum() {
        MonsterEnum expectedEnum = MonsterEnum.GOBLIN;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Goblin"));
    }

    @Test
    public void shouldReturnOrcEnum() {
        MonsterEnum expectedEnum = MonsterEnum.ORC;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Orc"));
    }

    @Test
    public void shouldReturnFimirEnum() {
        MonsterEnum expectedEnum = MonsterEnum.FIMIR;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Fimir"));
    }

    @Test
    public void shouldReturnSkeletonEnum() {
        MonsterEnum expectedEnum = MonsterEnum.SKELETON;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Skeleton"));
    }

    @Test
    public void shouldReturnZombieEnum() {
        MonsterEnum expectedEnum = MonsterEnum.ZOMBIE;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Zombie"));
    }

    @Test
    public void shouldReturnMummyEnum() {
        MonsterEnum expectedEnum = MonsterEnum.MUMMY;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Mummy"));
    }

    @Test
    public void shouldReturnChaosWarriorEnum() {
        MonsterEnum expectedEnum = MonsterEnum.CHAOS_WARRIOR;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("ChaosWarrior"));
    }

    @Test
    public void shouldReturnGargoyleEnum() {
        MonsterEnum expectedEnum = MonsterEnum.GARGOYLE;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("Gargoyle"));
    }

    @Test
    public void shouldReturnPolarWarbearEnum() {
        MonsterEnum expectedEnum = MonsterEnum.POLAR_WARBEAR;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("PolarWarbear"));
    }

    @Test
    public void shouldReturnChaosSorcererEnum() {
        MonsterEnum expectedEnum = MonsterEnum.CHAOS_SORCERER;
        assertEquals(expectedEnum, MonsterEnum.getEnumByName("ChaosSorcerer"));
    }
}
