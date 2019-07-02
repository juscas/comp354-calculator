import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class Parser_Test
{
	
	Parser p1 = new Parser();
	
	@Test
	void bracketMatch_Test() {
		
		boolean pass = true;
		
		String[] goodBrackets = {
				"()(()){([()])}",
				"((()(()){([()])}))",
				"(){}[]",
				"()",
				""
		};
		
		String[] badBrackets = {
				")(()){([()])}",
				"({[])}",
				"(",
				")"
		};
		
		// test the good strings (to pass, pass must not be equal to -1 after this loop)
		for(int i = 0 ; i < goodBrackets.length; ++i) {
			if(p1.bracketMatch(goodBrackets[i]) == -1)
				pass = false;
				
		}
		
		// test the bad strings (to pass, fail must be equal to -1 after this loop)
		for(int i = 0 ; i < badBrackets.length; ++i) {
			if(p1.bracketMatch(badBrackets[i]) != -1)
				pass = false;
		}
		
		assertTrue(pass);
		
	}

}
