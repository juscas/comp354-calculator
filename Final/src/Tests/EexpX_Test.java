import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class EexpX_Test
{
	@Test
	void _1to100() {
		for (int i = 1; i < 30; i = i + 5){
			assertEquals(MathFunctions.e_to_x(i), Math.pow(Math.E,i), 0.0001, "Fail at x = " + String.valueOf(i) + ".");
		}
	}
}
