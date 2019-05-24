

public class MathFunctions
{
	final static double e = 2.7182818284590452;
	final static double pi = 3.141592653589793;
	
	public static double power(double base, int exponent) {
		
		double result = base;
		for(int i = 1; i < exponent; ++i)
			result *= base;
		return result;
	}
	
	public static int factorial(int n) {
		int fact = 1;
	    for (int i = 2; i <= n; i++)
	        fact = fact * i;
	    return fact;
	}
}
