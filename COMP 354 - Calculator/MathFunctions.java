

public class MathFunctions
{
	final static double e = 2.7182818284590452;
	final static double pi = 3.141592653589793;
	
	
	/**
	 * Power function that works ONLY FOR INTEGER EXPONENTS
	 * @param base
	 * @param exponent
	 * @return
	 */
	public static double intPower(double base, int exponent) {
		
		
		if(exponent == 0) // anything to the exponent 0 is 1 by definition
			return 1;
		
		double result = base;
		int posExponent = exponent; // can be used to make a negative exponent temporarily positive.
		
		if(exponent < 0) // if neg. exponent, use positive then divide 1/result at end.
			posExponent = -exponent;
		
		// this multiplies by the base by the amount of times of the exponent.
		for(int i = 1; i < posExponent; ++i)
			result *= base;
		
		if(exponent < 0) // if the exponent is negative then return 1/result.
			return 1 / result;
		
		return result;
	}
	
	
	/**
	 * Calculates the factorial of a number.
	 * @param n: int
	 * @return long
	 */
	public static long factorial(int n) {
		if(n < 0)
			throw new IllegalArgumentException("No negative values allowed");
		int fact = 1;
	    for (int i = 2; i <= n; i++)
	        fact = fact * i;
	    return fact;
	}
}
