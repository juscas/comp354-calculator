import java.util.Stack;

public class ExpressionValidator
{
	
	/**
	 * This array holds all of the recoginzed operators. If a new operator is added in the future,
	 * it needs to be defined in this array.
	 * 
	 * Note: when adding to this list, ensure that you also classigy the operator in the 
	 * 		"fullyBinaryOperators[]" or "binaryAndUnaryOperators[]" arrays.
	 */
	public final char[] recoginzedOperatorsSymbols = {'+', '-', '*', '/', '^'};
	
	/**
	 * The are the defined bracketed operators. They must have a sub-expression on their right.
	 */
	public final String[] unaryOperators = {	"cosh", "sinh", "cos", "sin", "tan", "csc", "sec", 
												"cot", "exp", "abs", "ln", "log" 
											};
	
	/**
	 * These operators are fully binary and must have terms to their left and right at all times.
	 */
	public final char[] fullyBinaryOperators = {'*', '/', '^'};
	
	/**
	 * These operators can be both binary and unary (where they are in front ex: -(3.5 * 2) )
	 * These operators must minimally have a sub-expression on their right hand side.
	 */
	public final char[] binaryAndUnaryOperators = {'+', '-'};
	
	
	// EXPRESSION VALIDATION METHODS ------------------------------------------------------------
	
	public String validateExpression(String original) throws SyntaxErrorException {
		
		/*
		 * This function scans the string many times. This was a deliberate design choice to make
		 * the code more modular (at the expense of efficiency). A possible optimization would be 
		 * to check all of the operations below in one pass.
		 */
		
		String finalExpression = original;
		
		// 1) Replace all brackets by '(' or ')' to facilitate parsing
		finalExpression = replaceBrackets(finalExpression);
		
		// 2) Check for spaces between numbers. ex: 5 6 * 3 must be an error since 5 6 is ambiguous
		if(finalExpression.matches("\\d+\\s+\\d+|(.\\s*)")) // TODO fix.\\s+ it does not work
			throw new SyntaxErrorException("Ambiguous: Spaces are not permitted between numbers");
		
		// 3) For parsing simplicity, remove all spaces.
		// note: do not do this step before checking for spaces between numbers (which are invalid)
		finalExpression = removeSpaces(finalExpression);
		
		// 4) Check for dangling operators (last character of cannot be an operator)
		if(danglingOperator(finalExpression))
			throw new SyntaxErrorException("Dangling operator at end of expression");
		
		// 5.1) Replace double minus "--" and "++" by plus "+"
		finalExpression = finalExpression.replaceAll("-\\s*-", "+").replaceAll("+\\s*+", "+");
		
		// 5.2) Check that entirely binary operator (ie. '*', '^', '/') each have 2 terms.
		// note: this must be run after you have removed all spaces in the expression
		if(!binaryOperatorsHaveTermsOnBothSides(finalExpression))
			throw new SyntaxErrorException("Cannot have 2 binary operators in a row");
		
		
		// 5.3) Check that unary/binary operators have terms on their right and 
		if(!unaryBinaryOperatorsHaveTermsOnRHS(finalExpression))
			throw new SyntaxErrorException("Unary operators must have an expression on their RHS");
		
		// 5.4) No 2 operators binary operators in a row (
		// note: do this after replacing all of "-\s*-" signs by "+"
		if(!binaryOperatorsHaveTermsOnBothSides(finalExpression))
			throw new SyntaxErrorException("Binary operators must have expressions on LHS and RHS");
		
		// 5.5) Check that pure unary operators have terms on their right.
		if(!validUnaryOperatorBrackets(finalExpression))
			throw new SyntaxErrorException("Unary operators must be of form: cos(\"expression\")");
		
		// 6) make sure that the opening and closing brackets all match
		if(bracketMatch(finalExpression) == -1)
			throw new SyntaxErrorException("Unmatched brackets");
		
		// 7) check invalid characters
		if(validCharacters(finalExpression) != null)
			throw new SyntaxErrorException();
		
		
		// 8) check invalid expressions
		
		
		// 9) empty brackets
		// note: do this only after converting all non-round brackets "[{" to round brackets "()"
		if(finalExpression.matches("(\\s*)"))
			throw new SyntaxErrorException("Ambiguous empty brackets");
		
		// 10) comma handling
		
		
		return finalExpression;
	}
	
	// TODO test this
	public Character validCharacters(String expression) {
		
		char [] decomposedExpression = expression.toCharArray();
		
		boolean validChar = true;
		
		Character problemChar = null;
		
		for(char c : decomposedExpression) {
			
			// these are the hardcoded valid characters
			validChar = (c >= 'a' && c <= 'z') || 
						(c >= 'A' && c <= 'Z') || 
						(c >= '0' && c <= '9') ||
						c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' ||
						c == '.';
			
			// if not one of the hardcoded then check the operator array
			if(!validChar) {
				for(char op : recoginzedOperatorsSymbols) {
					if(c != op) {
						validChar = false;
					}
				}
			}
			
			problemChar = c;
			
			if(!validChar) { break; }
		}
		
		return new Character(problemChar);
	}
	
	// TODO test this!!
	/**
	 *
	 * @param expression
	 * @return
	 */
	public boolean binaryOperatorsHaveTermsOnBothSides(String expression) {
		
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
	private boolean unaryBinaryOperatorsHaveTermsOnRHS(String expression) {
		
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
	 * @param expression: String
	 * @return true if all unary operators are valid
	 */
	private boolean validUnaryOperatorBrackets(String expression) {
		
		// do this check for each unary operator defined in the array
		for(int i = 0; i < unaryOperators.length; ++i) {
			
			int index = expression.indexOf(unaryOperators[i]);
			boolean isFirstPassOnOperator = true;
			
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
	private boolean danglingOperator(String original) {
		
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
	private String replaceBrackets(String original) {
		return original.replace("[", "(").replace("]",")").replace("{", "(")
				.replace("}", ")");
	}
	
    /**
     * Given a mathematical expression, this will tell you if the brackets match or not. A match
     * is when it returns the index of the ending bracket. Invalid brackets return -1.
     * @param expression: String
     * @return index of ending bracket: int
     */
    private int bracketMatch(String expression) {

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
    private String removeSpaces(String expression) {
        return expression.replaceAll("\\s+", "");
    }
}
