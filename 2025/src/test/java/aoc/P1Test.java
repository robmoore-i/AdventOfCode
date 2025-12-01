package aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class P1Test {

    @Test
    public void testZeroes() {
        checkDialTurning(91, 1, 1, "L10");
        checkDialTurning(0, 1, 1, "L1");
        checkDialTurning(1, 0, 2, "L1");
        checkDialTurning(97, 2, 2, "L105");
        checkDialTurning(20, 0, 50, "L30");
        checkDialTurning(0, 1, 47, "L47");
        checkDialTurning(90, 0, 0, "L10");
        checkDialTurning(0, 3, 40, "L240");
        checkDialTurning(0, 3, 0, "L300");

        checkDialTurning(11, 0, 1, "R10");
        checkDialTurning(0, 1, 1, "R99");
        checkDialTurning(95, 0, 90, "R5");
        checkDialTurning(10, 3, 80, "R230");
        checkDialTurning(80, 0, 50, "R30");
        checkDialTurning(25, 0, 0, "R25");
        checkDialTurning(25, 1, 0, "R125");
        checkDialTurning(0, 2, 0, "R200");
        checkDialTurning(0, 2, 94, "R106");
    }

    private static void checkDialTurning(int expectedDial, int expectedZeroes, int dialStart, String turn) {
        var dial = new P1.Dial(dialStart);
        assertEquals(expectedZeroes, dial.zeroes(turn));
        assertEquals(expectedDial, dial.value);
    }
}
