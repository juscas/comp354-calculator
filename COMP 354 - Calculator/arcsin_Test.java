import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class arcsin_Test {

    @Test
    void _pi() {
        assertEquals(Transcendentals.arcsin(Math.PI), Math.asin(Math.PI), 0.00001, "Fail at x = " + Math.PI + ".");
    }

    @Test
    void _1to10000() {
        for (int i = 1; i < 10000; i++) {
            assertEquals(Transcendentals.arcsin(i), Math.asin(i), 0.00001, "Fail at i = " + i + ".");
        }
    }

    @Test
    void _neg10000to0() {
        for (int i = -10000; i < 0; i++) {
            assertEquals(Transcendentals.arcsin(i), Math.asin(i), 0.00001, "Fail at i = " + i + ".");
        }
    }
}