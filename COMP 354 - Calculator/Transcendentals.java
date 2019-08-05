public class Transcendentals
{
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
			throw new IllegalArgumentException("No values under or equal 0");
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
	
	//(needs testing for accuracy) power function for decimal/fractional exponents
	static double power(double base, double exponent){

		//if the exponent value is an integer, we perform the simple power function in MathFunctions class
		if(exponent==(int)exponent)
			return MathFunctions.intPower(base,(int)exponent);

		int integerExp;			//integer part of the exponent
		int decimalExp;			//decimal part of the exponent
		double integerValue;	//value of the base number to the power of integerExp (i.e. just the integer part of the exponent)
		double decimalValue;    //value of the base number to the power of decimalExp (i.e. just the decimal part of the exponent)
		int decimallength = 1;	//value of the denominator when the decimal is converted into a fraction

		//converting the exponent number into array of 2 strings. 1 containing the integer part, the other containing the decimal part
		//the integer String is converted into an int again. The decimal part will be trimmed of any useless 0s on the right
		integerExp = Integer.parseInt(String.valueOf(exponent).split("\\.")[0]);

		//the decimal part of the given exponent, still in String form
		String tempDecimal = String.valueOf(exponent).split("\\.")[1];

		//removing any 0s from the right side of the decimal value
		while(tempDecimal.charAt(tempDecimal.length()-1)==0){
			tempDecimal = tempDecimal.substring(0,tempDecimal.length()-1);
		}

		//to convert a decimal into a fraction form, we have to have it in the form of number/10^n
		//this for loop calculates the denominator (i.e. the actual value of 10^n)
		for(int i=0;i<tempDecimal.length();i++){
			decimallength=decimallength*10;
		}

		//converting the refined decimal number back to int
		decimalExp = Integer.parseInt(tempDecimal);

		//if the exponent provided is negative, then so should the decimal part of the exponent
		if(exponent<0) decimalExp = decimalExp*-1;

		//using the power function from class MathFunctions to calculate the value of the base number to the power of the integer
		//part of the exponent
		integerValue = MathFunctions.intPower(base,integerExp);

		//calculation of base^(1/decimallength) is estimated using Newton's method
		//in this function we're applying the equation (base^(1/decimallength))^decimalExp which is = to base^(decimalExp/decimallength)
		decimalValue = MathFunctions.intPower(MathFunctions.nroot(base,decimallength,10),decimalExp);

		return integerValue*decimalValue;
	}
		
	/**
	 * Calculates e^x.
	 * @param x: double
	 * @return e^x: double
	 */
	public static double e_to_x(double x) {
		
		double result = 0;
		int precision = 13; // 13 matches most calculators. DO NOT EXCEED 20.
		
		// This is the Taylor expansion of e^x.
		for(int i = 0; i < precision; ++i)
			result += MathFunctions.intPower(x, i) / MathFunctions.factorial(i);
		
		return result;
	}
	
	/**
	 *
	 * UNTESTED AND DEFINITELY BROKEN
	 * Calculates cos(x) provided x is in radians.
	 * @param x: double
	 * @return cos(x):double
	 */
	public static double cos(double x) {
		
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
	
	/**
	 * BROKEN - DON'T KNOW WHY :(
	 * Returns sin(x). Taylor expansion centered around 0.
	 * @param x: double
	 * @return sin(x): double
	 */
	public static double sin(double x) {
		double result = 0;
		int precision = 15; // can change this for greater precision by increasing
		int plusMinus = 1; // flips between negative and positive (leave at 1)
		
		for(int i = 1; i <= precision; ++i) {
			result += MathFunctions.intPower(x, (2*i) - 1) / MathFunctions.factorial((2*i) - 1);
			result *= plusMinus;
			plusMinus = -plusMinus;
		}
		
		return result;
	}
	
	/**
	 * UNTESTED!!!
	 * Calculates arcsin of x.
	 * 
	 * @param x: double
	 * @return arcsin(x): double
	 */
	public static double arcsin(double x) {
		double result = 0;
		
		for(int i = 0; i < 50; ++i) {
			result += (2/(2*i + 1)*MathFunctions.binomialCoefficient(2*i, i) *
					MathFunctions.intPower(x/2, 2*i + 1));
		}
		
		return result;
	}
		
}

