package sample;
public class ResultInfinityException extends ArithmeticException
{
	/**
	 * Default constructor.
	 */
	public ResultInfinityException() {
		super("Result is infinity");
	}
	
	/**
	 * Constructor that lets you set the message.
	 * @param message:String
	 */
	public ResultInfinityException(String message) {
		super(message);
	}
	
	
}

