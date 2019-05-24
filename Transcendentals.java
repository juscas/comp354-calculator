
public class Transcendentals
{
	
	static double sin(double x) {
		
		double result = 0.0;
		int precision = 12; // needs to be even
		int negative = 1;
		
		for(int exponent = 0; exponent <= precision; exponent += 2) {
			result += negative * (MathFunctions.power(x, exponent) / MathFunctions.factorial(exponent));
			negative = -negative;
		}
		
		return x;
	}
	
}
