import org.junit.jupiter.api.Test;
import java.math.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class squareRoot_Test
{

    @Test
    void _1to1000() {
        for (int i = 1; i < 1000; i = i + 5){
            assertEquals(MathFunctions.squareRoot(i), Math.sqrt(i), 0.00001, "Fail at x = " + String.valueOf(i) + ".");
        }
    }

    @Test
    void _1000to10000() {
        for (int i = 1000; i < 10000; i = i + 5){
            assertEquals(MathFunctions.squareRoot(i), Math.sqrt(i), 0.00001, "Fail at x = " + String.valueOf(i) + ".");
        }
    }

    @Test
    void _1to1000_Decimals() {
        double i = 0.1;
        while(i < 1000.0){
            assertEquals(MathFunctions.squareRoot(i), Math.sqrt(i), 0.00001, "Fail at x = " + String.valueOf(i) + ".");
            i += 0.01;
        }
    }
}
