import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {


		Scanner input = new Scanner(System.in);
		String manStr=null;		//String parameter for the manual request
		String[] splited;	//raw input string seperated by " "
		String function;	//String variable for the function name
		double[] arguments; //String array of arguments given after the function name

		//keeps looping until user types exit
		while(true){
		System.out.println("Please input the name of the function followed by the parameters (separated by spaces): (you can type 'man [FunctionName] to get more information about the function)" +
				"\n\t- Addition (...)				-e^ (1)"+
				"\n\t- Subtraction (...)             -power (2)"+
				"\n\t- Multiplication (...)						"+
				"\n\t- Division (..)			  				"+
				"\n\t- root (2)										"+
				"\n\t- factorial (1)								"+
				"\n\t- ln (1) 										"+
				"\n\t- exit (0)"

		);
		// obtaining the inputed string and splitting it at " "
		splited = input.nextLine().split(" ");

		//converting the function name to lower case
		function = splited[0].toLowerCase();

		arguments= new double[splited.length-1];


		for (int i = 1; i <=arguments.length ; i++) {
			try {
				arguments[i-1]=Double.parseDouble(splited[i]);
			}catch (NumberFormatException e){
				manStr=splited[i];
			}
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
			case "power":
				System.out.println(Transcendentals.power(arguments[0],arguments[1]));
				break;
			case "man":{
				switch (manStr){
					case "addition":{
						System.out.println(manual[0]);
						break;
					}
					case "subtraction":{
						System.out.println(manual[1]);
						break;
					}
					case "multiplication":{
						System.out.println(manual[2]);
						break;
					}
					case "division":{
						System.out.println(manual[3]);
						break;
					}
					case "root":{
						System.out.println(manual[4]);
						break;
					}
					case "factorial":{
						System.out.println(manual[5]);
						break;
					}
					case "ln":{
						System.out.println(manual[6]);
						break;
					}
					case "e^":{
						System.out.println(manual[7]);
						break;
					}
					case "power":{
						System.out.println(manual[8]);
						break;
					}
					default:{
						System.out.println("Could not find the function");
					}

				}
				break;
			}
			case "exit": {
				System.exit(0);
			}
			default:{
				System.out.println("Could not find the function");
			}
		}
		}



	}
	public static String[] manual = {"-Addition (...)\n" +
			"  SYNTAX: addition [Double ...]\n" +
			"  PARAMETER LIMIT: infinit  \n" +
			"  RETURN TYPE: Double \n" +
			"\n","-Subtraction (...)\n" +
			"  SYNTAX: subtraction [Double ...]\n" +
			"  PARAMETER LIMIT: infinit  \n" +
			"  RETURN TYPE: Double  \n" +
			"\n","-Multiplication (...)\n" +
			"  SYNTAX: multiplication [Double ...]\n" +
			"  PARAMETER LIMIT: infinit \n" +
			"  DESCRIPTION: Multiplies the all the numbers in the parameters list \n" +
			"  RETURN TYPE: Double \n" +
			"\n","-Division (...)\n" +
			"  SYNTAX: division [Double ...]\n" +
			"  PARAMETER LIMIT: infinit\n" +
			"  RETURN TYPE: Double \n" +
			"  DESCRUPTION: Divides the first parameters by the 2nd. The answers of \n" +
			"               that division is divided by the 3rd, which is then \n" +
			"\t       devided by the 4th...\n" +
			"  RETURN TYPE: Double \n" +
			"\n","-root (2)\n" +
			"  SYNTAX: root [Double] [Integer]\n" +
			"  PARAMETER LIMIT: 2\n" +
			"  DESCRIPTION: Calculates the nth root of the 1st parameter, where n is\n" +
			"               the 2nd parameter\n" +
			"  RETURN TYPE: Double \n" +
			"\n","-factorial (1)\n" +
			"  SYNTAX: factorial [Long/Integer]\n" +
			"  PARAMETER LIMIT: 1\n" +
			"  DESCRIPTION: Calculates factorial of the 1st parameter\n" +
			"  RETURN TYPE: Long \n" +
			"\n","-ln (1)\n" +
			"  SYNTAX: ln [Double]\n" +
			"  PARAMETER LIMIT: 1\n" +
			"  DESCRIPTION: Calculates natural logarithm of the 1st parameter\n" +
			"  RETURN TYPE: Double \n" +
			"\n","-e^ (1)\n" +
			"  SYNTAX: e^ [Double]\n" +
			"  PARAMETER LIMIT: 1\n" +
			"  DESCRIPTION: Calculates eulers number to the power of 1st parameter\n" +
			"  RETURN TYPE: Double","-power (2)\n" +
			"  SYNTAX: e^ [Double, Double]\n" +
			"  PARAMETER LIMIT: 2\n" +
			"  DESCRIPTION: Calculates parameter 1 to the power of parameter 2\n" +
			"  RETURN TYPE: Double "};

}


