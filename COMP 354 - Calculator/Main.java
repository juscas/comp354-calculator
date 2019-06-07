import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		//keeps looping until user types exit
		while(true){
		System.out.println("Please input the number for the function:" +
				"\n\t- Addition (...)				-e^ (1)	"+
				"\n\t- Subtraction (...)            -manual       " +
				"\n\t- Multiplication (...)"+
				"\n\t- Division (..)"+
				"\n\t- root (2)"+
				"\n\t- factorial (1)"+
				"\n\t- ln (1) "+
				"\n\t- exit (0)"

		);
		// obtaining the inputed string and splitting it at " "
		String[] splited = input.nextLine().split(" ");

		//converting the function name to lower case
		String function = splited[0].toLowerCase();

		double[] arguments= new double[splited.length-1];
		for (int i = 1; i <=arguments.length ; i++) {
			arguments[i-1]=Double.parseDouble(splited[i]);
		}


		switch (function) {
			case "addition": {
				System.out.println(MathFunctions.add(arguments));
				break;
			}
			case "subtraction": {
				System.out.println(MathFunctions.subtract(arguments));
				break;
			}
			case "multiplication": {
				System.out.println(MathFunctions.multiply(arguments));
				break;
			}
			case "division": {
				System.out.println(MathFunctions.divide(arguments));
				break;

			}
			case "root": {
				if (arguments[1]!=(int)arguments[1]){
					System.out.println("The root has to be an integer");
					break;
				}else {
					System.out.println(MathFunctions.nroot(arguments[0], (int) arguments[1]));
				}
			}
			case "factorial": {
				if (arguments[0]!=(int)arguments[0]) {
					System.out.println("Math error: argument has to be an integer");
					break;
				}else {
					System.out.println(MathFunctions.factorial((int) arguments[0]));
					break;
				}
			}
			case "ln":{
				System.out.println(Transcendentals.ln(arguments[0]));
				break;
			}
			case "e^":{
				System.out.println(Transcendentals.e_to_x(arguments[0]));
				break;
			}
			case "manual":{
				printManual();
				break;
			}
			case "exit": {
				System.exit(0);
			}
		}
		}



	}
	public static void printManual(){
		System.out.println("-Addition (...)\n" +
				"  SYNTAX: addition [Double ...]\n" +
				"  PARAMETER LIMIT: infinit  \n" +
				"  RETURN TYPE: Double \n" +
				"\n" +
				"-Subtraction (...)\n" +
				"  SYNTAX: subtraction [Double ...]\n" +
				"  PARAMETER LIMIT: infinit  \n" +
				"  RETURN TYPE: Double  \n" +
				"\n" +
				"-Multiplication (...)\n" +
				"  SYNTAX: multiplication [Double ...]\n" +
				"  PARAMETER LIMIT: infinit \n" +
				"  DISCRIPTION: Multiplies the all the numbers in the parameters list \n" +
				"  RETURN TYPE: Double \n" +
				"\n" +
				"-Division (...)\n" +
				"  SYNTAX: division [Double ...]\n" +
				"  PARAMETER LIMIT: infinit\n" +
				"  RETURN TYPE: Double \n" +
				"  DUSCRUPTION: Divides the first parameters by the 2nd. The answers of \n" +
				"               that division is divided by the 3rd, which is then \n" +
				"\t       devided by the 4th...\n" +
				"  RETURN TYPE: Double \n" +
				"\n" +
				"-root (2)\n" +
				"  SYNTAX: root [Double] [Integer]\n" +
				"  PARAMETER LIMIT: 2\n" +
				"  DISCRIPTION: Calculates the nth root of the 1st parameter, where n is\n" +
				"               the 2nd parameter\n" +
				"  RETURN TYPE: Double \n" +
				"\n" +
				"-factorial (1)\n" +
				"  SYNTAX: factorial [Long/Integer]\n" +
				"  PARAMETER LIMIT: 1\n" +
				"  DISCRIPTION: Calculates factorial of the 1st parameter\n" +
				"  RETURN TYPE: Long \n" +
				"\n" +
				"-ln (1)\n" +
				"  SYNTAX: ln [Double]\n" +
				"  PARAMETER LIMIT: 1\n" +
				"  DISCRIPTION: Calculates natural logarithm of the 1st parameter\n" +
				"  RETURN TYPE: Double \n" +
				"\n");
	}
}


