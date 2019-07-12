import java.util.Calendar;
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

    public String prioritiser(String input) throws SyntaxErrorException{

        //TODO: remove after the validator is implemented
        if(bracketMatch(input)==-1) throw new SyntaxErrorException();

        System.out.println(input);

        int levelnumb=0;

        int pointer=0;
        String newStr="";
        String[] level=new String[0];

        //replacing cosine with special character @
        input = input.replaceAll("cos\\(","@(");

        //replacing cosine with special character ~
        input = input.replaceAll("sin\\(","~(");
        // System.out.println(input);

        //replacing log with L
        input = input.replaceAll("log\\(","L(");

        //replacing ln with N
        input = input.replaceAll("ln\\(","N(");


        //keep iterating until pointer reaches the last char
        while(pointer<input.length()){

            //if the char pointed at is '(,+,-,*,/' then a suitable priority number as well as a special character (parenthesis are given a different
            // special characters from operators) is assigned to its left, in a new string called newStr
            switch (input.charAt(pointer)){
                //in case the character is (,[,{, we assign priority '$'+levelnumber and replace it with )
                case '(':
                case '{':
                case'[':
                {
                    newStr = newStr + " $" + levelnumb+" (";
                    levelnumb = levelnumb+1;
                    break;
                }
                //in case the character is ] or },  replace it with )
                case ']':
                case '}':
                {
                    newStr = newStr + ")";
                    break;
                }
                //unary operators are given the highest priority
                case'@':
                case'~':
                case'L':
                case'N':
                {
                    newStr = newStr + " #4 "+input.charAt(pointer);
                    break;
                }
                //in case the character is ^ we assign priority #3
                case '^':
                {
                    newStr = newStr + " #3 "+input.charAt(pointer);
                    break;
                }
                //in case the character is * or / we assign priority #2
                case '*':
                case '/':
                {
                    newStr = newStr + " #2 "+input.charAt(pointer);
                    break;
                }
                //in case the character is + or - we assign priority #1
                case '+':
                case '-':
                {
                    System.out.println(pointer-1 + " input : "+input+" "+" charAt: "+input.charAt(pointer-1));
                    if(pointer==0||isSign(input.charAt(pointer-1))){

                        newStr=newStr+input.charAt(pointer);
                    }else {
                        newStr = newStr + " #1 " + input.charAt(pointer);
                    }
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
       // return newStr;
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

                passedString = str.substring(startPointer+2,endPointer+1);
                System.out.println("passed to Calculate : "+passedString);

                str=str.substring(0,startPointer)+calculate(passedString)+str.substring(endPointer+1);

                System.out.println("Returned string : "+str);
                level--;
            }
            System.out.println(str);
        }

        //calculate the final simple expression and then return it
        return calculate(str);
    }

    public static String calculate(String str){

        System.out.println("Calculate in : "+str);

        //removing any brackets that are passed to the string
        str=str.replaceFirst("[()]","");

        //the above function only removes ( , so we do it again to remove )
        str=str.replaceFirst("[()]","");

        //if the string contains any more brackets, return them back to the disector function to deal with them
        if(str.contains("[()]")) return str;

        //setting the initial priority to the highest one
        int priority =5;
        char operator;
        String leftOperand;
        String rightOperand;

        //iterating through the priorities from highest to lowest
        for(int i=0;i<priority;priority--){

            //finding the index of the special character followed by the current priority
            int index = str.indexOf("#"+priority);

            //int j=1;

            //there might be multiple operators with the same priority, hence the while loop
            while(index!=-1){
              //  j--;

                //recalculating the index of the other special character of the current priority (will be -1 if does not exist)
                index = str.indexOf("#"+priority);

                //finding the operator that follows #P
                operator=str.charAt(index+2);

                //System.out.println(operator);

                //if the priority is set to 5, we limit the operators to those with priority of 5
                if(priority==3){
                    switch(operator){

                        // handles e^x function as well
                        case '^':{

                            //Finding the right operand:
                            //finding the next occurrence of #
                            int next =str.indexOf("#",index+1);

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                rightOperand = str.substring(index + 3);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                rightOperand = str.substring(index + 3,next );

                            }
                            //System.out.println(str);
                            //Finding the left operand
                            //prev is the index of the # that is before the variable index (it looks backwords)
                            int prev = str.lastIndexOf("#",index-1);

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){
                                leftOperand=str.substring(0,index);

                                //if the left operand is the character e, then we perform the e_to_x function instead of the power
                                if(leftOperand.equals("e")){
                                    str=MathFunctions.e_to_x(Double.parseDouble(rightOperand))+(next==-1?"":str.substring(next));
                                }else{
                                    str=MathFunctions.power(Double.parseDouble(leftOperand),Double.parseDouble(rightOperand))+(next==-1?"":str.substring(next));

                                }
                            }else{
                                leftOperand=str.substring(prev+3,index);

                                //if the left operand is the character e, then we perform the e_to_x function instead of the power
                                if(leftOperand.equals("e")){
                                    str=str.substring(0,prev+3)+MathFunctions.e_to_x(Double.parseDouble(rightOperand))+(next==-1?"":str.substring(next));
                                }else{
                                    str=str.substring(0,prev+3)+MathFunctions.power(Double.parseDouble(leftOperand),Double.parseDouble(rightOperand))+(next==-1?"":str.substring(next));
                                }
                            }

                            break;


                        }
                    }
                }
                //System.out.println(operator);
                //urinary operators have highest priority
                if(priority==4){
                    switch(operator) {
                        //if the operator is @ (special character for cosine)
                        case '@': {

                            //Finding the right operand:
                            //finding the next occurrence of #
                            int next =str.indexOf("#",index+1);

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.cos(Double.parseDouble(rightOperand));
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.cos(Double.parseDouble(rightOperand))+str.substring(next);
                                //System.out.println(rightOperand);
                            }



                            break;
                        }
                        //if the operator is ~ special character for sine
                        case '~': {

                            //Finding the right operand:
                            //finding the next occurrence of #
                            int next =str.indexOf("#",index+1);

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.cos(Double.parseDouble(rightOperand));
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.sin(Double.parseDouble(rightOperand))+str.substring(next);
                                //System.out.println(rightOperand);
                                break;
                            }

                        }
                        //log base 10
                        case'L':{

                            //finding the next occurrence of #
                            int next =str.indexOf("#",index+1);


                            int comaIndex=str.indexOf(",");

                            //if the index of , is not -1
                            if(comaIndex!=-1){
                                //the base would be from the beginning of the string till comma
                                String BaseString = str.substring(index+3,comaIndex);

                                //converting the base back into a double
                                Double Base = Double.parseDouble(BaseString);

                                String exponentString;

                                if(next==-1){       //if there are no operations to the left anymore, the exponent string spans until the end of the string
                                     exponentString = str.substring(comaIndex+1);
                                }else{
                                    //else it spans until the next #
                                     exponentString = str.substring(comaIndex+1,next);
                                }

                                //recalculating the string in case it is an expression
                                //exponentString = calculate(exponentString);

                                //converting to double
                                Double exponent = Double.parseDouble(exponentString);

                                str=str.substring(0,index)+MathFunctions.log(Double.parseDouble(BaseString),Double.parseDouble(exponentString))+(next==-1?"":str.substring(next));

                            }else{
                                String exponentString = str.substring(index+3);
                                str=str.substring(0,index)+MathFunctions.log(Double.parseDouble(exponentString))+(next==-1?"":str.substring(next));
                            }

                            break;

                        }
                        //ln function
                        case 'N' :{
                            //Finding the right operand:
                            //finding the next occurrence of #
                            int next =str.indexOf("#",index+1);

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.ln(Double.parseDouble(rightOperand));
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.ln(Double.parseDouble(rightOperand))+str.substring(next);
                                //System.out.println(rightOperand);

                            }
                            break;
                        }
                    }
                }

                //if the priority is set to 5, we limit the operators to those with priority of 4
                if(priority==2){
                    switch(operator){
                        //if the operator is *
                        case '*':{

                            boolean containsComma =str.contains(",");

                            //finding the next occurrence of # or , whichever comes first (helps us know if the operation is the last one in the string)
                            int next =(int) poistiveMin((double)str.indexOf("#",index+1),(double)str.indexOf(",",index+1));

                            //finding the previous occurrence of # or , whichever comes first (helps us know if the operation is the first one in the string)
                            int prev = (int) MathFunctions.max((double)str.lastIndexOf("#",index-1),(double)str.lastIndexOf(",",index-1));

                            Double operands[] = findOperand(str,index,prev,next);

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){

                                str=MathFunctions.multiply(operands[0],operands[1])+(next==-1?"":str.substring(next));

                            }else{

                                str=str.substring(0,prev+1)+MathFunctions.multiply(operands[0],operands[1])+(next==-1?"":str.substring(next,str.length()));
                            }
                            break;
                        }
                        //if the operator is /
                            //Note: / is not picking up in a string so we might have to replace / with // which makes it work
                        case '/':{
                            boolean containsComma =str.contains(",");

                            //finding the next occurrence of # or , whichever comes first (helps us know if the operation is the last one in the string)
                            int next =(int) poistiveMin((double)str.indexOf("#",index+1),(double)str.indexOf(",",index+1));

                            //finding the previous occurrence of # or , whichever comes first (helps us know if the operation is the first one in the string)
                            int prev = (int) MathFunctions.max((double)str.lastIndexOf("#",index-1),(double)str.lastIndexOf(",",index-1));

                            Double operands[] = findOperand(str,index,prev,next);

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){

                                str=MathFunctions.divide(operands[0],operands[1])+(next==-1?"":str.substring(next));

                            }else{

                                str=str.substring(0,prev+1)+MathFunctions.divide(operands[0],operands[1])+(next==-1?"":str.substring(next,str.length()));
                            }
                            break;

                        }
                    }
                }

                //if the priority is set to 5, we limit the operators to those with priority of 3
                if(priority==1){
                    switch(operator){
                        //if the operator is +
                        case '+':{

                            boolean containsComma =str.contains(",");

                            //finding the next occurrence of # or , whichever comes first (helps us know if the operation is the last one in the string)
                            int next =(int) poistiveMin((double)str.indexOf("#",index+1),(double)str.indexOf(",",index+1));

                            //finding the previous occurrence of # or , whichever comes first (helps us know if the operation is the first one in the string)
                            int prev = (int) MathFunctions.max((double)str.lastIndexOf("#",index-1),(double)str.lastIndexOf(",",index-1));

                            Double operands[] = findOperand(str,index,prev,next);

                            //when the operation is the first one in the string
                            if(prev==-1){

                                str=(operands[0]+operands[1])+(next==-1?"":str.substring(next));

                            }else{      //when operation is not the first in the string

                                str=str.substring(0,prev+1)+(operands[0]+operands[1])+(next==-1?"":str.substring(next,str.length()));
                            }
                            break;

                        }
                        //if the operator is -
                        case '-':{

                            boolean containsComma =str.contains(",");

                            //finding the next occurrence of # or , whichever comes first (helps us know if the operation is the last one in the string)
                            int next =(int) poistiveMin((double)str.indexOf("#",index+1),(double)str.indexOf(",",index+1));

                            //finding the previous occurrence of # or , whichever comes first (helps us know if the operation is the first one in the string)
                            int prev = (int) MathFunctions.max((double)str.lastIndexOf("#",index-1),(double)str.lastIndexOf(",",index-1));

                            Double operands[] = findOperand(str,index,prev,next);

                            //when the operation is the first one in the string
                            if(prev==-1){
                                leftOperand=str.substring(0,index);

                                str=(operands[0]-operands[1])+(next==-1?"":str.substring(next));

                            }else{      //when operation is not the first in the string
                                leftOperand=containsComma?str.substring(prev+1,index):str.substring(prev+3,index);

                                str=str.substring(0,prev+1)+(operands[0]-operands[1])+(next==-1?"":str.substring(next,str.length()));
                            }
                            break;

                        }
                    }
                }
            }
        }

        System.out.println("Calculate out : "+str);
        return str;
    }
    private static Double[] findOperand(String str, int index,int prev, int next){

        String rightOperand ;
        String leftOperand ;

        boolean containsComma =str.contains(",");

        //Finding the right operand:


        //separate cases for when the operation is the last one in the string and when it is not
        if(next==-1){
            rightOperand = str.substring(index + 3);
        }else {
            //the left operand should be 3 indexes after the # until the next #
            rightOperand = str.substring(index + 3,next );

        }
        //Finding the left operand
        //separate cases for when the operation is the first one in the string and when it is not
        if(prev==-1){
            leftOperand=str.substring(0,index);

        }else{
            leftOperand=containsComma?str.substring(prev+1,index):str.substring(prev+3,index);

        }
        Double temp[] = {Double.parseDouble(leftOperand),Double.parseDouble(rightOperand)};
        return temp;

    }
    //function that returns the smaller of the 2 values, with the condition that it is a positive value, else it returns -1
    public static double poistiveMin(double x, double y){
        //if both values are negative, return 0
        if(x<0&&y<0) return -1.0;

        //if y is less than x, and positive, return y, else return x
        if(y>0){
            if(x>0){
                if(y>x) return x;
                else return y;
            }else return y;
        }
        return x;
    }

    //function that evaluates whether +/- is a sign or operator in the context
    private static boolean isSign(char c){
        if(c=='('||c=='+'||c=='-'||c=='*'||c=='/'||c=='@'||c=='~'||c=='['||c=='{'||c=='^'||c==',') return true;
        else return false;

    }


}
