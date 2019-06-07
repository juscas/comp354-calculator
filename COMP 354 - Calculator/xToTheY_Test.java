import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class xToTheY_Test
{

	@Test
	// To be changed, I have to cast to float because scientific notation makes results offset
	void _e_x() {
		for (int i = 1; i < 20; i++) {
			assertEquals(Transcendentals.realPower(Math.E, i), Math.pow(Math.E,i), 0.00001, "Fail at x = " + i + ".");
		}

		for (int i = 0; i < 10; i++) {
			assertEquals(Transcendentals.realPower(Math.E, i/10), Math.pow(Math.E,i/10), 0.00001, "Fail at x = " + i/10 + ".");
		}
	}

	@Test
	void _pi_x() {
		for (int i = 1; i < 20; i++) {
			assertEquals(Transcendentals.realPower(Math.PI, i), Math.pow(Math.PI,i), 0.00001, "Fail at x = " + i + ".");
		}
		for (int i = 0; i < 100; i++) {
			assertEquals(Transcendentals.realPower(Math.PI, i/10), Math.pow(Math.PI,i/10), 0.00001, "Fail at x = " + i/10 + ".");
		}
	}

	@Test
	void _1to1000() {
		for (int i = 1; i < 1000; i = i + 5){
			//assertEquals(Transcendentals.ln(i), Math.log(i), 0.00001, "Fail at x = " + String.valueOf(i) + ".");
		}
	}

}
