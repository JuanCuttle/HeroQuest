package entities;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PositionTest {

    private Position position1;

    private Position position2;

    @Test
    public void shouldReturnFalseForPositionsWithSameRowAndDifferentColumn() {
        position1 = new Position(0, 0);
        position2 = new Position(0, 1);
        assertFalse(position1.equals(position2));
    }

    @Test
    public void shouldReturnFalseForPositionsWithDifferentRowAndSameColumn() {
        position1 = new Position(0, 0);
        position2 = new Position(1, 0);
        assertFalse(position1.equals(position2));
    }

    @Test
    public void shouldReturnFalseForPositionsWithDifferentRowAndDifferentColumn() {
        position1 = new Position(0, 0);
        position2 = new Position(1, 1);
        assertFalse(position1.equals(position2));
    }

    @Test
    public void shouldReturnTrueForPositionsWithSameRowAndColumn() {
        position1 = new Position(0, 0);
        position2 = new Position(0, 0);
        assertTrue(position1.equals(position2));
    }
}
