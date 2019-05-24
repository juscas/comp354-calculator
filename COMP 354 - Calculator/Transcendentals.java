
public class Transcendentals
{
	
	static double cos(double x) {
		
		double result = 0.0;
		int precision = 30; // needs to be even
		int negative = 1;
		
		for(int exponent = 0; exponent <= precision; exponent += 2) {
//			System.out.println(exponent);
//			System.out.println(result);
//			System.out.println(negative);
//			System.out.println();
			result += negative * (MathFunctions.intPower(x, exponent) / MathFunctions.factorial(exponent));
			negative = -negative;
		}
		
		return result;
	}
	
}
