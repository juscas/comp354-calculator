import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class lnX_Test
{

    @Test
    void _ln_e() {
            assertEquals(Model.MathFunctions.ln(Math.E), Math.log(Math.E), 0.0000001, "Fail at x = " + Math.E + ".");
    }

    @Test
    void _ln_pi() {
            assertEquals(Model.MathFunctions.ln(Math.PI), Math.log(Math.PI), 0.0000001, "Fail at x = " + Math.PI + ".");
    }

	@Test
	void _1to1000() {
	    for (int i = 1; i < 300; i = i + 5){
            assertEquals(Model.MathFunctions.ln(i), Math.log(i), 0.0000001, "Fail at x = " + String.valueOf(i) + ".");
        }
	}

    @Test
    void _1to100_Decimals() {
        double i = 0.1;
        while(i < 100.0){
            assertEquals(Model.MathFunctions.ln(i), Math.log(i), 0.0000001, "Fail at x = " + String.valueOf(i) + ".");
            i += 0.01;
        }
    }
}
