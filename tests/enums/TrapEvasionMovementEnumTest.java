package enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrapEvasionMovementEnumTest {

    @Test
    public void shouldReturnForwardEnum() {
        TrapEvasionMovementEnum expectedEnum = TrapEvasionMovementEnum.FORWARD;
        assertEquals(expectedEnum, TrapEvasionMovementEnum.getEnumById((byte) 0));
    }

    @Test
    public void shouldReturnBackwardEnum() {
        TrapEvasionMovementEnum expectedEnum = TrapEvasionMovementEnum.BACKWARD;
        assertEquals(expectedEnum, TrapEvasionMovementEnum.getEnumById((byte) 1));
    }

    @Test
    public void shouldReturnFallenIntoPitEnum() {
        TrapEvasionMovementEnum expectedEnum = TrapEvasionMovementEnum.FALLEN_INTO_PIT;
        assertEquals(expectedEnum, TrapEvasionMovementEnum.getEnumById((byte) 2));
    }

    @Test
    public void shouldReturnZeroForForward() {
        byte expectedId = 0;
        assertEquals(expectedId, TrapEvasionMovementEnum.getIdByEnum(TrapEvasionMovementEnum.FORWARD));
    }

    @Test
    public void shouldReturnOneForBackward() {
        byte expectedId = 1;
        assertEquals(expectedId, TrapEvasionMovementEnum.getIdByEnum(TrapEvasionMovementEnum.BACKWARD));
    }

    @Test
    public void shouldReturnTwoForFallenIntoPit() {
        byte expectedId = 2;
        assertEquals(expectedId, TrapEvasionMovementEnum.getIdByEnum(TrapEvasionMovementEnum.FALLEN_INTO_PIT));
    }
}
