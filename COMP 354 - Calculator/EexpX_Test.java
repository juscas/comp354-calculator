import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class EexpX_Test
{
	@Test
	void _1to100() {
		for (int i = 1; i < 100; i = i + 5){
			assertEquals(Transcendentals.e_to_x(i), Math.pow(Math.E,i), 0.0001, "Fail at x = " + String.valueOf(i) + ".");
		}
	}

	@Test
	void _1to100_Decimals() {
		double i = 0.1;
		while(i < 100.0){
			assertEquals(Transcendentals.e_to_x(i), Math.pow(Math.E,i), 0.00001, "Fail at x = " + String.valueOf(i) + ".");
			i += 0.01;
		}
	}
}
