import java.util.Stack;

public class Parser {
	
	/**
	 * Given a mathematical expression, this will tell you if the brackets match or not. A match
	 * is when it returns the index of the ending bracket. Invalid brackets return -1.
	 * @param expression: String
	 * @return index of ending bracket: int
	 */
	public int bracketMatch(String expression) {
		
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
