import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class Parser {

    private static boolean debug=false;

    /**
     * Wrapper function for the parse function. Accepts a 2nd parameter which, if = to 1 ,runs the program in debugger mode
     * @param input : String
     * @param debugParameter : int
     * @return String
     */
    public static String parse(String input, int debugParameter){
        if(debugParameter==1) debug = true;
        return parse(input);
    }

//ToDO: add support for the square root function
    /**
     * Initialising function that initiates the parsing process. It also defines weather the user wishes to enter debug mode or not
     * @param input : String
     * @return String
     */
    public static String parse(String input){

        //the debug boolean has to be set before passin the string to the validator, since the validator will remove the 'debug' term
        if(input.contains("debug")){
            debug = true;
        }
        //validating the expression
        input = ExpressionValidator.validateExpression(input);



        //creating a formatter object
        DecimalFormat df = new DecimalFormat("0");

        //specifying he number of fractions we want
        df.setMaximumFractionDigits(7);

        //calculating the answer
        String output = prioritiser(input).replaceAll("-\\s*-", "+").replaceAll("\\+\\s*\\+", "+");

        //formatting the value
         output = df.format(Double.parseDouble(output));
        return output;
    }
    /**
     * Given a mathematical expression, it converts cos an sin into the special characters @ AND ~ respectively, and
     * will insert a special character followed by a priority to the left of each operator.
     * + , - , ^ ,  * ,and / have static priorities , which are marked by the special character '#'
     * cos/sin , () , {} ,and [] have modular priorities, marked by special character '$' , as the functions inside them will be prioritized as well
     * @param input: String
     * @return prioritised function with special characters
     */

    public static String prioritiser(String input) throws SyntaxErrorException{

        if (debug) System.out.println("Initial input : "+input);

        int levelnumb=0;

        int pointer=0;

        StringBuilder newStr = new StringBuilder("");

        //String newStr="";
        String[] level=new String[0];

        //filtering out words and replacing them with special characters
        input = replaceWithSpecialChars(input);

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
                    //newStr = newStr + " $" + levelnumb+" (";
                    newStr.append(" $" + levelnumb+" (");
                    levelnumb = levelnumb+1;
                    break;
                }
                //in case the character is ] or },  replace it with )
                case ']':
                case '}':
                {
                    //newStr = newStr + ")";
                    newStr.append(")");
                    break;
                }
                //unary operators are given the highest priority
                case'@':    //cos
                case'~':    //sin
                case'L':    //log
                case'N':    //ln
                case'E':    //e^x
                case'R':    //root
                case'O':    // cot
                case'T':    // Tanget
                case'C':    // csc
                case'S':    // sec
                case'H':    //cosh
                case'Q':    //sqrt
                case'I':    //sinh
                case'!':    //factorial
                {
                    //newStr = newStr + " #4 "+input.charAt(pointer);
                    newStr.append(" #4 "+input.charAt(pointer));
                    break;
                }
                //in case the character is ^ we assign priority #3
                case '^':
                {
                    //newStr = newStr + " #3 "+input.charAt(pointer);
                    newStr.append(" #3 "+input.charAt(pointer));
                    break;
                }
                //in case the character is * or / we assign priority #2
                case '*':
                case '/':
                {
                    //newStr = newStr + " #2 "+input.charAt(pointer);
                    newStr.append(" #2 "+input.charAt(pointer));
                    break;
                }
                //in case the character is + or - we assign priority #1
                case '+':
                case '-':
                {
                    if(pointer==0||isSign(input.charAt(pointer-1))){

                        //newStr=newStr+input.charAt(pointer);
                        newStr.append(input.charAt(pointer));
                    }else {
                        //newStr = newStr + " #1 " + input.charAt(pointer);
                        newStr.append( " #1 " + input.charAt(pointer));
                    }
                    break;
                }
                //default case add the character pointed at to the new string
                default:{
                    //newStr=newStr+input.charAt(pointer);
                    newStr.append(input.charAt(pointer));
                }
            }
            pointer++;

        }
        if (debug) System.out.println("     t : "+newStr.toString());
        //pass the string to the disector function, along with the number of brackets -1 since levelnumb is 1 ahead of the actual number
        return  disector(newStr.toString().replaceAll(" ",""),levelnumb-1);
        //return "";
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
        if (debug) System.out.println(str);

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
                if (debug) System.out.println("passed to Calculate : "+passedString);

                str=str.substring(0,startPointer)+calculate(passedString)+str.substring(endPointer+1);

                if (debug) System.out.println("Returned string : "+str);
                level--;
            }
            if (debug) System.out.println(str);
        }

        //calculate the final simple expression and then return it
        return calculate(str);
    }

    public static String calculate(String str){

        if (debug) System.out.println("Calculate in : "+str);

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

                if (debug) System.out.println("\nWorking on priority #"+priority+" : "+str+"\n");

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
                            double[] data = findOperand(str,index);

                            int next =(int)data[3];



                            //System.out.println(str);
                            //Finding the left operand
                            //prev is the index of the # that is before the variable index (it looks backwords)
                            int prev = (int)data[2];

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){
                                //leftOperand=str.substring(0,index);

                                //if the left operand is the character e, then we perform the e_to_x function instead of the power
                                if(data[0]==2.7182818){
                                    str=MathFunctions.e_to_x(data[1])+(next==-1?"":str.substring(next));
                                }else{
                                    str=MathFunctions.power(data[0],data[1])+(next==-1?"":str.substring(next));

                                }
                            }else{
                                //leftOperand=str.substring(prev+3,index);

                                //if the left operand is the character e, then we perform the e_to_x function instead of the power
                                if(data[0]==-2){
                                    str=str.substring(0,prev+3)+MathFunctions.e_to_x(data[1])+(next==-1?"":str.substring(next));
                                }else{
                                    str=(str.charAt(prev)==','?str.substring(0,prev+1):str.substring(0,prev+3))+MathFunctions.power(data[0],data[1])+(next==-1?"":str.substring(next,str.length()));

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
                            double[] data = findOperand(str,index);

                            int next =(int) data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                               // rightOperand = data[1];
                                str=str.substring(0,index)+MathFunctions.cos(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.cos(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }



                            break;
                        }
                        //if the operator is ~ special character for sine
                        case '~': {

                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.sin(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.sin(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //tan
                        case 'T':{

                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.tan(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.tan(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //sec
                        case 'S':{

                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.sec(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.sec(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //cot
                        case 'O':{
                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.cot(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.cot(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //csc
                        case 'C':{

                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.csc(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.csc(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //cosh
                        case 'H':{

                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.cosh(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.cosh(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //root
                        case'R':{
                            //finding the next occurrence of # after the current index
                            int next =str.indexOf("#",index+1);

                            int comaIndex=str.indexOf(",",index+1);

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

                            //converting to double
                            Double exponent = Double.parseDouble(exponentString);

                            str=str.substring(0,index)+MathFunctions.nroot(Double.parseDouble(BaseString),Integer.parseInt(exponentString),19)+(next==-1?"":str.substring(next));

                            break;
                        }
                        //sqrt
                        case'Q':{
                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.squareRoot(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.squareRoot(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //sinh
                        case'I':{
                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);
                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                //rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.sinh(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                //rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.sinh(data[1])+str.substring(next);
                                //System.out.println(rightOperand);
                            }
                            break;
                        }
                        //log base 10
                        case'L':{

                            //finding the next occurrence of # after the current index
                            int next =str.indexOf("#",index+1);


                            int comaIndex=str.indexOf(",",index+1);

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

                            double[] data = findOperand(str,index);

                            int next =(int)data[3];

                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                               // rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.ln(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                               // rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.ln(data[1])+str.substring(next);
                                //System.out.println(rightOperand);

                            }
                            break;
                        }
                        //e^x
                        case 'E' :{

                            //finding the next occurrence of #

                            double[] data = findOperand(str,index);

                            int next =(int)data[3];


                            //separate cases for when the operation is the lat one in the string and when it is not
                            if(next==-1){
                                // rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.e_to_x(data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                // rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.e_to_x(data[1])+str.substring(next);
                                //System.out.println(rightOperand);

                            }
                            break;
                        }
                        case '!':{
                            //Finding the right operand:
                            //finding the next occurrence of #
                            double[] data = findOperand(str,index);

                            int next =(int)data[3];

                            //separate cases for when the operation is the last one in the string and when it is not
                            if(next==-1){
                                // rightOperand = str.substring(index + 3);
                                str=str.substring(0,index)+MathFunctions.factorial((int)data[1]);
                            }else {
                                //the left operand should be 3 indexes after the # until the next #
                                // rightOperand = str.substring(index + 3,next );

                                str=str.substring(0,index)+MathFunctions.factorial((int)data[1])+str.substring(next);
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

                            double operands[] = findOperand(str,index);

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){

                                str=MathFunctions.multiply(operands[0],operands[1])+(next==-1?"":str.substring(next));

                            }else{

                                str=(str.charAt(prev)==','?str.substring(0,prev+1):str.substring(0,prev+3))+MathFunctions.multiply(operands[0],operands[1])+(next==-1?"":str.substring(next,str.length()));
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

                            double operands[] = findOperand(str,index);

                            //separate cases for when the operation is the first one in the string and when it is not
                            if(prev==-1){

                                str=MathFunctions.divide(operands[0],operands[1])+(next==-1?"":str.substring(next));

                            }else{

                                str=(str.charAt(prev)==','?str.substring(0,prev+1):str.substring(0,prev+3))+MathFunctions.divide(operands[0],operands[1])+(next==-1?"":str.substring(next,str.length()));
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

                            double operands[] = findOperand(str,index);

                            //when the operation is the first one in the string
                            if(prev==-1){

                                str=(operands[0]+operands[1])+(next==-1?"":str.substring(next));

                            }else{      //when operation is not the first in the string
                                str=(str.charAt(prev)==','?str.substring(0,prev+1):str.substring(0,prev+3))+(operands[0]+operands[1])+(next==-1?"":str.substring(next,str.length()));

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

                            double operands[] = findOperand(str,index);

                            //when the operation is the first one in the string
                            if(prev==-1){
                                str=(operands[0]-operands[1])+(next==-1?"":str.substring(next));

                            }else{      //when operation is not the first in the string
                                str=(str.charAt(prev)==','?str.substring(0,prev+1):str.substring(0,prev+3))+(operands[0]-operands[1])+(next==-1?"":str.substring(next,str.length()));
                            }
                            break;

                        }
                    }
                }
                //recalculating the index of the other special character of the current priority (will be -1 if does not exist)
                index = str.indexOf("#"+priority);
            }

        }

        if (debug) System.out.println("Calculate out : "+str);
        return str;
    }
    private static double[] findOperand(String str, int index){

        String rightOperand ;
        String leftOperand ;

        //finding the next occurrence of # or , whichever comes first (helps us know if the operation is the last one in the string)
        int next =(int) poistiveMin((double)str.indexOf("#",index+1),(double)str.indexOf(",",index+1));

        //finding the previous occurrence of # or , whichever comes first (helps us know if the operation is the first one in the string)
        int prev = (int) MathFunctions.max((double)str.lastIndexOf("#",index-1),(double)str.lastIndexOf(",",index-1));

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
            //if prev points to a comma, left operand starts at index+1, else at prev+3
            leftOperand=str.charAt(prev)==','?str.substring(prev+1,index):str.substring(prev+3,index);

        }
        double returnedLeft;


        if(leftOperand.equals("")){         //if there is no left operand, return -10
            returnedLeft=-10;
        }else{
            if(leftOperand.equals("e")){      //if the left operand is eulers number, return -2
                returnedLeft = 2.7182818;
            }else{
                returnedLeft = Double.parseDouble(leftOperand);     //else return the value of the left operand
            }
        }
        if(rightOperand.equals("")) rightOperand = "-10";

        double temp[] = {returnedLeft,Double.parseDouble(rightOperand),(double)prev,(double)next};
        return temp;

    }
    //function that returns the smaller of the 2 values, with the condition that it is a positive value, else it returns -1
    private static double poistiveMin(double x, double y){
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

    public static String replaceWithSpecialChars(String input){

        //fact replaced with !
        input = input.replaceAll("fact","!");

        //Cosine replaced with @
        input = input.replaceAll("cos\\(","@(");

        //Sin replaced with ~
        input = input.replaceAll("sin\\(","~(");

        //sqrt replaced with Q
        input = input.replaceAll("sqrt","Q");

        //Tan replaced with T
        input = input.replaceAll("tan","T");

        //csc replaced with C
        input = input.replaceAll("csc","C");

        //sec replaced with S
        input = input.replaceAll("sec","S");

        //Cot replaced with O
        input = input.replaceAll("cot","O");

        //Sinh replaced with I
        input = input.replaceAll("sinh","I");

        //Cosh replaced with H
        input = input.replaceAll("cosh","H");

        //Ln replaced with N
        input = input.replaceAll("ln\\(","N(");

        //replacing log with L
        input = input.replaceAll("exp\\(","E(");

        //handling roots and logs
        input = Parser.logHandler(Parser.rootHandler(input));
        return input;
    }
    private static String logHandler(String input ){

        //input = input.replaceAll("log","(log");
        int closingIndex;
        int logIndex = input.indexOf("log");
        int openIndex;
        int newIndex;

        while (logIndex!=-1) {
            openIndex = logIndex+3;
            closingIndex = ExpressionValidator.getClosingBracket(input, openIndex);
            newIndex = openIndex-3;
            input = input.substring(0,logIndex)+"(L"+input.substring(logIndex+3,closingIndex)+")"+input.substring(closingIndex);
            logIndex = input.lastIndexOf("log");
        }
        return input;
    }
    private static String rootHandler(String input ){

        //input = input.replaceAll("log","(log");
        int closingIndex;
        int rootIndex = input.indexOf("root");
        int openIndex;
        int newIndex;

        while (rootIndex!=-1) {
            openIndex = rootIndex+4;
            closingIndex = ExpressionValidator.getClosingBracket(input, openIndex);
            newIndex = openIndex-3;
            input = input.substring(0,rootIndex)+"(R"+input.substring(rootIndex+4,closingIndex)+")"+input.substring(closingIndex);
            rootIndex = input.lastIndexOf("root");
        }
        return input;
    }


}
