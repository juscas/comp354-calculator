import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * This is a Trie data structure that will hold the names of all of the stock (non-user-defined)
 * functions. The reason for this Trie is that the parser needs to quickly determine if it has 
 * scanned an entire function name from the expression passed to it by user input into the calc.
 * @param <T>
 */
public class Trie
{
	/**
	 * These are the Nodes that comprise the Trie. They have Map of Character that holds one of the
	 * characters of a word. Each Node also has a flag that says if the previous Node's key is a
	 * word.
	 */
	private static class Node {
		
		public Map<Character, Node> children;
		public boolean isWord = false;
		
		/**
		 * This is the constructor for creatig
		 */
		private Node() {
			children = new HashMap<Character, Node>();
			isWord = false;
		}
		
		private Node(char letter, boolean isWord) {
			children = new HashMap<Character, Node>();
			this.isWord = isWord;
			children.put(letter, new Node());
		}

	} // END OF STATIC INNER CLASS
	
	/**
	 * This is the root node of our Trie. It holds the first letters of every word and the mapping
	 * to each of the subsequent nodes associated to those letters.
	 */
	private Node root;
	
	// THIS CONSTRUCTOR IS ONLY FOR TESTING
	public Trie() {
		root = new Node(); // set root node
	}
	
	/**
	 * This constructor expects an array of the names of the functions.
	 * ex. String[] fnArray = { "sin", "cos", "cosh", "exp" };
	 * It will add each of these words to the trie.
	 * 
	 * @param nameArray : String[]
	 */
	public Trie(String[] nameArray) {
		
		 // create root node
		root = new Node();
		
		// populate Trie with the words from the functions name array.
		for(String s : nameArray) {
			this.add(s);
		}
	}
	

	/**
	 * This function will add a word to the Trie.
	 * 
	 * As a bonus, this will return 'true' if the word already existed in the Trie.
	 * 
	 * @param word : String
	 */
	public boolean add(String word) {
		
		Node iteratorNode = root;
		boolean alreadyExists = false;
		
		for(int i = 0; i < word.length(); ++i){
		
			char currentLetter = word.charAt(i);
			
			// if the root does not contains the first char of our word then add it
			if(!iteratorNode.children.containsKey(currentLetter)) {
				iteratorNode.children.put(currentLetter, new Node());
			}
		
			iteratorNode = nextNode(iteratorNode, currentLetter);
		}
		
		// we are at the end of the String so we set the 'isWord' flag to 'true'
		// if the flag was already set then the word already exists
		if(iteratorNode.isWord)
			alreadyExists = true;
		else
			iteratorNode.isWord = true;
		
		return alreadyExists;
	}
	
	/**
	 * This will look in the Trie to find the word. Returns 'true' if found and 'false' if not
	 * found. Note that it is possible to search for a substring of a word and have this method
	 * return 'false' when the substring was not itself defined as a word.
	 * 
	 * ex. 	Trie MyTrie = new Trie;
	 * 		MyTrie.add("sinus");
	 * 		MyTrie.find("sin"); // would give false as "sin" was not added as a word.
	 * 
	 * @param word : String
	 * @return 'true' if word is found
	 */
	public boolean find(String word) {
		
		Node iteratorNode = root;
		
		for(int i = 0; i < word.length(); ++i) {
			
			char currentLetter = word.charAt(i);
			
			// if the Node contains the key then examine next Node
			if(iteratorNode.children.containsKey(word.charAt(i))) {
				iteratorNode = nextNode(iteratorNode, currentLetter);
			}
			else
				return false; // if a single letter is not found then our word does not exist yet
		}
		return iteratorNode.isWord; // can be false if we look for a substr that is not a word
	}
	
	
	
	public boolean remove(String word) {
		
		/*
		 * This will try to delete as many letters as possible from a word. 
		 *	- add("sin"); add("sinus"); remove("sinus"); -> must leave "sin"
		 *		- we can only remove the unique suffix of a word (ie. the "us" of "sinus")
		 *		- if a word has any letters after it, then it has no unique suffix.
		 *		- if there are >1 word in a branch (ie. "sinus", "sinful") we refer to the
		 *		  Node where the tree branches off as a 'branching' (here: at 4th node ('u' and 'f')
		 *		- There may be multiple branchings in a 'word', we are interested in retaining the 
		 *		  branching that is closes to the end of the 'word'. We can delete the character
		 *		  at this branching Node and that will effectively delete the unique suffix.
		 *		
		 */
		
		Node branching = root; // this will hold the final branching.
		
		// The following is essentially a modified 'find' method that also records the branching
		Node iteratorNode = root;
		
		char firstLetterOfUniqueSuffix; // holds the letter of the first branching
		char currentLetter = firstLetterOfUniqueSuffix = word.charAt(0);
		
		for(int i = 0; i < word.length(); ++i) {
			
			currentLetter = word.charAt(i);
			
			// if the Node contains the key then examine next Node
			if(iteratorNode.children.containsKey(word.charAt(i))) {
				
				// if we were on a word OR node after has more than 1 child (branching)
				if(iteratorNode.isWord || iteratorNode.children.size() > 1) {
					branching = iteratorNode; // set the branching to here
					firstLetterOfUniqueSuffix = currentLetter; // retain the letter at the branching
				}
				
				iteratorNode = nextNode(iteratorNode, currentLetter);
			}
			// if a single letter is not found then our word does not exist yet
			else
				return false; 
		}
		
		// if you get to the end of a word and the 'word' flag is not set
		if(!iteratorNode.isWord) 
			return false;
			
		// if 'word' has dependent children then it is a prefix and we must preserve all its letters
		if(iteratorNode.children.size() != 0)
			iteratorNode.isWord = false;
		// there was a word or a branching so we can only delete the unique suffix of 'word'
		else {
			// 1) 'word' that is not a prefix also has no dependent branchings -> delete all letters
			if(branching == root)
				root.children.remove(word.charAt(0));
			
			// 2) there is a branching -> remove the letter at the last branching
			if(branching.isWord || branching.children.size() > 1)
				branching.children.remove(firstLetterOfUniqueSuffix);
		}
		
		return true;
	}
	
	
	
	/**
	 * This returns the next node that is mapped to by the character key. This is mainly a helper
	 * functions that cleans up the syntaxt of obtaining the next node.
	 * 
	 * @param current : Node
	 * @param key : char
	 * @return the next Node
	 */
	private Node nextNode(Node current, char key) {
		return current.children.get(key);
	}

}
