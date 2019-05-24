
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

	// Babylonian/Newton's Method for approximating square roots (good for smaller numbers, larger number require more iterations to be accuratez)
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

	// ln approximation function based on the area hyperbolic tangent function - more iterations for more accuracy when size of x increases
	static double ln(double x){
		if (x < 0){
			throw new IllegalArgumentException("No values under 0");
		}

		double sum = 0;

		for (int i = 0; i < 1000; i++){
			sum += 1.0/(2*i +1)*MathFunctions.intPower(((x-1)/(x+1)), (2*i + 1));
		}

		return 2 * sum;
	}
}
