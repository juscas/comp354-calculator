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

//Dany2 Functions
	/**
     * Given a mathematical expression, it converts cos an sin into the special characters @ AND ~ respectively, and
     * will insert a special character followed by a priority to the left of each operator.
     * + , - , ^ ,  * ,and / have static priorities , which are marked by the special character '#'
     * cos/sin , () , {} ,and [] have modular priorities, marked by special character '$' , as the functions inside them will be prioritized as well
     * @param input: String
     * @return prioritised function with special characters
     */

    public static String prioritiser(String input){

        System.out.println(input);

        int levelnumb=0;

        int pointer=0;
        String newStr="";
        String[] level=new String[0];

        //replacing cosine with special character @
        input = input.replaceAll("cos\\(","@( ");

        //replacing cosine with special character ~
        input = input.replaceAll("sin\\(","~( ");
       // System.out.println(input);

        //keep iterating until pointer reaches the last char
        while(pointer<input.length()){

            //if the char pointed at is '(,+,-,*,/' then a suitable priority number as well as a special character (parenthesis are given a different
            // special characters from operators) is assigned to its left, in a new string called newStr
               switch (input.charAt(pointer)){
                   //in case the character is (,[,{,@(which stands for cosine), or ~ (which stands for sin) , we assign priority '$'+levelnumber
                   case '(':
                   case '{':
                   case'[':
                       {
                       newStr = newStr + " $" + levelnumb+" "+input.charAt(pointer);
                       levelnumb = levelnumb+1;
                       break;
                   }
                   //in case the character is ^ we assign priority #2
                   case '^':
                   {
                       newStr = newStr + " #5 "+input.charAt(pointer);
                       break;
                   }
                   //in case the character is + or - we assign priority #4
                   case '+':
                   case '-':
                   {
                       newStr = newStr + " #3 "+input.charAt(pointer);
                       break;
                   }
                   //in case the character is * or / we assign priority #3
                   case '*':
                   case '/':
                   case'@':
                   case'~':
                   {
                       newStr = newStr + " #4 "+input.charAt(pointer);
                       break;
                   }
                    //default case add the character pointed at to the new string
                   default:{
                       newStr=newStr+input.charAt(pointer);
                   }
               }
           pointer++;

        }
        //pass the string to the disector function, along with the number of brackets -1 since levelnumb is 1 ahead of the actual number
        return  disector(newStr.replaceAll(" ",""),levelnumb-1);
    }


    /**
     * A follow up function to the prioritiser function that handles the expressions with modular priorities (i.e. $)
     * It operates by isolating the expressions with the modular priority, calculating it in a seperate function
     * and replacing the expression with the answer, all while taking priorities into account
     * @param str: String with included priorities
     * @param highestLevel the number of brackets there are (starting from 0)
     * @return prioritised function with special characters
     */
    public static String disector(String str,int highestLevel){
        System.out.println(str);

        //if the string has $
        if(str.contains("$")){
            String passedString = "";
            int level =highestLevel;
            int startPointer;
            int endPointer;

            //boolean array that keeps track whether the passed string contains other forms of brackets or not (to reduce calculations)
            boolean otherBrackets[] = {str.contains("]"),str.contains("}"),};
           while(level>=0){

               //creating a secondary pointer to keep track of the start index
               startPointer=str.indexOf("$"+level);

               endPointer=str.indexOf(")",startPointer);

               //if there is a ] in the expression
               if(otherBrackets[0]){

                   //calculating the index of the first ] after startPointer
                   int index = str.indexOf("]",startPointer);
                   //if there is no ] then set the otherBracket[0] to false. else compare endPointer and index and choose the lowest
                   if(index==-1){
                       otherBrackets[0]=str.contains("]");      //reevaluate if the entire string still contains ]
                   }else{
                       System.out.println("tttttttttttttttttttttt");
                       //set endPointer = index if our current endPointer is > index , or if the are no ')' passed the starting point
                       if(endPointer>index||endPointer==-1) endPointer=index;
                   }
               }

               //if there is a ] in the expression
               if(otherBrackets[1]){
                   //calculating the index of the first ] after startPointer
                   int index = str.indexOf("}",startPointer);

                   //if there is no } then set the otherBracket[1] to false. else compare endPointer and index and choose the lowest
                   if(index==-1){
                       otherBrackets[0]=str.contains("}");      //reevaluate if the entire string still contains }
                   }else{
                       //set endPointer = index if our current endPointer is > index , or if the are no ')\]' passed the starting point
                       if(endPointer>index||endPointer==-1) endPointer=index;
                   }
               }

               System.out.println(otherBrackets[0]);
                System.out.println(endPointer);
               passedString = str.substring(startPointer+2,endPointer+1);
               System.out.println(passedString);

               str=str.substring(0,startPointer)+calculate(passedString)+str.substring(endPointer+1);

               System.out.println(str);
               level--;
           }
           System.out.println(str);
        }

        //calculate the final simple expression and then return it
        return calculate(str);
    }

    public static String calculate(String str){


        return "6";
    }
	
	
}
