public class Main
{
	public static void main(String[] args) {
		
		double number = -5.0;

		for(int i = 0; i < 100; ++i) {
			//System.out.println(Transcendentals.cos(number) - Math.cos(number));
			number += 0.1;
		}

		System.out.println(MathFunctions.nroot(54,5));

		//testSquareRoot(100);

		//System.out.println(Transcendentals.ln(200));
		

	} // ***END OF main***

	private static void testCos(double number) {
		for(int i = 0; i < 100; ++i) {
			System.out.println(Transcendentals.cos(number) - Math.cos(number));
			number += 0.1;
		}
	}

	private static void testSquareRoot(double number) {
		System.out.println("Testing square roots from " + number + " to " + (number + 99));
		for(int i = 0; i < 100; ++i) {
			System.out.println("Square root of " + number + " is " + Transcendentals.squareRoot(number));
			number+=1;
		}
	}
}

