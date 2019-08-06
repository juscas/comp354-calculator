import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class SineTest {

	@Test
	void _pi() {
		assertEquals(MathFunctions.sin(Math.PI), Math.sin(Math.PI), 0.00001, "Fail at x = " + Math.PI + ".");
	}

	@Test
	void _1to10000() {
		for (int i = 1; i < 10000; i++) {
			assertEquals(MathFunctions.sin((double) i), Math.sin((double) i), 0.00001, "Fail at i = " + i + ".");
		}
	}
}
