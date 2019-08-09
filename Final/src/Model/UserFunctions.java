package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Controller.SyntaxErrorException;

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
public class UserFunctions implements Serializable
{
	/**
	 * Some serialUID (randomly generated)
	 */
	private static final long serialVersionUID = 2L;

	/*
	 * This is a function. It is defined by its arity and its String mathematical expression.
	 */
	public static class Function // TODO make private after tests
	{
		private int arity;
		private String expression;
		
		private Function(int arity, String expression) {
			
			// this is here because when we replace constant by number, we dont' yet deal with 
			// the case where we replace a constant with "10". This can be fixed in future.
			if(arity > 9)
				throw new SyntaxErrorException("Error: max of 9 parameters in custom function");
			
			this.arity = arity;
			this.expression = expression;
		}
		
		private int getArity() {
			return arity;
		}
		
		public String getExpression() { // TODO make private after tests
			return expression;
		}
		
	} // End of nested class
	
	// These are constants that return the corresponding String from a Matcher.group(int) call.
	private static final int functionNamePart = 1;
	private static final int parameterPart = 2;
	private static final int expressionPart = 3;
	
	/*
	 * This is the map that holds the functions. You search by function name. (ie. "log")
	 */
	private static Map<String, Function> customFunctions = new HashMap<String, Function>();
	
	
	/**
	 * Given the function name (ie. "log") this will return the String expression associated to that
	 * function so that it can later be passed to the Parser and resolved to a number.
	 * 
	 * Will return 'null' if not found.
	 * 
	 * @param functionName: String
	 * @return expression associate to the functionName
	 */
	private static Function getFunction(String functionName) {
		
		return customFunctions.get(functionName);
		
	}
	
	/**
	 * Will return 'true' if the given String is defined as a user-defined function in this class.
	 * 
	 * @param functionName
	 * @return
	 */
	public static boolean isACustomFunction(String functionName) {
		
		return customFunctions.containsKey(functionName);
				
	}
	
	/**
	 * This is what is called to get the String from a function call.
	 * 	Where "test(a,b,c) = a + b + c"
	 * 	If User passes "test(1,2,3)" 
	 * 	then he receives "1 + 2 + 3" back from this function
	 * 
	 * @param userFunctionCall
	 * @return
	 */
	public static String getUserFunction(String userFunctionCall) {
		
		Matcher matcher = Model.UserFunctions.tokenizeFunctionCall(userFunctionCall);
    	
    	String replacementExpression = Model.UserFunctions.replaceParametersWithArguments(matcher);
    	
		return replacementExpression;
	}
	
	
	private static String replaceUserFunctionsInExpression(String expression) {
		
		String test = "";
		
		Pattern pattern = Pattern.compile("([a-z][a-z]+)");
		Matcher matcher = pattern.matcher(expression);
		
		
		matcher.find();
		test += matcher.group(1);
		matcher.find();
		test += matcher.group(1);
		
		
		customFunctions.forEach((k,v)->{
			
			
			
			
		});
		
		return test;
	}
	
	/**
	 * Takes a function call and tokenizes it.
	 * 
	 * ie. test(15, cos(28)) and returns matcher:
	 * 	group(1) = "tim"
	 * 	group(2) = "15, cos(28)"
	 * 
	 * @param functionCall
	 * @return
	 */
	private static Matcher tokenizeFunctionCall(String functionCall) {
		
		// These hold the different parts of regex (separated for easy maintenance)
		String functionName = "[a-zA-Z][a-zA-Z]+";
		String parameters = ".*";
		
		// This regex captures: 1) name, 2) parameter list, 3) RHS math expression
		String validUserFunctionRegex = "^" + "(" + functionName + ")" +
				"\\(" + "(" +  parameters + ")" + "\\)";
		
		Pattern pattern = Pattern.compile(validUserFunctionRegex);
		Matcher matcher = pattern.matcher(functionCall);
		matcher.find();

		return matcher;
	}
	
	/**
	 * Given a functionCallTokenized Matcher, this will return an ArrayList<String> of validated
	 * arguments (run through expressionValidator). These will serve as replacements for their 
	 * corresponding parameters.
	 * 
	 * @param tokenizedFunctionCall
	 * @return
	 */
	private static ArrayList<String> getFunctionCallParameters(Matcher tokenizedFunctionCall) {
		
		ArrayList<String> parameterList = new ArrayList<String>();
		String rawParameters = tokenizedFunctionCall.group(parameterPart);
		String temp = "";
		
		for(int i = 0; i < rawParameters.length(); ++i) {
			
			if(i == rawParameters.length() - 1) {
				temp += rawParameters.charAt(i);
				Controller.ExpressionValidator.validateExpression(temp, false);
				parameterList.add(temp);
			}
			else if(rawParameters.charAt(i) != ',') {
				if(rawParameters.charAt(i) != ' ')
					temp += rawParameters.charAt(i);
			}
			else {
				Controller.ExpressionValidator.validateExpression(temp, false);
				parameterList.add(temp);
				temp = "";
			}
		}
		
		return parameterList;
	}
	
	/**
	 * Given a Matcher from tokenizeFunctionCall(), this will replace all parameters with the
	 * arguments that the user passed.
	 * 
	 * @param tokenizeFunctionCall
	 * @return
	 */
	private static String replaceParametersWithArguments(Matcher tokenizeFunctionCall) {
		
		Function function = customFunctions.get(tokenizeFunctionCall.group(functionNamePart));
		
		if(function == null)
			throw new SyntaxErrorException("Error: unknown function " + "\"" +
					tokenizeFunctionCall.group(functionNamePart) + "\"");
		
		int arity = function.getArity();
		
		// this is the expresion with the parameters replaced with "!0" etc.
		String expression = function.getExpression();
		System.out.println("expression before : " + expression);
		
		ArrayList<String> argumentList = getFunctionCallParameters(tokenizeFunctionCall);
		
		int addedCharacters = 0;
		String addedIntoExpression = "";
		
		// for each number in arity (!0, !1, !2,...) replace all occurences of that !number by the 
		// corresponding capture group (argument) of the Matcher.
		for(int i = 0; i < arity; ++i) {
			
			expression = expression.replaceAll(("!" + i), argumentList.get(i));
			
		}
			
		
		return expression;
	}
	
	/**
	 * Given an expression of the form : test(15*2, cos(4)) this will return a matcher where
	 * group 1 corresponds to the first argument (15*2) and group 2 corresponds to the second 
	 * (cos(4)) and so on.
	 * 
	 * @param expression
	 * @return
	 */
	private static Matcher getArgumentsOfFunctions(String expression) {
		
		Pattern pattern = Pattern.compile("\\(\\s*(.*), \\s*(.*)\\s*\\).*");
		Matcher matcher = pattern.matcher(expression);
		matcher.find();
		
		return matcher;
		
	}
	
	/**
	 * This will add the function to the map. It will return 'true' if the functions already existed
	 * in the map (ie. we are overwriting its definition.
	 * 
	 * 
	 * @param tokenizedExpression : Matcher
	 * @return 'true' if we are overwriting the function
	 */
	public static boolean setFunction(String userFunctionAssignment) {
		
		Matcher tokenizedExpression = tokenizeUserFunctions(userFunctionAssignment);
		
		String validatedRHSExpression = validateUserFunctionsExpression(tokenizedExpression);
		int arityOfFunction = getArity(tokenizedExpression);
		
		// This will be true only if the function is already defined and thus we are overriding.
		boolean isOverwritingMap = customFunctions.containsKey(tokenizedExpression.group(functionNamePart));
		
		Function functionToAdd = new Function(arityOfFunction, validatedRHSExpression);
		
		customFunctions.put(tokenizedExpression.group(functionNamePart), functionToAdd);
		
		return isOverwritingMap;
	}
	
	
	/**
	 * This is the first step: to parse the user's expression and ensure that it is of the correct
	 * form for a user function (see Regex pattern).
	 * 
	 * Note: this does not yet validate the RHS expression for syntactical correctness. This 
	 * step will be done by another function.
	 * 
	 * This cannot return a 'null' object so further methods do no need to check for a 'null' 
	 * Matcher returned from this.
	 * 
	 * Return a Matcher object where capture group n is:
	 * 	0) entire String
	 * 	1) the functions name
	 * 	2) the parameter list (with commas)
	 * 	3) the RHS of the "=" sign (un-validated expression)
	 * 
	 * @param usrFunctionDefinition : String
	 * @return Matcher
	 */
	private static Matcher tokenizeUserFunctions(String usrFunctionDefinition) {
		
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
		Matcher matcher = pattern.matcher(usrFunctionDefinition);
		matcher.find();
		
		
		// Checks if the name of function is already defined in MathFunctions class (throws exception)
		isDuplicateFunctionName(matcher.group(functionNamePart));
		
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
	 * This will take as a parameter the RHS expression and validate its parameters. Then it will
	 * replace all constants with their corresponding numbers. Finally, it will run it through the
	 * general validator for expression defined in the ExpressionValidator class of the Controller.
	 * 
	 * @param userFunctionDefinition
	 * @return
	 */
	private static String validateUserFunctionsExpression(Matcher tokenizedExpression) {
		
		validateParameterOccurences(tokenizedExpression);
		
		String formattedExpression = replaceConstantsWithNumbers(tokenizedExpression);
		
		return Controller.ExpressionValidator.validateExpression(formattedExpression, true);
	}
	
	
	/**
	 * This will throw an exception if the user-defined functions name is already defined in the
	 * MathFunctions class.
	 * 
	 * @param functionName: String
	 * @throws SyntaxErrorException
	 */
	private static void isDuplicateFunctionName(String functionName) throws SyntaxErrorException {
		
		// Check to see if the name of the function is already a MathFunctions (this is forbidden)
		if(Controller.ExpressionValidator.isValidFunction(functionName))
			throw new SyntaxErrorException("Error: this is already a built-in function");
		
	}
	
	/**
	 * This will throw an exception if the user-defined functions name is already defined in the
	 * MathFunctions class.
	 * 
	 * @param functionName: Matcher
	 * @throws SyntaxErrorException
	 */
	private static void isDuplicateFunctionName(Matcher tokenizedExpression) 
			throws SyntaxErrorException {
		
		isDuplicateFunctionName(tokenizedExpression.group(functionNamePart));
		
	}
	
	
	/**
	 * This will validate the parameters.
	 *	1) none of them are reserved characters (e, p)
	 *	2) if a constant (other than e, p) are in the expression, they must also be in parameters
	 *	3) if a paramter is defined, it must figure at least once in the RHS expression
	 *
	 * @param tokenizedExpression
	 * @throws SyntaxErrorException
	 */
	private static void validateParameterOccurences(Matcher tokenizedExpression) 
			throws SyntaxErrorException {
		
		char parameters[] = removeCommasFromConstantList(tokenizedExpression).toCharArray();
		String expression = tokenizedExpression.group(expressionPart);
		
		// ensure that none of the parameters are reserved characters
		checkParametersForReservedChars(parameters);
		
		// check that if a constant is in expression, it must also be in parameters (except e, p)
		expressionConstantNotInParameterList(parameters, expression);
		
		// check that if a parameter is defined, it must appear in the expression
		parameterNotInExpression(parameters, expression);
		
	}
	
	
	
	/**
	 * This ensures that if a parameter is defined in the function, that is must be present at least
	 * once in the RHS expression.
	 * 
	 * @param parameters
	 * @throws SyntaxErrorException
	 * @param expression
	 */
	private static void parameterNotInExpression(char parameters[], String expression) 
			throws SyntaxErrorException {
		
		ArrayList<Character> constantsInExpression = extractConstantsFromExpression(expression);
		
		boolean isParameterInExpresion = false;
		
		for(char c : parameters) {
			
			for(Character C : constantsInExpression) {
				
				if(c == C.charValue())
					isParameterInExpresion = true;
				
			}
			
			if(!isParameterInExpresion)
				throw new SyntaxErrorException("Error: Parameter " + c + " not used in expression");
			
			isParameterInExpresion = false;
		}
		
	}
	
	/**
	 * This is a method that ensure that if a constant (other than e or p) are found in the expression
	 * that they must also be in the parameter list.
	 * 
	 * ie. test(a,b) = a + b + k => throw Exception("k is not in paramter list")
	 * 
	 * @param parameters
	 * @param expression
	 * @throws SyntaxErrorException
	 */
	private static void expressionConstantNotInParameterList(char parameters[], String expression)
			throws SyntaxErrorException {
		
		boolean isParameterPresent = false;
		// check first letter of array vs the second (this is an edge case to prevent OOB)
		
		if(isValidLowerAlpha(expression.charAt(0)) && !isValidLowerAlpha(expression.charAt(1))){
			
			for(char c : parameters) {
				if(c == expression.charAt(0)) {
					isParameterPresent = true;
				}
			}
			
			if(!isParameterPresent)
				throw new SyntaxErrorException("Error: parameter " + expression.charAt(0) + " is not in expresion");
		}
		
		// all constants (other than e, p) in expression are found in parameter list
		for(int i = 1; i < expression.length() - 1; ++i) {
			
			if(!isValidLowerAlpha(expression.charAt(i - 1)) && 
					!isValidLowerAlpha(expression.charAt(i + 1))) {
				if(isValidLowerAlpha(expression.charAt(i))
						&& (expression.charAt(i) != 'e') 
						&& (expression.charAt(i) != 'p')) {
					
					for(char c : parameters) {
						if(c == expression.charAt(i)) {
							isParameterPresent = true;
						}
					}
						
					if(!isParameterPresent)
						throw new SyntaxErrorException("Error: parameter " + expression.charAt(i) + " is not in expresion");
						
					isParameterPresent = false;
				}
			}
		}
		
		// check last letter of array vs the second last (this is an edge case to prevent OOB)
		if(isValidLowerAlpha(expression.charAt(expression.length() - 1)) && 
				!isValidLowerAlpha(expression.charAt(expression.length() - 2))){
			
			for(char c : parameters) {
				if(c == expression.charAt(expression.length() - 1)) {
					isParameterPresent = true;
				}
			}
			
			if(!isParameterPresent) {
				throw new SyntaxErrorException("Error: parameter " + (expression.charAt(expression.length() - 1) + " is not in expresion"));
			}
		}
		
	}
	
	
	/**
	 * This will replace the constants with a number. This is how the functions expression will be
	 * stored. The reason for this is that when the user calls the function with parameters, we will
	 * be able to match the first parameter to its occurences in the expression and so forth. The
	 * special character '!' is used to differentiate the constant from a number which may be
	 * present in the expression definition
	 * 
	 * ex. "test(x,y,z) = x^y + 18 * cos(z) - x + y"  would give  "!0^!1 + 18 * cos(!3) - !0 + !2"
	 * 	   where all occurences of the first parameter int the RHS expression are replaced by "!0",
	 *     the second parameters are replaced by "!2", and so on.
	 *     
	 * @param tokenizedExpression
	 * @return
	 */
	private static String replaceConstantsWithNumbers(Matcher tokenizedExpression) {
		
		String expression = tokenizedExpression.group(expressionPart);
		char parameters[] = removeCommasFromConstantList(tokenizedExpression).toCharArray();
		
		// This is some ghetto special character stuff that makes edge cases easier.
		expression = "%" + expression + "%";
		
		int addedCharacters = 0;
		String addedIntoExpression = "";
		
		for(int i = 1; i < expression.length() - 1; ++i) {
			
			// some weird skipping condition or we go out of bounds
			if(i + 1 + addedCharacters == expression.length())
				break;
			
			if(!isValidLowerAlpha(expression.charAt(i - 1 + addedCharacters)) && 
					!isValidLowerAlpha(expression.charAt(i + 1 + addedCharacters))) {
				if(isValidLowerAlpha(expression.charAt(i + addedCharacters)) && 
						(expression.charAt(i + addedCharacters) != 'e') && 
						(expression.charAt(i + addedCharacters) != 'p')) {
					
					for(int j = 0; j < parameters.length; ++j) {
						if(parameters[j] == expression.charAt(i + addedCharacters)) {
							addedIntoExpression = "!" + new Integer(j).toString();
						}
					}
					
					expression = insertAndReplace(expression, addedIntoExpression, i + addedCharacters);
					
					if(addedIntoExpression.length() > 1)
						addedCharacters += addedIntoExpression.length() - 1;
				}
			}
		}
		
		// Remove the ghetto characters
		return expression.subSequence(1, expression.length() - 1).toString();
		
	}
	
	/**
	 * This will remove the commas and spaces from a parameter list.
	 * 
	 * ie. "a,b,c" => "abc"
	 * 
	 * @param tokenizedExpression
	 * @return
	 */
	private static String removeCommasFromConstantList(Matcher tokenizedExpression) {
		
		String parameters = tokenizedExpression.group(parameterPart);
		String parametersWithoutCommas = "";
		
		
		// remove commas and spaces
		for(int i = 0; i < parameters.length(); ++i) {
			if(parameters.charAt(i) != ' ')
				if(parameters.charAt(i) != ',') // parameters already checked and of the form : "a,b,c"
					parametersWithoutCommas += parameters.charAt(i);
		}
		
		return parametersWithoutCommas;
		
	}
	
	/**
	 * This counts the letter in the parameter list of the Matcher for user-defined functions. This 
	 * is the arity of the function that the user is trying to define.
	 * 
	 * @param tokenizedExpression: Matcher
	 * @return the arity (how many parameters were passed in the expression)
	 */
	private static int getArity(Matcher tokenizedExpression) {
		
		int arity = 0;
		String parameterList = tokenizedExpression.group(parameterPart);
		
		for(int i = 0; i < parameterList.length(); ++i) {
			if(parameterList.charAt(i) >= 'a' && parameterList.charAt(i) <= 'z')
				++arity;
		}
		
		return arity;
	}
	
	/**
	 * This will return 'true' if the String passed to it is a valid function defined in this class.
	 * 
	 * @param functionName: String
	 * @return 'true' if functionName is defined in this class
	 */
	private static boolean isValidUserFunction(String functionName) {
		return customFunctions.containsKey(functionName);
	}
	
	/**
	 * Returns 'true' if the character is a lower case letter.
	 * 
	 * @param aChar : char
	 * @return if aChar is a letter
	 */
	private static boolean isValidLowerAlpha(char aChar) {
		if(aChar >= 97 && aChar <= 122)
			return true;
		return false;
	}
	
	 /**
	 * This will insert a String at a specified index. The character at the specified index will be
	 * discarded (this is meant to be a helper for other methods)
	 * 
	 * To append at front, use indexAt = 0.
	 * To append to rear, use indexAt = intoMe.length(); 
	 * 
	 * @param intoMe : String
	 * @param toAdd : String
	 * @param indexAt : int
	 * @return String with another String inserted into it.
	 */
	private static String insertAndReplace(String intoMe, String toAdd, int indexAt) {
		
		if(indexAt < 0 || indexAt > intoMe.length())
			throw new StringIndexOutOfBoundsException("Index provided is out of range");
		
		return intoMe.substring(0, indexAt) + toAdd + intoMe.substring(indexAt + 1, intoMe.length());
	}
	
	/**
	 * This will check to see if one of the parameters is defined in UserConstants class as a 
	 * reserved character (ie. e, p). If so then it will throw a corresponding exception.
	 *  
	 * @param parameters : char[]
	 */
	private static void checkParametersForReservedChars(char parameters[]) throws SyntaxErrorException {
		
		for(char c : parameters) {
			UserConstants.validConstantLetter(c);
		}
		
	}
	
	/**
	 * This will check to see if one of the parameters is defined in UserConstants class as a 
	 * reserved character (ie. e, p). If so then it will throw a corresponding exception.
	 *  
	 * @param tokenizedExpression : Matcher
	 * @return
	 */
	private static void checkParametersForReservedChars(Matcher tokenizedExpression) throws SyntaxErrorException {
		
		checkParametersForReservedChars(tokenizedExpression.group(parameterPart).toCharArray());
		
	}
	
	/**
	 * This will look through the expression and extranct all constants (except e and p) to an
	 * ArrayList. This is done to simplify subsequent checking by validator functions.
	 * 
	 * @param expression
	 * @return
	 */
	private static ArrayList<Character> extractConstantsFromExpression(Matcher tokenizedExpression) {
		
		return extractConstantsFromExpression(tokenizedExpression.group(expressionPart));
		
	}
	
	/**
	 * This will look through the expression and extranct all constants (except e and p) to an
	 * ArrayList. This is done to simplify subsequent checking by validator functions.
	 * 
	 * @param expression
	 * @return
	 */
	private static ArrayList<Character> extractConstantsFromExpression(String expression) {
		
		ArrayList<Character> constantList = new ArrayList<>();
		
		// check first char of the String vs 2nd char
		if(isValidLowerAlpha(expression.charAt(0)) && !isValidLowerAlpha(expression.charAt(1)) 
				&& expression.charAt(0) != 'p' && expression.charAt(0) != 'e')
			constantList.add(expression.charAt(0));
		
		// check middle chars of String
		for(int i = 1; i < expression.length() - 1; ++i) {
		
			if(!isValidLowerAlpha(expression.charAt(i - 1)) && 
					!isValidLowerAlpha(expression.charAt(i + 1))) {
				if(isValidLowerAlpha(expression.charAt(i))
						&& (expression.charAt(i) != 'e') 
						&& (expression.charAt(i) != 'p')) {
					constantList.add(expression.charAt(i));
					
				}
			}
		}
		
		// check last char of the String vs 2nd last char
		if(isValidLowerAlpha(expression.charAt(expression.length()-1)) && !isValidLowerAlpha(expression.charAt(expression.length()-2)) 
				&& expression.charAt(expression.length()-1) != 'p' && expression.charAt(expression.length()-1) != 'e')
			constantList.add(expression.charAt(expression.length()-1));
		
		return constantList;
		
	}
}
