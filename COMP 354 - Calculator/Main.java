

public class Main
{
	public static void main(String[] args) {
		
		double number = -5.0;

		for(int i = 0; i < 100; ++i) {
			System.out.println(Transcendentals.cos(number) - Math.cos(number));
			number += 0.1;
		}
		

	} // ***END OF main***

	private static void testCos(double number) {
		for(int i = 0; i < 100; ++i) {
			System.out.println(Transcendentals.cos(number) - Math.cos(number));
			number += 0.1;
		}
	}

}

