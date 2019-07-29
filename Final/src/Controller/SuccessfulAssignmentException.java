package Controller;

/**
 * This is a bit of a weirdo. Up until now, all messages (SyntaxErrors) were caught and displayed
 * by the calculator and they triggered a change of color to the GUI.
 * 
 * This Exception is the same but it is to signify a successful assignment of an operator. It is 
 * caught by the GUI and flashes a happy color (instead of red).
 *
 */
public class SuccessfulAssignmentException extends ArithmeticException
{
	/**
     * Default constructor.
     */
    public SuccessfulAssignmentException() {
        super("Successful assignment");
    }

    /**
     * Constructor that lets you set the message.
     * @param message:String
     */
    public SuccessfulAssignmentException(String message) {
        super(message);
    }
}
