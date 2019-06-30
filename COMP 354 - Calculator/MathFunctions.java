

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
	 * @param degree: double
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
	public static double nroot(double base, int root, int x) throws ImaginaryNumberException {

		//making sure that when the base is negative, even roots will not be calculated (Imaginary numbers)
		if(root%2 == 0&&base<0) throw new ImaginaryNumberException();

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
	 * Calculates the greatest common divisor .
	 * @param x: double
	 * @param y: double
	 * @return double
	 */
	public static double GCD (double x , double y){
		double gcd=1;
		double positiveX = MathFunctions.abs(x);
		double positiveY = MathFunctions.abs(y);
		for( int i=2;i<positiveX && i<positiveY;i++){
			if(positiveX%i==0 && positiveY%i==0)
				gcd =i;
		}
		return gcd;
	}

	/**
	 * accepts a fraction and returns a simplified one in the from of an array
	 * @param numerator: double
	 * @param denominator: double
	 * @return ans[0] : the simplified numerator , ans[1] : the simplified denominator
	 */
	public static  double[] fractionSimplify (double numerator, double denominator){
		double[] ans = new double[2];

		//calculating the GCD
		double gcd = GCD(numerator,denominator);

		//setting the simplified numerator
		ans[0] = numerator/gcd;
		//setting the simplified denominator
		ans[1] = denominator/gcd;
		return ans;
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
		double result = x[0];
		for(int i = 1; i < x.length; ++i)
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
		for(int i = 1; i < x.length; ++i) {
			if (x[i] == 0) throw new DivideByZeroException();
			result /= x[i];
		}
		return result;
	}
	
	/**
	 * This gives the value of cos(x) where x is in radians.
	 * @param radians: double
	 * @return cos(radians): double
	 */
	public static double cos(double radians) {
		
		double result = 1; // first term of the Taylor expansion of cos(x) is +1
		int accuracy = 6; // how many terms to calculate to (max 6 or else imprecise)
		int posNeg = -1; // used to alternate between +/- in the series.
		
		// get the angles down to a positive multiple of 2Pi since cos is periodic around 2Pi
		radians = mapRadiansToBetween0and2Pi(radians);
		
		// given that the angle is now a multiple of 2Pi, get which quadrant of the cos curve its on
		int quadrantOf2Pi = whatQuadrantOf2Pi(radians);
		
		// These are the mappings from the first quadrant to the appropriate quadrant.
		switch(quadrantOf2Pi) {
			case 1 :
				// do nothing
				break;
			case 2 :
				radians = (MathFunctions.PI - radians);
				break;
			case 3 :
				radians = (radians - MathFunctions.PI);
				break;
			case 4 :
				radians = ((MathFunctions.PI * 2) - radians);
				break;
			default :
				break;
		}
		
		// This is the Taylor expansion of cos(x)
		for(int i = 1; i <= accuracy; ++i) {
			result += posNeg * (MathFunctions.intPower(radians, 2*i) / 
					MathFunctions.factorial(2*i));
			posNeg = -posNeg; // flip sign for the next term
		}
		
		if(quadrantOf2Pi == 2 || quadrantOf2Pi == 3 ) // since cos(x + pi) = -cos(x) we change signs
			result = -result;
		
		return result;
	}
	
	
	/**
	 * This give secant(x) where x is in radians.
	 * @param radians: double
	 * @return secant(x) : double
	 */
	public static double sec(double radians) {
		return 1 / cos(radians);
	}
	
	/**
	 * This gives the value of cos(x) where x is in radians.
	 * @param radians: double
	 * @return cos(radians): double
	 */
	public static double sin(double radians) {
		
		// get the angles down to a positive multiple of 2Pi since cos is periodic around 2Pi
		radians = mapRadiansToBetween0and2Pi(radians);
		
		double result = 0; 
		int accuracy = 6; // how many terms to calculate to (max 6 or else imprecise)
		int posNeg = -1; // used to alternate between +/- in the series.
		
		// The 2nd and 3rd quadrants of the 0 to 2Pi curve lie under the x-axis -> flip sign
		int quadrantOf2Pi = whatQuadrantOf2Pi(radians);
		
		// These are the mappings from the first quadrant to the appropriate quadrant.
		switch(quadrantOf2Pi) {
			case 1 :
				// do nothing
				break;
			case 2 :
				radians = MathFunctions.PI - radians;
				break;
			case 3 :
				radians = radians - (3/2 * MathFunctions.PI) ;
				break;
			case 4 :
				radians = 2 * MathFunctions.PI - radians;
				break;
			default :
				break;
		}
		
		// This is the Taylor expansion of sin(x)
		for(int i = 0; i < accuracy; ++i) {
			posNeg = -posNeg; // flip sign of this, ie. (-1)^(i)
			result += posNeg * ( MathFunctions.intPower(radians, 2*i + 1) / 
					MathFunctions.factorial(2*i+1));
		}
		
		// The 3rd and 4th quadrants of the 0 to 2Pi curve lie under the x-axis -> flip sign
		if(quadrantOf2Pi == 3 || quadrantOf2Pi == 4 )
			result = -result;
		
		return result;
	}
	
	/**
	 * This gives cosecant(x) where x is in radians.
	 * @param radians: double
	 * @return secant(x) : double
	 */
	public static double csc(double radians) {
		return 1 / cos(radians);
	}
	
	/**
	 * This gives tangent(x) where x is in radians.
	 * @param radians: double
	 * @return secant(x) : double
	 */
	public static double tan(double radians) {
		return sin(radians) / cos(radians);
	}
	
	/**
	 * This gives cotangent(x) where x is in radians.
	 * @param radians: double
	 * @return secant(x) : double
	 */
	public static double cot(double radians) {
		return cos(radians) / sin(radians);
	}
	
	/**
	 * So you give this method a radian value and it will convert it into the equivalent radian
	 * value between 0 and 2Pi. It will return what quarter of this your angle is in.
	 * ie. 15 radian is equivalent to 2.43 radians for sin/cos trig functions since they are
	 * periodic on 2Pi. 2.43 falls in the the 2nd quadrant since it is greater than pi/2 but smaller
	 * than pi.
	 * @param radians : double
	 * @return equivalent quadrant of 2Pi: int
	 * @throws SHTFException
	 */
	private static int whatQuadrantOf2Pi(double radians) throws SHTFException {
		
		final double PI_OVER_2 = MathFunctions.PI / 2; // saved because used 4 times
		
		radians = mapRadiansToBetween0and2Pi(radians); // first step is to bring the radians down to 2 Pi.
		
		// This will tell you what quadrant of 2Pi your radians angles are equivalent to.
		if(radians >= 0 && radians <= PI_OVER_2)
			return 1;
		if(radians > PI_OVER_2 && radians <= MathFunctions.PI)
			return 2;
		if(radians > MathFunctions.PI && radians <= 3*PI_OVER_2)
			return 3;
		if(radians > 3*PI_OVER_2 && radians <= 2*MathFunctions.PI)
			return 4;
		
		// Should never happen as first step maps to 0 to 2Pi
		throw new SHTFException("Something went wrong, please try again");
	}
	
	/**
	 * Given an angle in radians, this will return the equivalent angle mapped between 0 and 2*Pi
	 * @param radians: double
	 * @return radians mapped between 0 and 2Pi: double
	 */
	private static double mapRadiansToBetween0and2Pi(double radians) {
		return radians = MathFunctions.abs(radians % (2 * MathFunctions.PI));
	}
	
}
