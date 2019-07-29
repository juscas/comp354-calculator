import java.util.Formatter;

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
	 * Calculates the factorial of a number. Don't go nuts with this - you can wrap around very
	 * quickly so limit the size of these.
	 * @param n: long
	 * @return long
	 */
	public static double factorial(double n) {
		if(n - (int) n > 0)
			throw new SyntaxErrorException("Factorial only defined for integers");
		if(n < 0)
			throw new IllegalArgumentException("No negative values allowed");
		int fact = 1;
		for (int i = 2; i <= n; i++)
			fact = fact * i;
		return fact;
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

		double epsilon = 0.001;

		//
		boolean negative = root<1;
		if(MathFunctions.abs(root - 0.5) < epsilon)	// if you call this with 1/2 then just call simple sqrt() function.
			return squareRoot(x);

		//making sure that when the base is negative, even roots will not be calculated (Imaginary numbers)
		if(root%2 == 0&&base<0) throw new ImaginaryNumberException("Imaginary solutions unsupported");

		if(negative) root=root*-1;

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
					(double) base / MathFunctions.exponentBySquaring(aprx, root - 1))/ root;
			difference = abs(betterAprx - aprx);
			aprx = betterAprx;
		}
		return negative? (1/aprx):aprx;
	}

	/**
	 * Wrapper function for nroot(3) that takes only 2 parameters instead of 3. The 3rd parameter is set to a default of 0.
	 * @param base: double
	 * @param root: int
	 * @return the nth root of the base number
	 */
	public static double nroot (double base, int root){
		return nroot(base,root,10);
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
		double radiansMapped = mapRadiansToBetween0and2Pi(radians);

		double result = 0;
		int accuracy = 6; // how many terms to calculate to (max 6 or else imprecise)
		int posNeg = -1; // used to alternate between +/- in the series.

		// The 2nd and 3rd quadrants of the 0 to 2Pi curve lie under the x-axis -> flip sign
		int quadrantOf2Pi = whatQuadrantOf2Pi(radiansMapped);

		// These are the mappings from the first quadrant to the appropriate quadrant.
		switch(quadrantOf2Pi) {
			case 1 :
				// do nothing
				break;
			case 2 :
				radiansMapped = MathFunctions.PI - radiansMapped;
				break;
			case 3 :
				radiansMapped = radiansMapped - (3/2 * MathFunctions.PI) ;
				break;
			case 4 :
				radiansMapped = 2 * MathFunctions.PI - radiansMapped;
				break;
			default :
				break;
		}

		// This is the Taylor expansion of sin(x)
		for(int i = 0; i < accuracy; ++i) {
			posNeg = -posNeg; // flip sign of this, ie. (-1)^(i)
			result += posNeg * ( MathFunctions.intPower(radiansMapped, 2*i + 1) /
					MathFunctions.factorial(2*i+1));
		}

		// The 3rd and 4th quadrants of the 0 to 2Pi curve lie under the x-axis -> flip sign
		if(quadrantOf2Pi == 3 || quadrantOf2Pi == 4 )
			result = -result;

		if(quadrantOf2Pi == 2 && radians < 0)
			return -result;
		
		
		return result;
	}

	/**
	 * This gives cosecant(x) where x is in radians.
	 * @param radians: double
	 * @return secant(x) : double
	 */
	public static double csc(double radians) {
		return 1 / sin(radians);
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
	private static int whatQuadrantOf2Pi(double radians) throws MathErrorException {

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
		throw new MathErrorException("Something went wrong, please try again");
	}

	/**
	 * Given an angle in radians, this will return the equivalent angle mapped between 0 and 2*Pi
	 * @param radians: double
	 * @return radians mapped between 0 and 2Pi: double
	 */
	private static double mapRadiansToBetween0and2Pi(double radians) {
		return radians = MathFunctions.abs(radians % (2 * MathFunctions.PI));
	}

	/**
	 * Babylonian/Newton's Method for approximating square roots
	 * (good for smaller numbers, larger number require more iterations to be accurate)
	 * @param x:double
	 * @return square root of x:double
	 */
	static double squareRoot(double x) {

		if (x == 0){
			return 0.0;
		}
		if (x < 0){
			throw new IllegalArgumentException("No negative values allowed");
		}

		// first approximation
		double app = 0.5 * x;
		double moreAccurateApp = 0.0;
		for (int i = 0; i < 100; i++){
			moreAccurateApp = 0.5 * (app + x/app);
			app = moreAccurateApp;
		}
		return moreAccurateApp;
	}

	/**
	 * ln function that is more accurate the more iterations that are done. Currently set to 1000 iterations.
	 * @param x: double
	 * @return ln(x) : double
	 */
	static double ln(double x){
		if (x <= 0){
			throw new MathErrorException("No values under or equal 0");
		}
		int precision = 1000;

		// Change this in the future to figure out something more efficient for precision
		if (x > 400){
			precision = 5000;
		}

		double sum = 0;

		for (int i = 0; i < precision; i++){
			sum += 1.0/(2*i +1)*MathFunctions.intPower(((x-1)/(x+1)), (2*i + 1));
		}

		return 2 * sum;
	}

	/**
	 * This returns the logarithm base 10 of a number.
	 * @param x : double
	 * @return log10(x) : double
	 */
	public static double log(double x) {

		if (x <= 0){
			throw new MathErrorException("No values under or equal 0");
		}

		if(x == 1){
			throw new MathErrorException("Log of 1 is Infinity.");
		}

		// this is because the algorithm for log10 only works for x >= 1 (but it is infinitely precise)
		if(x < 1)
			return log(10, x);
		
		
		String answer = "";
		int signicantDigits  = 15; // how many numbers after the decimal

		for(int i = 0; i < signicantDigits; ++i) {

			int exponent = nearestPower10(x); // this gets the digits from left to right

			answer = answer + exponent; // concatenated the successive digits into a string

			if(i == 0) // for first pass, add a decimal mark
				answer = answer + ".";

			x = x / MathFunctions.intPower(10.0, exponent);

			x = MathFunctions.intPower(x, 10);
		}

		return Double.parseDouble(answer);
	}
	/**
	 * This returns the logarithm of a number with user defined base.
	 * @param base : double
	 * @param number : double
	 * @return log10(x) : double
	 */
	public static double log(double base, double number) {
		
		double denominator;
		
		if(base == 10)
			denominator = 2.30258509299404568402; // ln(10) hardcoded
		else if(base == E)
			denominator = 1; // ln(e) hardcoded
		else
			denominator = ln(base);
			
			System.out.println("number = " + number);
			System.out.println("ln = " + ln(number));
			System.out.println("denominator = " + denominator);
		
		return ln(number) / denominator; // this is just the change of base formula for logarithms.
	}
	/**
	 * Helper function for calculating the log10. This will return the nearest power of 10 that
	 * divides into a number. This is necessary for the algorithm.
	 * @param x : double
	 * @return nearest power of 10 : int
	 */
	private static int nearestPower10(double x) {
		if(x < 0)
			throw new MathErrorException("Logarithms undefined for negative numbers");

		int exponent = 0;

		for(exponent = 1; exponent <= x; ++exponent) {
			if (MathFunctions.intPower(10, exponent) > x)
				break;
		}
		return (exponent - 1);
	}

	/**
	 * Returns the result of base raised to the exponent (ie. base^exponent).
	 * @param base : double
	 * @param exponent : double
	 * @return b^e : double
	 */
	static double power(double base, double exponent){

		//if the exponent value is an integer, we perform the simple power function in MathFunctions class
		if(exponent==(int)exponent)
			return MathFunctions.intPower(base,(int)exponent);

		int integerExp;			//integer part of the exponent
		double decimalExp;			//decimal part of the exponent
		double integerValue;	//value of the base number to the power of integerExp (i.e. just the integer part of the exponent)
		double decimalValue;    //value of the base number to the power of decimalExp (i.e. just the decimal part of the exponent)
		double decimallength = 1;	//value of the denominator when the decimal is converted into a fraction
		double positiveBase = MathFunctions.abs(base);

		//converting the exponent number into array of 2 strings. 1 containing the integer part, the other containing the decimal part
		//the integer String is converted into an int again. The decimal part will be trimmed of any useless 0s on the right
		integerExp = Integer.parseInt(String.valueOf(exponent).split("\\.")[0]);


		//the decimal part of the given exponent, still in String form
		String tempDecimal = String.valueOf(exponent).split("\\.")[1];

		//if the base is negative, and the power is any number where the first decimal number is an odd integer, then it throws an exception
		// (this is a math error)
		if(base<0&&Integer.parseInt(tempDecimal.substring(0,1))%2!=0) throw new ImaginaryNumberException();

		//removing any 0s from the right side of the decimal value
		while(tempDecimal.charAt(tempDecimal.length()-1)==0){
			tempDecimal = tempDecimal.substring(0,tempDecimal.length()-1);
		}

		/*
		//if the decimal length is > 6 we trim the value to 6 decimal points, since more than that takes a heavy toll on the function
		if(tempDecimal.length()>6) tempDecimal=tempDecimal.substring(0,6);
		*/

		//to convert a decimal into a fraction form, we have to have it in the form of number/10^n
		//this for loop calculates the denominator (i.e. the actual value of 10^n)
		for(int i=0;i<tempDecimal.length();i++){
			decimallength=decimallength*10;
		}

		//converting the refined decimal number back to int
		decimalExp = Double.parseDouble(tempDecimal);


		//using the power function from class MathFunctions to calculate the value of the base number to the power of the integer
		//part of the exponent
		integerValue = MathFunctions.exponentBySquaring(base,integerExp);

		//if the exponent provided is negative, then so should the decimal part of the exponent
		if(exponent<0) decimalExp = decimalExp*-1;

		//when the base is negative, we have to check for imaginary numbers
		if(base<0){

			double[] temp =  MathFunctions.fractionSimplify(decimalExp,decimallength);
			decimalExp = (int) temp[0];
			decimallength = (int) temp[1];

			//if the denominator of the fraction (which is derived from the decimal part of the exponent) is even,
			// then the result would be an imaginary number
			if(decimallength%2==0) throw new ImaginaryNumberException();
		}

		//calculation of base^(1/decimallength) is estimated using Newton's method
		//in this function we're applying the equation (base^(1/decimallength))^decimalExp which is = to base^(decimalExp/decimallength)
		decimalValue = MathFunctions.exponentBySquaring(MathFunctions.upgradedRoot(base,decimallength),decimalExp);

		return integerValue*decimalValue;
	}

	/**
	 * Calculates e^x.
	 * @param x: double
	 * @return e^x: double
	 */
	public static double e_to_x(double x) {
		/*
		 * ex. say x = 15.73. This is too far from 0 to give a good Taylor approximation so we divide
		 * by 'e' to get quotient = 15. The decimalExponent is 0.73. We will now run the Taylor expansion
		 * on the decimalExponent. After that we will add back the e^15 to that result.
		 *
		 * note: e^15 = e^15 * e^0.73
		 */

		// A few guard clauses that return well know exact values of e^x
		if(x == 0) return 1;
		if(x == 1) return MathFunctions.E;
		if(x == -1) return 1 / MathFunctions.E;
		if(x == 2) return MathFunctions.E * MathFunctions.E;

		int precision = 13; // 13 gives lowest sum of square error for values between 0 and 1.
		double result = 0.0;
		
		int integerExponent = (int) x; // retain the integer part of the exponent
		double decimalExponent = x;
		
		// if |x| is < 1
		if(integerExponent != 0)
			decimalExponent = x % integerExponent; // retain the exponent part
		
		// This is the Taylor expansion of e^decimalExponent around 0
		for(int i = 0; i < precision; ++i)
			result += MathFunctions.intPower(decimalExponent, i) / MathFunctions.factorial(i);
		
		// add the appropriate multiples of E back to the Taylor expansion of the remainder.
		if(abs(x) >= 2)
			result = result * MathFunctions.intPower(MathFunctions.E, integerExponent);
		
		return result;
	}

	/**
	 * Returns the hyperbolic sine of a number.
	 * @param x: double
	 * @return sinh(x): double
	 */
	public static double sinh(double x){
		return (e_to_x(x) - e_to_x(-x)) / 2;
	}

	/**
	 * Returns the hyperbolic cosine of a number.
	 * @param x: double
	 * @return cosh(x): double
	 */
	public static double cosh(double x){
		return (e_to_x(x) + e_to_x(-x)) / 2;
	}

	/**
	 * Returns the higher value of the 2 parameters
	 * if the parameters are equal, it returns the first parameter
	 * @param x: double
	 * @param y: double
	 * @return double
	 */
	public  static  double max(double x, double y){
		if(y > x){
			return y;
		}else 
			return x;
	}

	/**
	 * Returns the lower value of the 2 parameters
	 * if the parameters are equal, it returns the 2nd parameter
	 * @param x: double
	 * @param y: double
	 * @return double
	 */
	public  static  double min(double x, double y){
		if(x<y){
			return x;
		}else return y;
	}
	/**
	 * Same as the intPower function except this one is more efficient
	 * @param base: double
	 * @param exponent: double
	 * @return double
	 */
	public static double exponentBySquaring(double base, double exponent){

		if(exponent==0) return 1;

		boolean negative = exponent < 0;
		//in case of negative exponents, set the base = 1/base
		if(negative){
			exponent = exponent * -1;
		}
		//initialising remainder =1
		double r = 1;

		while (exponent > 1){
			if (exponent % 2 == 0){
				base = base * base;
				exponent = exponent / 2;
			}
			else {
				r = base * r;
				base = base * base;
				exponent = (exponent - 1) / 2;
			}
		}
		if(negative){
			return 1 / (base * r);
		}else{
			return base * r;
		}
	}
	/*public static double roundToN(Double number, int index){
		String temp = number.toString();
		int Eindex = temp.indexOf("E");
		if(Eindex !=-1){
			String str;
			Formatter test = new Formatter();
			//figuring out how many times we have to shift the decimal point
			int shiftNumber =Integer.parseInt(temp.substring(Eindex+1));
		}
		return 0.0;
	}*/

	/**
	 * Does the same thing as the nroot function, except in case of a big exponent, this function calculates the root in small intervals which increases performance
	 * Only factors of 10 should be used for exponents
	 * @param base double
	 * @param exponent double : must be divisible by 10
	 * @return double
	 */
	private static double upgradedRoot(double base, double exponent){

		//getting the log(base 10) of the exponent
		double log = log(exponent);

		//obtaining the integer of the log
		int intLog = (int) log;


		double decimalLog = log - (intLog);

		//setting the initial integer value = to base
		double intVal=base;

		//calculating the roots in intervals
		for(int i =0 ; i< intLog ; i++){
			intVal = nroot(intVal,10,10);
		}


		if(decimalLog>0){
			return power(intVal,decimalLog);
		}else{
			return intVal;
		}

	}
}
