import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionValidator
{
	
	// TODO make everything private except the validator function
	
	/**
	 * This array holds all of the recoginzed operators. If a new operator is added in the future,
	 * it needs to be defined in this array.
	 * 
	 * Note: when adding to this list, ensure that you also classify the operator in the 
	 * 		"fullyBinaryOperators[]" or "binaryAndUnaryOperators[]" arrays.
	 */
	public static final char[] recoginzedOperatorsSymbols = {'+', '-', '*', '/', '^'};
	
	/**
	 * The are the defined bracketed operators. They must have a sub-expression on their right.
	 * Functions must be purely alphabetic characters, specifically: [a-zA-z][a-zA-z]+ (min 2 
	 * letters).
	 */
	public static final String[] unaryOperators = {	"cosh", "sinh", "cos", "sin", "tan", "csc", 
													"sec", "cot", "exp", "abs", "ln", "log", "sqrt",
													"root", "fact"
												};
	
	/**
	 * These operators are fully binary and must have terms to their left and right at all times.
	 */
	public static final char[] fullyBinaryOperators = {'*', '/', '^'};
	
	/**
	 * These operators can be both binary and unary (where they are in front ex: -(3.5 * 2) )
	 * These operators must minimally have a sub-expression on their right hand side.
	 */
	public static final char[] binaryAndUnaryOperators = {'+', '-'};
	
	/**
	 * This puts all of the unaryOperators (worded and bracketed functions such as "sin") into a 
	 * Trie for easy lookup.
	 */
	public static Trie functionLookup = new Trie(unaryOperators);;
	
	// EXPRESSION VALIDATION METHODS ------------------------------------------------------------
	
	public static String validateExpression(String original) throws SyntaxErrorException {
		
		/*
		 * This function scans the string many times. This was a deliberate design choice to make
		 * the code more modular (at the expense of efficiency). A possible optimization would be 
		 * to check all of the operations below in one pass.
		 */
		String finalExpression = original;
		
		// this will be set if "debug" mode is in the front of the expression ex. "debug cos(5)"
		boolean isDebugMode = finalExpression.matches("debug .*");
		
		if(isDebugMode)
			finalExpression = (String) finalExpression.subSequence(5, finalExpression.length());
		
		// 1) Replace all brackets by '(' or ')' to facilitate parsing
		finalExpression = replaceBrackets(finalExpression);
		
		// 2) Check for spaces between numbers. ex: 5 6 * 3 must be an error since 5 6 is ambiguous
		if(invalidSpaces(finalExpression))
			throw new SyntaxErrorException("No spaces permitted between numbers or after period");
		
		// 3.1) For parsing simplicity, remove all spaces.
		// note: do not do this step before checking for spaces between numbers (which are invalid)
		finalExpression = removeSpaces(finalExpression);
		
		// 3.2) Replace e^(<something>) by exp(<something>), and e^<number> by exp(<number>)
		// note: do this strictly before replacing constants by their numbers
		// note: must also remove all spaces before doing this
		finalExpression = replaceExponential(finalExpression);
		
		// 3.3) Replace all constants by their number
		// note: do this only after removing all spaces
		finalExpression = replaceConstants(finalExpression);
		
		// 4) Check for dangling operators (last character of cannot be an operator)
		if(danglingOperator(finalExpression))
			throw new SyntaxErrorException("Error: Dangling operator at end of expression");
		
		// 5.1) Replace double minus "--" and "++" by plus "+"
		finalExpression = finalExpression.replaceAll("-\\s*-", "+").replaceAll("\\+\\s*\\+", "+");
		
		// 5.2) Check that entirely binary operator (ie. '*', '^', '/') each have 2 terms.
		// note: this must be run after you have removed all spaces in the expression
		if(!binaryOperatorsHaveTermsOnBothSides(finalExpression))
			throw new SyntaxErrorException("Error: Cannot have 2 binary operators in a row");
		
		// 5.3) Check that unary/binary operators have terms on their right and 
		if(!unaryBinaryOperatorsHaveTermsOnRHS(finalExpression))
			throw new SyntaxErrorException("Error: Unary operators must have an expression on their RHS");
		
		// 5.4) No 2 binary operators in a row (
		// note: do this after replacing all of "-\s*-" signs by "+"
		if(!binaryOperatorsHaveTermsOnBothSides(finalExpression))
			throw new SyntaxErrorException("Error: Binary operators must have expressions on LHS and RHS");
		
		// 5.5) Check that pure unary operators have terms on their right. ("cos(" ok, "sin5" bad)
		if(!validUnaryOperatorBrackets(finalExpression))
			throw new SyntaxErrorException("Error: Unary operators must be of form: cos(\"expression\")");
		
		// 6) make sure that the opening and closing brackets all match
		if(bracketMatch(finalExpression) == -1)
			throw new SyntaxErrorException("Error: Unmatched brackets");
		
		// 7) check invalid characters or symbols
		if(validCharacters(finalExpression) != null) {
			throw new SyntaxErrorException();
		}
		
		// 8) check invalid expressions
		String invalidFunction = validFunctions(finalExpression);
		if(!invalidFunction.equals(""))
			throw new SyntaxErrorException(invalidFunction + " is not a valid function");
		
		// 9) empty brackets
		// note: do this only after converting all non-round brackets "[{" to round brackets "()"
		if(finalExpression.equals("")) { // do not remove as all Strings match the null string
			// do nothing and consider "" as valid
		}
		else if(finalExpression.matches("(\\s*)"))
			throw new SyntaxErrorException("Error: Ambiguous empty brackets");
		
		// 10) comma handling and parameter checking
		// TODO change the functions array to take arity
		
		// 12) check that numbers are valid (and maximum number of decimals)
		// TODO
		
		return finalExpression;
	}
	
	/**
	 * This will replace all "e^" by "exp(" in the entire expression.
	 * 
	 * "e^p" -> "exp(p)" | "e^3.1415" -> "exp(3.1415)" | "e^(15)" -> "exp(15)" | "e^sin(2)" -> "exp(sin(s))"
	 * 
	 * @param expression : String
	 * @return expression with all e^x replaced by exp( )
	 */
	public static String replaceExponential(String expression) {
		
		for(int i = 1; i < expression.length() - 1; ++i) {
			
			// if we hit "e^" then do the following replacements
			if(expression.charAt(i-1) == 'e' && expression.charAt(i) == '^') {
				bracketMatch((String) expression.subSequence(i + 1, expression.length()));
				
				// if the char after "e^" is a bracket then just replace "e^" by "exp"
				if(expression.charAt(i+1) == '(') {
					expression = expression.substring(0, i-1) + "exp" 
							+ expression.substring(i+1, expression.length());
				}
				
				// if the next char after "e^" is a character, put brackets around it
				else if(isValidLowerAlpha(expression.charAt(i+1)) && !isValidLowerAlpha(expression.charAt(i+2))) {
					expression = expression.substring(0, i-1) + "exp" + "(" + expression.charAt(i+1) + ")" + 
								expression.substring(i+2, expression.length());
				}
					
				// if the next char after "e^" is a number then put brackets around the number
				else if(expression.charAt(i+1) >= 48 && expression.charAt(i+1) <= 57) {
						
					Pattern pattern = Pattern.compile("[+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))"); // any decimal
					Matcher matcher = pattern.matcher(expression.substring(i+1));	// after the "e^"
					matcher.find();
			
					// offset used later since the index of the matcher will be offset by i+i
					int offset = expression.length() - expression.substring(i+1).length();
					
					expression = expression.substring(0, i-1) + "exp(" + expression.substring(matcher.start() 
							+ offset, matcher.end() + offset) + ")" + 
							expression.subSequence(matcher.end() + offset, expression.length());
				}
				
				//  if the next char after "e^" is a function then wrap function with brackets
				else {
						
					Pattern pattern = Pattern.compile("[a-z][a-z]+"); // capture the functions
					Matcher matcher = pattern.matcher(expression.substring(i+1));	// after the "e^"
										
					// if the function following "e^" is valid then surround it by brackets
					if(matcher.find() && isValidFunction(matcher.group())) {
						
						int offset = expression.length() - expression.substring(i+1).length();
						int functionLength = matcher.group().length();
						int indexOfEndBracket = getClosingBracket(expression, i+1 + functionLength);
						
						
						expression = expression.substring(0, i-1) + "exp(" + 
								expression.substring(i+1, indexOfEndBracket + 1) + ")" +
								expression.substring(indexOfEndBracket + 1, expression.length());
						
					}
					
					// no other possibility, there is an error after "e^"
					else {
						throw new SyntaxErrorException("Invalid function near e^(...)");
					}
						
				}
			}
		}
		
		return expression;
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
	public static String insertAndReplace(String intoMe, String toAdd, int indexAt) {
		
		if(indexAt < 0 || indexAt > intoMe.length())
			throw new StringIndexOutOfBoundsException("Index provided is out of range");
		
		return intoMe.substring(0, indexAt) + toAdd + intoMe.substring(indexAt + 1, intoMe.length());
	}
	
	/**
	 * This will insert a String into another String at the provided index. The inserted String will
	 * be added at the provided index (its first letter will now be at that index).
	 * 
	 * To append at front, use indexAt = 0.
	 * To append to rear, use indexAt = intoMe.length(); 
	 * 
	 * @param intoMe : String
	 * @param toAdd : String
	 * @param indexAt : int
	 * @return String with another String inserted into it.
	 */
	public static String insertIntoString(String intoMe, String toAdd, int indexAt) {
		
		if(indexAt < 0 || indexAt > intoMe.length())
			throw new StringIndexOutOfBoundsException("Index provided is out of range");
		
		return intoMe.substring(0, indexAt) + toAdd + intoMe.substring(indexAt, intoMe.length());
	}
	
	/**
	 * Given a mathematical expression this will replace all constants (lone letters) by their 
	 * corresponding number value (found in UserConstants class).
	 * 
	 * ex. if p is mapped to 3.14
	 * 	   then (a + 4 * 5) -> (3.14 + 4 * 5)
	 * 
	 * @param expression : String
	 * @return expression with constants replaced by their values
	 */
	public static String replaceConstants(String expression) {
		
		// This is some ghetto special character stuff that makes edge cases easier.
		expression = "%" + expression + "%";
		
		int addedCharacters = 0;
		String addedIntoExpresion = "";
		
		for(int i = 1; i < expression.length() - 1; ++i) {
			
			// some weird skipping condition or we go out of bounds
			if(i + 1 + addedCharacters == expression.length())
				break;
			
			if(!isValidLowerAlpha(expression.charAt(i - 1 + addedCharacters)) && 
					!isValidLowerAlpha(expression.charAt(i + 1 + addedCharacters))) {
				if(isValidLowerAlpha(expression.charAt(i + addedCharacters))) {
					
					addedIntoExpresion = UserConstants.getValue(expression.charAt(i + addedCharacters));
					
					expression = insertAndReplace(expression, addedIntoExpresion, i + addedCharacters);
					
					if(addedIntoExpresion.length() > 1)
						addedCharacters += addedIntoExpresion.length() - 1;
				}
			}
		}
		
		// Remove the ghetto characters
		return expression.subSequence(1, expression.length() - 1).toString();
		
	}
	
	
	/**
	 * Given a mathematical expression, this will go through all words ("sin", "cos", "happy") to 
	 * see if they were defined as a valid functions in the 'unaryOperators' array.
	 * 
	 * This will return the invalid word or null if all words are valid functions.
	 * 
	 * @param expression : String
	 * @return "" if all valid, else invalid function.
	 */
	public static String validFunctions(String expression) {
		
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("[a-zA-z][a-zA-z]+").matcher(expression);
		
		while (m.find()) {
			allMatches.add(m.group());
		}
		
		if(allMatches.size() == 0)
			return "";
		
		// put list into a String array
		String[] functionArray = new String[allMatches.size()];
		for(int i = 0; i < allMatches.size(); ++i) {
			functionArray[i] = allMatches.get(i);
		}

		boolean foundValid = false;
		
		int i = 0;
		
		for(i = 0; i < functionArray.length; ++i) {
			
			foundValid = false;
			
			for(int j = 0; j < unaryOperators.length ; ++j) {
				
				if(functionArray[i].equals(unaryOperators[j])) {
					foundValid = true;
					break;
				}
			}
			
			if(!foundValid) {
				break;
			}
		}
		
		if(!foundValid)
			return functionArray[i];
		
		// all 'worded' functions in the expression are valid.
		return "";
	}
	
	/**
	 * This will check the mathematical expression for invalid use of the 'space' character.
	 * 
	 * -space after period is invalid
	 * -period after period is invalid
	 * -space between numbers is invalid
	 * 
	 * Returns 'true' if there are invalid spaces and 'false' if the expression is valid.
	 * 
	 * @param expression : String
	 * @return 'true' if invalid
	 */
	public static boolean invalidSpaces(String expression) {
		
		char[] decomposedExpression = expression.toCharArray();
		
		// check each character in the String
		for(int i = 1; i < decomposedExpression.length; ++i) {
			
			// if the previous character is a '.' then check for subsequent space or period
			if(decomposedExpression[i-1] == '.') {
				if(decomposedExpression[i] == ' ' || decomposedExpression[i] == '.') {
					return true;
				}
			}
		}
		
		for(int i = 1; i < decomposedExpression.length - 1; ++i) {
			// if the previous character is a number then check for subsequent space
			if(decomposedExpression[i-1] >= '0' && decomposedExpression[i-1] <= '9') {
				if(decomposedExpression[i] == ' ') {
					if(decomposedExpression[i+1] >= '0' && decomposedExpression[i+1] <= '9') {
						return true;
					}
				}
			}
		}
		return false; // no invalid space characters (all spaces valide).
	}
	
	
	/**
	 * This will ensure that all of the characters in the expression are valid. They are valid if
	 * they are one of the following. 
	 * 
	 * Returns the first offending character.
	 * Returns 'null' if all characters are valid.
	 * 
	 * -bracket: '(', ')', '[', ']', '{', '}'
	 * -period: '.'
	 * -numbers: 0-9
	 * -letters: a-z
	 * -recoginzedOperatorsSymbols[]
	 * 
	 * @param expression : String
	 * @return 
	 */
	public static Character validCharacters(String expression) {
		
		// null string is valid
		if(expression.length() == 0)
			return null;
		
		char [] decomposedExpression = expression.toCharArray();
		
		boolean validChar = true;
		
		Character problemChar = null;
		
		for(char c : decomposedExpression) {
			
			// these are the hardcoded valid characters
			if(validChar == ( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') ||
						c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' ||
						c == '.' || c == ',')) {
				validChar = false;
				break;
			}
			
			// if not one of the hardcoded then check the operator array
			if(!validChar) {
				for(char op : recoginzedOperatorsSymbols) {
					if(c == op) {
						validChar = true;
						break;
					}
					else {
						return new Character(c);
					}
				}
			}
			
		}
		return null;
	}
	
	
	/**
	 * This will ensure that all binary symbol operator (ie. * / ^) are flanked on both sides by
	 * a valid number of sub-expression.
	 * 	ie. (5 * (2 -8)) is valid || (5 * 2 /) is invalid
	 * @param expression : String
	 * @return
	 */
	public static boolean binaryOperatorsHaveTermsOnBothSides(String expression) {
		
		// do this check for each unary operator defined in the array
		for(int op = 0; op < fullyBinaryOperators.length; ++op) {
			
			// scan entire string and check LHR and RHS of operator
			for(int ch = 0; ch < expression.length(); ++ ch) {
				
				// operator found in expression
				if(expression.charAt(ch) == fullyBinaryOperators[op]) {
					try {
								// ex: "(*"							ex: "*)"
						if(expression.charAt(ch-1) == '(' || expression.charAt(ch+1) == ')') {
							return false;
						}
						else {
							// check if there is another binary operator on RHS or LHS
							for(int op2 = 0 ; op2 < fullyBinaryOperators.length; ++op2) {
								if(expression.charAt(ch-1) == fullyBinaryOperators[op2] || 
										expression.charAt(ch+1) == fullyBinaryOperators[op2]) {
									return false;
								}
							}
						}
					} catch (StringIndexOutOfBoundsException e) {
						return false; // an OOB exception means that nothing is on LHS or RHS
					}
				}
			}
			
		}
		
		return true;	// All tests pass so the binary operators are valid.
	}
	
	/**
	 * This returns 'false' if one of the unary/binary operators defined in that array is found in 
	 * the expression with no sub-expression on its RHS.
	 * @param expression : String
	 * @return 'true' if everything is valid
	 */
	private static boolean unaryBinaryOperatorsHaveTermsOnRHS(String expression) {
		
		for(char c : binaryAndUnaryOperators) {
			
			if(expression.matches(".*[" + c + "]$"))
				return false; // invalid: operator of form "3 * 5 +"
			
		}
		
		return true;
	}
	
	/**
	 * This will check that all of the unary operators defined in the Parser have a bracket 
	 * immediately following the operator.
	 * ex: 3 * cos(5 * 4) ok, 3 * cos 5 not ok
	 * 
	 * @param expression: String
	 * @return true if all unary operators are valid
	 */
	public static boolean validUnaryOperatorBrackets(String expression) {
		
		// do this check for each unary operator defined in the array
		for(int i = 0; i < unaryOperators.length; ++i) {
			
			int index = expression.indexOf(unaryOperators[i]);
			boolean isFirstPassOnOperator = true; // different behaviour on the very first pass
			
			// This loop will scan expression and check the validity of each (ie. cos) expression
			while(true) {
				
				// If not first pass then start from the end of the operator you found in last pass
				if(!isFirstPassOnOperator)
					index = expression.indexOf(unaryOperators[i], index + unaryOperators[i].length());
				
				// If not found then break from loop to try with another operator from the array
				if(index == -1)
					break;
				
				try {
				// If the char after operator is not '(' (ex: "cos5" is invalid, "cos(" is valid)
					if(expression.charAt(index + unaryOperators[i].length()) != '(') {
						if(expression.charAt(index + unaryOperators[i].length()) == 'h' &&
							expression.charAt(index + unaryOperators[i+1].length()) == '(')
							/* the last "if" hardcodes a check for sinh(/cosh(. The reason is that 
							 * "sinh" will always cause an error since when checking "sin", then the
							 * next char is 'h' and not the expected '('. Probably going to need to
							 * fix this at some point since not very extendable.
							 * Definitely need to fix if we incorporate user functions as this can't
							 * be hardcoded for every functions that is a prefix of another.
							 */
						return false;
					}
				} catch(IndexOutOfBoundsException e) { // expression ends with operator ("3 * cos")
					return false;
				}
				
				isFirstPassOnOperator = false;
			}
		}
		
		return true; // no invalid unary operators
	}
	
	/**
	 * This checks for dangling operators. An operator is dangling if it is alone at the end of an
	 * expression. ex: (3 * 5) + (7 ^ 2)-
	 * @param original
	 * @return
	 */
	private static boolean danglingOperator(String original) {
		
		if(original.length() == 0)
			return false;
			
			
		char lastChar = original.charAt(original.length() - 1);
		
		boolean hasDanglingOperator = false;
		
		for(char c : recoginzedOperatorsSymbols) {
			if(lastChar == c) {
				hasDanglingOperator = true;
				break;
			}
		}
		
		return hasDanglingOperator;
		
	}
	
	
	/**
	 * This will replace all [{ and ]} bracket characters by the corresponding round brackets ().
	 * @param original: String
	 * @return brackets replaced: String
	 */
	private static String replaceBrackets(String original) {
		return original.replace("[", "(").replace("]",")").replace("{", "(")
				.replace("}", ")");
	}
	
    /**
     * Given a mathematical expression, this will tell you if the brackets match or not. A match
     * is when it returns the index of the ending bracket. Invalid brackets return -1.
     * @param expression: String
     * @return index of ending bracket: int
     */
    public static int bracketMatch(String expression) {

        int indexOfLastBracket = 0;
        String opening = "({[";
        String closing = ")}]";

        Stack<Character> buffer = new Stack<>();

        // Check each character of the expression for brackets (skip over non brackets)
        for(char c : expression.toCharArray()) {
            ++indexOfLastBracket;

            if(opening.indexOf(c) != -1) {	// if c is an opening bracket

                buffer.push(c);

            }
            else if(closing.indexOf(c) != -1) { // if c is a closing bracket

                if(buffer.isEmpty()) // we are missing an opening bracket
                    return -1;

                if(closing.indexOf(c) != opening.indexOf(buffer.pop()))
                    return -1; // current closing bracket does not match opening bracket on stack
            }
        }

        // if after all that, the buffer is empty then the brackets match
        if(buffer.isEmpty())
            if(indexOfLastBracket == -1 || indexOfLastBracket == 0) { // this is the case where there are no brackets
                return 0;
            }
            else {
                return indexOfLastBracket - 1;
            }
        return -1;
    }

    /**
     * Given a String expression, this will return the same expression minus any spaces.
     * @param expression: String
     * @return expression without spaces: String
     */
    private static String removeSpaces(String expression) {
        return expression.replaceAll("\\s+", "");
    }
    
    /**
     * Given a string with brackets. If the start index of the open bracket is given, find the 
     * index of the closing bracket.
     * 
     * @author Rajput-Ji (https://www.geeksforgeeks.org/find-index-closing-bracket-given-opening-bracket-expression/)
     * 
     * @param expression : String
     * @param openingBracketIndex 
     * @return i : index of closing bracket
     */
     public static int getClosingBracket(String expression, int openingBracketIndex) { 
         int i; 
   
         // If index given is invalid and is  
         // not an opening bracket.  
         if (expression.charAt(openingBracketIndex) != '(') { 
             return -1; 
         } 
   
         // Stack to store opening brackets.  
         Stack<Integer> st = new Stack<>(); 
   
         // Traverse through string starting from  
         // given index.  
         for (i = openingBracketIndex; i < expression.length(); i++) { 
   
             // If current character is an  
             // opening bracket push it in stack.  
             if (expression.charAt(i) == '(') { 
                 st.push((int) expression.charAt(i)); 
             } 
             // If current character is a closing  
             // bracket, pop from stack. If stack  
             // is empty, then this closing  
             // bracket is required bracket.  
             else if (expression.charAt(i) == ')') { 
                 st.pop(); 
                 if (st.empty()) {
                     return i; 
                 } 
             }
             
         } 
   
         // If no matching closing bracket  
         // is found.  
         return -1;
     } 
    
     /**
      * This will tell you if the function you pass to it is defined as a valid function in this
      * class.
      * 
      * @param function : String
      * @return 'true' if function is valid and defined
      */
    public static boolean isValidFunction(String function) {
    	
    	for(String s : unaryOperators)
    		if(function.equals(s))
    			return true;
    	
		return false;
    }
    
}
