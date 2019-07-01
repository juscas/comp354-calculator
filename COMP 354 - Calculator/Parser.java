import java.util.Stack;

public class Parser {
	
	/**
	 * Given a mathematical expression, this will tell you if the brackets match or not.
	 * @param expression: String
	 * @return if brackets match or not: boolean
	 */
	public boolean bracketMatch(String expression) {

		String opening = "({[";
		String closing = ")}]";
		
		Stack<Character> buffer = new Stack<>();
		
		// Check each character of the expression for brackets (skip over non brackets)
		for(char c : expression.toCharArray()) {
			
			if(opening.indexOf(c) != -1) {	// if c is an opening bracket
				
				buffer.push(c);
				
			}
			else if(closing.indexOf(c) != -1) { // if c is a closing bracket
				
				if(buffer.isEmpty()) // we are missing an opening bracket
					return false;
				
				if(closing.indexOf(c) != opening.indexOf(buffer.pop()))
					return false; // current closing bracket does not match opening bracket on stack
			}
		}
		
		// if after all that, the buffer is empty then the brackets match
		return buffer.isEmpty();
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
