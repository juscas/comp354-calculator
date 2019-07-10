package sample;

/**
 * This is the exception that is thrown when we don't know what happened and probably can't recover
 * from it.
 * @author Dan
 *
 */
public class SHTFException extends ArithmeticException
{
	 /**
     * Default constructor.
     */
    public SHTFException() {
        super("Unknown error - please try again");
    }

    /**
     * Constructor that lets you set the message.
     * @param message:String
     */
    public SHTFException(String message) {
        super(message);
    }

}
