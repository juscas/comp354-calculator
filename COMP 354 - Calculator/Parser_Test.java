import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class Parser_Test
{
	
	Parser p1 = new Parser();
	
	@Test
	void bracketMatch_Test() {
		
		boolean pass = false;
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
		
		// test the good strings
		int i = 0;
		for( ; i < goodBrackets.length; ++i) {
			pass = p1.bracketMatch(goodBrackets[i]);
		}
		
		// test the bad strings
		int j = 0;
		for( ; i < badBrackets.length; ++i) {
			pass = !p1.bracketMatch(badBrackets[i]);
		}
		
		assertTrue(pass);
		
	}

}
