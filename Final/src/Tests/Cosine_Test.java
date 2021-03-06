package Tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Model.MathFunctions;


class Cosine_Test
{
	
    @Test
    void _pi() {
        assertEquals(Model.MathFunctions.cos(Math.PI), Math.cos(Math.PI), 0.00001, "Fail at x = " + Math.PI + ".");
    }

    @Test
    void _1to10000() {
        for (int i = 1; i < 10000; i++) {
            assertEquals(Model.MathFunctions.cos(i), Math.cos(i), 0.00001, "Fail at i = " + i + ".");
        }
    }

    @Test
    void _neg10000to0() {
        for (int i = -10000; i < 0; i++) {
            assertEquals(Model.MathFunctions.cos(i), Math.cos(i), 0.00001, "Fail at i = " + i + ".");
        }
    }

}
