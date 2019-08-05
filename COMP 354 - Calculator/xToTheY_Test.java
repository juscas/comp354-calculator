import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class xToTheY_Test
{

	@Test
	void _e_x() {
		for (int i = 1; i < 20; i++) {
			assertEquals(Transcendentals.power(Math.E, i), Math.pow(Math.E,i), 0.00001, "Fail at x = " + i + ".");
		}

		for (int i = 0; i < 10; i++) {
			assertEquals(Transcendentals.power(Math.E, i/10), Math.pow(Math.E,i/10), 0.00001, "Fail at x = " + i/10 + ".");
		}
	}

	@Test
	void _pi_x() {
		for (int i = 1; i < 20; i++) {
			assertEquals(Transcendentals.power(Math.PI, i), Math.pow(Math.PI,i), 0.00001, "Fail at x = " + i + ".");
		}
		for (int i = 0; i < 100; i++) {
			assertEquals(Transcendentals.power(Math.PI, i/10), Math.pow(Math.PI,i/10), 0.00001, "Fail at x = " + i/10 + ".");
		}
	}

	@Test
	void _1to50() {
		for (int i = 1; i < 50; i++) {
            for (int j = 1; j < 10; j++) {
                assertEquals(Transcendentals.power(i, j), Math.pow(i, j), 1, "Fail at i = " + i + " and j = " + j + ".");
            }
        }
	}
/*
    @Test
    void _1to10_Decimals() {
        double j = 0.1;

        for (int i = 1; i < 10; i++) {
            while (j < 1.0) {
                assertEquals(Transcendentals.realPower(i, j), Math.pow(i, j), 0.00001, "Fail at x = " + String.valueOf(j) + ".");
                j += 0.1;
                System.out.println(Transcendentals.realPower(i, j) + " " + Math.pow(i, j));
            }
        }
    }
*/
}
