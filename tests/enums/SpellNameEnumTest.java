package enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpellNameEnumTest {

    @Test
    public void shouldReturnSwiftWind() {
        String expectedName = SpellNameEnum.SWIFT_WIND.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 1));
    }

    @Test
    public void shouldReturnTempest() {
        String expectedName = SpellNameEnum.TEMPEST.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 2));
    }

    @Test
    public void shouldReturnBallOfFlame() {
        String expectedName = SpellNameEnum.BALL_OF_FLAME.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 3));
    }

    @Test
    public void shouldReturnCourage() {
        String expectedName = SpellNameEnum.COURAGE.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 4));
    }

    @Test
    public void shouldReturnFireOfWrath() {
        String expectedName = SpellNameEnum.FIRE_OF_WRATH.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 5));
    }

    @Test
    public void shouldReturnSleep() {
        String expectedName = SpellNameEnum.SLEEP.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 6));
    }

    @Test
    public void shouldReturnWaterOfHealing() {
        String expectedName = SpellNameEnum.WATER_OF_HEALING.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 7));
    }

    @Test
    public void shouldReturnHealBody() {
        String expectedName = SpellNameEnum.HEAL_BODY.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 8));
    }

    @Test
    public void shouldReturnRockFeet() {
        String expectedName = SpellNameEnum.ROCK_FEET.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 9));
    }

    @Test
    public void shouldReturnRockSkin() {
        String expectedName = SpellNameEnum.ROCK_SKIN.getName();
        assertEquals(expectedName, SpellNameEnum.getNameById((byte) 10));
    }
}
