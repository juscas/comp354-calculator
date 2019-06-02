public class Main
{
	public static void main(String[] args) {

		
		
		
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
	
	private static void sinTest(double number) {
		System.out.println("Testing the sin(x) function vs the stock java.Math.sin(x) functions:");
		for(int i = 0; i < 100; ++i) {
			System.out.println("number = " + number + "|| answer = " + (Transcendentals.sin(number) - Math.sin(number)));
			number += 0.1;
		}
	}
}

