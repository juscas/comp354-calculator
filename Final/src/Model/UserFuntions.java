package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class will hold all the functionality needed for users to save their own custom functions.
 * 
 * Limitations: 
 * 1)	The same function can only be defined once (cannot have same name with different arity)
 * 			ex. it is not permitted to have log(x) and log(x, y). The last one inserted overrides 
 * 			the other.
 * 2)	The function inputed must be composed of the functions defined in class MathFunctions.
 * 
 */
public class UserFuntions implements Serializable
{
	/**
	 * Some serialUID (randomly generated)
	 */
	private static final long serialVersionUID = 2L;

	/*
	 * This is a function. It is defined by its arity and its String mathematical expression.
	 */
	private static class Function
	{
		private int arity;
		private String expression;
		
		public Function(int arity, String expression) {
			this.arity = arity;
			this.expression = expression;
		}
		
		public int getArity() {
			return arity;
		}
		
		public String getExpression() {
			return expression;
		}
		
		
	} // End of nested class
	
	/*
	 * This is the map that holds the functions. You search by function name. (ie. "log")
	 */
	private static Map<String, Function> customFunctions = new HashMap<String, Function>();
	
	
	/**
	 * Given the function name (ie. "log") this will return the String expression associated to that
	 * function so that it can later be passed to the Parser and resolved to a number.
	 * 
	 * Assumption: this assumes that 
	 * 
	 * @param functionName: String
	 * @return expression associate to the functionName
	 */
	public static Function getFunction(String functionName) {
		
		return customFunctions.get(functionName);
	}
	
	
	/**
	 * This is the first step: to parse the user's expression and ensure that it is of the correct
	 * form for a user function (see Regex pattern).
	 * 
	 * Note: this does not yet validate the RHS expression for syntactical correctness. This 
	 * step will be done by another function.
	 * 
	 * Return a Matcher object where capture group n is:
	 * 	0) entire String
	 * 	1) the functions name
	 * 	2) the parameter list (with commas)
	 * 	3) the RHS of the "=" sign (un-validated expression)
	 * 
	 * @param function
	 * @return
	 */
	public static Matcher parseUserFunction(String function) {
		
		// These hold the different parts of regex (separated for easy maintenance)
		String functionName = "[a-zA-Z][a-zA-Z]+";
		String parameters = "(?:[a-z],\\s*){0,3}[a-z]";
		String RHS_Expression = ".*";
		
		// This regex captures: 1) name, 2) parameter list, 3) RHS math expression
		String validUserFunctionRegex = "^" + "(" + functionName + ")" +
				"\\(" + "(" +  parameters + ")" + "\\)" + 
				"\\s*=\\s*" +
				"(" + RHS_Expression +")";
		
		Pattern pattern = Pattern.compile(validUserFunctionRegex);
		Matcher matcher = pattern.matcher(function);
		matcher.find();
		
		// This checks each capture group and if not found, throws an appropriate Syntax Error
		try {
		matcher.group(1);
		} catch (IllegalStateException e) {
			throw new Controller.SyntaxErrorException("Error: illegal function name. hint: \"gcd(\"");
		}
		try {
			matcher.group(2);
		} catch (IllegalStateException e) {
			throw new Controller.SyntaxErrorException("Error: parameters invalid. hint: \"(a, b, c)\"");
		}
		try {
			matcher.group(3);
		} catch (IllegalStateException e) {
			throw new Controller.SyntaxErrorException("Error: problem with expression after \"=\"");
		}
		
		return matcher;
	}
	
	
	/**
	 * This will return 'true' if the String passed to it is a valid function defined in this class.
	 * 
	 * @param functionName: String
	 * @return 'true' if functionName is defined in this class
	 */
	public static boolean isValidUserFunction(String functionName) {
		return customFunctions.containsKey(functionName);
	}
	
}
