


public class DivideByZeroException extends ArithmeticException
{
	/**
	 * Default constructor.
	 */
	public DivideByZeroException() {
		super("Division by zero");
	}
	
	/**
	 * Constructor that lets you set the message.
	 * @param message
	 */
	public DivideByZeroException(String message) {
		super(message);
	}
}
