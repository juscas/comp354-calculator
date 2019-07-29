package Model;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import Controller.SyntaxErrorException;

///import Trie;

public class UserConstants implements Serializable
{
	
	
	/**
	 * Some serialUID (randomly generated)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This will hold all of the constants.
	 */
	public static Map<Character, Double> constants  = new HashMap<Character, Double>();
	
	// Add Euler's constant to the map (it cannot be redefined as it is defined as 'invalid' below)
	
//	public UserConstants() {
//		constants.put('e', MathFunctions.E);	// define Euler's number
//		constants.put('p', MathFunctions.PI);	// define Pi
//	}
	
	
	/**
	 * This will create a new Constant. If a constant already exists of the same 'letter' then 
	 * it will overwrite the value associate with that letter. It returns a message saying what
	 * action was undertaken.
	 * @param letter : char
	 * @param value : double
	 * @return output msg : String
	 */
	public static String addConstant(char letter, double value) throws SyntaxErrorException {
		
		// Check if the letter is a valid constant
		if(!validConstantLetter(letter))
			throw new SyntaxErrorException("Error: Constants must be a single alphabetic character");
		
		constants.put(letter, value);
		return "Constant " + letter + " is defined as " + value;
		
	}
	
	/**
	 * Given a letter, this will return the corresponding double value as a String.
	 * @param letter
	 * @return
	 */
	public static String getValue(char letter) throws SyntaxErrorException {
		
		// Formats a decimal as this function returns a String number
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(10);
		
		String constantValueAsString = "";
		
		// Reserved constant letters are defined here!!
		
		if(letter == 'e')
			return df.format(MathFunctions.E);
		
		if(letter == 'p')
			return df.format(MathFunctions.PI);
		
		if(!constants.containsKey(letter))
			throw new SyntaxErrorException("Error: \"" + letter + "\"" + " is not defined");
		
		return df.format(constants.get(letter));
	}
	
	
	/**
	 * A constant is valid if it is a single character 'a' to 'z'
	 * @param letter : char
	 * @return true if valid constant label
	 */
	private static boolean validConstantLetter(char letter) {
		
		if(letter == 'e' || letter == 'p') { // 'e' and 'pi' are predefined constants (no changing)
			throw new SyntaxErrorException("That is a reserved constant and cannot be reset");
		}
		
		if(letter >= 97 && letter <= 122) {
			return true;
		}
		
		return false;
			
	}
	
}



























//Constant toModify = getDuplicateConstant(letter);
//
//if(toModify == null) { // if not duplicate then add a new Constant to list
//	constantList.add(new Constant(letter, value));
//	return "New constant " + letter + " = " + value;
//}
//else { // constant already exists -> overwrite its value
//	toModify.setValue(value);
//}



///**
//* This will return the user-defined constant that has the same letter as the one that we are 
//* trying to create. If no such constant is yet defined then returns null.
//* @param letter : char
//* @return duplicate : Constant
//*/
//private Constant getDuplicateConstant(char letter) {
//	
//	for(Constant c : constantList) {
//		if(letter == c.getLetter())
//			return c;
//	}
//	return null;
//}