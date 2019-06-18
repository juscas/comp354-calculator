import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class Cosine_Test
{

    /**
	 * This tests cos(x) from -2Pi to 2Pi and breaks as soon as the absolute difference between the
	 * two is greater than the specified precision. Do not need to test outside that range as the 
	 * cos(x) function brings all radians values down around 0 using 'radians =  radians % (2*PI)'.
	 */
	@Test
	void CosTest() {
		
		boolean preciseEnough = true;
		double radians = -2 * MathFunctions.PI;
		double desiredPrecision = 0.00001;
		
		for(int i = 0; i < 12557; ++i) { // 12257 is the multiples of 0.001 in 4Pi 
			if(MathFunctions.abs(MathFunctions.cos(radians) - Math.cos(radians)) > desiredPrecision) {
				preciseEnough = false;
				break;
			}
			radians += 0.001;
		}
		
		String assertMessage = "Fail at radians = " + radians + "||| value = " + (MathFunctions.cos(radians) - Math.cos(radians));
		assertTrue(preciseEnough, assertMessage);
	}

}
