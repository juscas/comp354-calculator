

public class MathFunctions
{
	final static double E = 2.7182818284590452;
	final static double PI = 3.141592653589793;
	final static double LN10 = 2.30258509299404568402;
	
	/**
	 * Power function that works ONLY FOR INTEGER EXPONENTS
	 * @param base: double
	 * @param exponent: double
	 * @return x^y : double
	 */
	public static double intPower(double base, int exponent) {
		
		if(exponent == 0) // anything to the exponent 0 is 1 by definition
			return 1;
		if(exponent == 1)
			return base;
		
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
	 * Converts from radians to degrees.
	 * @param radian: double
	 * @return degrees: double
	 */
	public static double radianToDegree(double radian) {
		return radian * (180 / MathFunctions.PI); 
	}
	
	/**
	 * Converts from degrees to radians.
	 * @param degrees: double
	 * @return radians: double
	 */
	public static double degreeToRadian(double degree) {
		return degree * (MathFunctions.PI / 180);
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
	
	/**
	 * Calculates the factorial of a number. Don't go nuts with this - you can wrap around very
	 * quickly so limit the size of these.
	 * @param n: long
	 * @return long
	 */
	public static long factorial(long n) {
		if(n < 0)
			throw new IllegalArgumentException("No negative values allowed");
		int fact = 1;
	    for (int i = 2; i <= n; i++)
	        fact = fact * i;
	    return fact;
	}
	
		/**
	 * Returns the absolute value of a byte.
	 * @param x: byte
	 * @return absolute value of x: byte
	 */
	public static byte abs(byte x) {
		if(x < 0)
			return (byte) x;
		return (byte) x;
	}
	
	/**
	 * Returns the absolute value of a short.
	 * @param x: short
	 * @return absolute value of x: short
	 */
	public static short abs(short x) {
		if(x < 0)
			return (short) x;
		return (short) x;
	}
	
	/**
	 * Returns the absolute value of an int.
	 * @param x: int
	 * @return absolute value of x: int
	 */
	public static int abs(int x) {
		if(x < 0)
			return -x;
		return x;
	}
	
	/**
	 * Returns the absolute value of a long.
	 * @param x: long
	 * @return absolute value of x: long
	 */
	public static long abs(long x) {
		if(x < 0)
			return -x;
		return x;
	}
	
	/**
	 * Returns the absolute value of a float.
	 * @param x: float
	 * @return absolute value of x: float
	 */
	public static float abs(float x) {
		if(x < 0)
			return -x;
		return x;
	}
	
	/**
	 * Returns the absolute value of a double.
	 * @param x: double
	 * @return absolute value of x: double
	 */
	public static double abs(double x) {
		if(x < 0)
			return -x;
		return x;
	}

	
	/**
	 * returns the nth root of the base value. 3rd parameter x is for accuracy, higher x = higher accuracy
	 * @param base: double
	 * @param root: int
	 * @param x: int
	 * @return the nth root of the base number
	 */
	public static double nroot(double base, int root, int x) {

		//initial random guess is set to 5
		double aprx = 5;

		// setting the default accuracy level
		double acc = 0.01;

		// decreases the acc variable, which increases accuracy
		for(int i=0;i<x;i++){
			acc=acc/10;
		}

		// initializing difference to some high number, higher than acc
		double difference = 10;

		//initialising betterAprx
		double betterAprx = 0.0;

		// loop until difference is less than acc
		while (difference > acc) {
			// calculating current value from previous
			// value by newton's method
			betterAprx = ((root - 1.0) * aprx +
					(double) base / MathFunctions.intPower(aprx, root - 1))/ root;
			difference = abs(betterAprx - aprx);
			aprx = betterAprx;
		}
		return aprx;
	}

	/**
	 * Wrapper function for nroot(3) that takes only 2 parameters instead of 3. The 3rd parameter is set to a default of 0.
	 * @param base: double
	 * @param root: int
	 * @return the nth root of the base number
	 */
	public static double nroot (double base, int root){
		return nroot(base,root,0);
	}
	
	/**
	 * Calculates the binomial coefficient (aka. "n choose K" or "nCk").
	 * @param n: long
	 * @param k: long
	 * @return n choose k: long
	 */
	public static long binomialCoefficient(long n, long k) {
		
		if(k > n || n <= 0 || k < 0)
			throw new ArithmeticException("Binomial coefficient undefined for these parameters");
		if(n == k || k == 0)
			return 1; // this is true for all 'n choose n' OR where k = 0
		if(k == 1)
			return k; // this is true for all 'n choose 1'
		
		// this is an optimization that cancels out the n! and the k! (2 less factorials to compute)
		// ex. 10C7 -> 10! / 7!(10-7)! -> 10!/7! = 10*9*8 = 720
		long numerator = 1;
		for(long i = k+1; i <= n; ++i) {
			numerator *= i;
		}
		
		return(numerator / MathFunctions.factorial(n-k));
	}
	
	/**
	 * Calculates the binomial coefficient (n choose K, nCk).
	 * @param n: int
	 * @param k: int
	 * @return n choose k: int
	 */
	public static long binomialCoefficient(int n, int k) {
		return binomialCoefficient((long) n, (long) k);
	}
	
	/**
	 * Adds variable amount of numbers.
	 * @param x: double
	 * @return result: double
	 */
	public static double add(double ... x) {
		double result = 0;
		for(int i = 0; i < x.length; ++i)
			result += x[i];
		return result;
	}
	
	/**
	 * Subtracts variable amount of numbers.
	 * @param x: double
	 * @return result: double
	 */
	public static double subtract(double ... x) {
		double result = x[0];
		for(int i = 1; i < x.length; ++i)
			result -= x[i];
		return result;
	}
	
	/**
	 * Multiplies variable amount of numbers.
	 * @param x: double
	 * @return result: double
	 */
	public static double multiply(double ... x) {
		double result = x[0];
		for(int i = 1; i < x.length; ++i)
			result *= x[i];
		return result;
	}
	
	/**
	 * Divides variable amount of numbers.
	 * @param x: double
	 * @return result: double
	 */
	public static double divide(double ... x) {
		double result = x[0];
		for(int i = 1; i < x.length; ++i)
			result /= x[i];
		return result;
	}
}
