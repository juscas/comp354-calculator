package Controller;

import View.Controller;

public class MathErrorException extends ArithmeticException
{
	/**
     * Default constructor.
     */
    public MathErrorException() {
        super("Value is an imaginary number");
    }

    /**
     * Constructor that lets you set the message.
     * @param message:String
     */
    public MathErrorException(String message) {
        super(message);
    }
}
