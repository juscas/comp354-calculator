public class SyntaxErrorException extends ArithmeticException
{
	/**
	 * Default constructor.
	 */
	public SyntaxErrorException() {
		super("Syntax error");
	}
	
	/**
	 * Constructor that lets you set the message.
	 * @param message:String
	 */
	public SyntaxErrorException(String message) {
		super(message);
	}
}
