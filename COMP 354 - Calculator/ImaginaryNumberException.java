public class ImaginaryNumberException extends ArithmeticException {

    /**
     * Default constructor.
     */
    public ImaginaryNumberException() {
        super("Value is an imaginary number");
    }

    /**
     * Constructor that lets you set the message.
     * @param message:String
     */
    public ImaginaryNumberException(String message) {
        super(message);
    }

}
