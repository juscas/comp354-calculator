package View;

import Controller.ExpressionValidator;
import Controller.Parser;
import Controller.SuccessfulAssignmentException;
import Controller.SyntaxErrorException;

import Model.MathErrorException;
import Model.ImaginaryNumberException;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;

public class Main extends Application {

        @Override
        public void start(Stage stage) {

            System.out.println("\nJava Version: "+System.getProperty("java.version"));

            // create num buttons
            Button button_0 = new Button("0");
            Button button_1 = new Button("1");
            Button button_2 = new Button("2");
            Button button_3 = new Button("3");
            Button button_4 = new Button("4");
            Button button_5 = new Button("5");
            Button button_6 = new Button("6");
            Button button_7 = new Button("7");
            Button button_8 = new Button("8");
            Button button_9 = new Button("9");
            Button button_dec = new Button(".");
            Button button_dummy1 = new Button("");
            Button button_dummy2 = new Button("");


            button_0.setMinSize(60,60);
            button_1.setMinSize(60,60);
            button_2.setMinSize(60,60);
            button_3.setMinSize(60,60);
            button_4.setMinSize(60,60);
            button_5.setMinSize(60,60);
            button_6.setMinSize(60,60);
            button_7.setMinSize(60,60);
            button_8.setMinSize(60,60);
            button_9.setMinSize(60,60);
            button_dec.setMinSize(60,60);


            button_0.getStyleClass().add("button_num");
            button_1.getStyleClass().add("button_num");
            button_2.getStyleClass().add("button_num");
            button_3.getStyleClass().add("button_num");
            button_4.getStyleClass().add("button_num");
            button_5.getStyleClass().add("button_num");
            button_6.getStyleClass().add("button_num");
            button_7.getStyleClass().add("button_num");
            button_8.getStyleClass().add("button_num");
            button_9.getStyleClass().add("button_num");
            button_dec.getStyleClass().add("button_num");



            // create operation buttons
            Button button_add = new Button("+");
            Button button_sub = new Button("-");
            Button button_mul = new Button("*");
            Button button_div = new Button("/");
            Button button_equ = new Button("=");
            Button button_br1 = new Button("(");
            Button button_br2 = new Button(")");
            Button button_sqr = new Button("^");
            Button button_rot = new Button("\u207f\u221Ax");
            Button button_sqt = new Button("\u221A");
            Button button_ln = new Button("ln");
            Button button_cos = new Button("cos");
            Button button_sin = new Button("sin");
            Button button_tan = new Button("tan");
            Button button_fac = new Button("!");
            Button button_etx = new Button("e^x");
            Button button_csc = new Button("csc");
            Button button_sec = new Button("sec");
            Button button_cot = new Button("cot");
            Button button_ans = new Button("ANS");
            Button button_sinh = new Button("sinh");
            Button button_cosh = new Button("cosh");
            Button button_tanh = new Button("tanh");
            Button button_log = new Button("log");
            Button button_pi = new Button("\u03c0");
            Button button_e = new Button("e");


            Button button_can = new Button("C");

            button_can.getStyleClass().add("button_num");

            button_add.setMinSize(60,60);
            button_sub.setMinSize(60,60);
            button_mul.setMinSize(60,60);
            button_div.setMinSize(60,60);
            button_equ.setMinSize(60,60);
            button_br1.setMinSize(60,60);
            button_br2.setMinSize(60,60);
            button_sqr.setMinSize(60,60);
            button_sqt.setMinSize(60,60);
            button_rot.setMinSize(60,60);
            button_ln.setMinSize(60,60);
            button_cos.setMinSize(60,60);
            button_sin.setMinSize(60,60);
            button_fac.setMinSize(60,60);
            button_etx.setMinSize(60,60);
            button_tan.setMinSize(60,60);
            button_csc.setMinSize(60,60);
            button_sec.setMinSize(60,60);
            button_cot.setMinSize(60,60);
            button_can.setMinSize(60,60);
            button_ans.setMinSize(60,60);
            button_sinh.setMinSize(60,60);
            button_cosh.setMinSize(60,60);
            button_tanh.setMinSize(60,60);
            button_log.setMinSize(60,60);
            button_pi.setMinSize(60,60);
            button_e.setMinSize(60,60);
            button_dummy1.setMinSize(60,60);
            button_dummy2.setMinSize(60,60);

            button_equ.getStyleClass().add("button_equ");


            // create number grid
            GridPane numGrid = new GridPane();

            // add number buttons to grid

            //row 1
            numGrid.add(button_add, 0, 0, 1, 1);
            numGrid.add(button_7, 1, 0, 1, 1);
            numGrid.add(button_8, 2, 0, 1, 1);
            numGrid.add(button_9, 3, 0, 1, 1);
            numGrid.add(button_ln, 4, 0, 1, 1);
            numGrid.add(button_log, 5, 0, 1, 1);
            numGrid.add(button_sin, 6, 0, 1, 1);
            numGrid.add(button_csc, 7, 0, 1, 1);
            numGrid.add(button_sinh, 8, 0, 1, 1);


            // row 2
            numGrid.add(button_sub, 0, 1, 1, 1);
            numGrid.add(button_4, 1, 1, 1, 1);
            numGrid.add(button_5, 2, 1, 1, 1);
            numGrid.add(button_6, 3, 1, 1, 1);
            numGrid.add(button_sqr, 4, 1, 1, 1);
            numGrid.add(button_etx, 5, 1, 1, 1);
            numGrid.add(button_cos, 6, 1, 1, 1);
            numGrid.add(button_sec, 7, 1, 1, 1);
            numGrid.add(button_cosh, 8, 1, 1, 1);


            // row 3
            numGrid.add(button_mul, 0, 2, 1, 1);
            numGrid.add(button_1, 1, 2, 1, 1);
            numGrid.add(button_2, 2, 2, 1, 1);
            numGrid.add(button_3, 3, 2, 1, 1);
            numGrid.add(button_br1, 4, 2, 1, 1);
            numGrid.add(button_br2, 5, 2, 1, 1);
            numGrid.add(button_tan, 6, 2, 1, 1);
            numGrid.add(button_cot, 7, 2, 1, 1);
            numGrid.add(button_fac, 8, 2, 1, 1);

            // row 4
            numGrid.add(button_div, 0, 3, 1, 1);
            numGrid.add(button_0, 1, 3, 1, 1);
            numGrid.add(button_dec, 2, 3, 1, 1);
            numGrid.add(button_can, 3, 3, 1, 1);
            numGrid.add(button_equ, 4, 3, 1, 1);
            numGrid.add(button_ans, 5, 3, 1, 1);
            numGrid.add(button_sqt, 6, 3, 1, 1);
            numGrid.add(button_rot, 7, 3, 1, 1);
            numGrid.add(button_pi, 8, 3, 1, 1);


            TextArea input = new TextArea();
            input.setPrefSize(500,500);
            TextField console = new TextField();
            console.setPrefHeight(200);
            console.setDisable(true);
            console.setStyle("-fx-opacity: 1;");

            Tooltip inputTooltip = new Tooltip("Type in equations here...");
            input.setTooltip(inputTooltip);

            TextArea output = new TextArea();
            output.setPrefSize(800,800);
            output.setWrapText(true);
            output.setDisable(true);
            output.setStyle("-fx-opacity: 1;");
            output.getStyleClass().add("text-area_output");


            // Clear History button
            Button button_clear_input = new Button("Clear History");

            button_clear_input.setPrefSize(500,0);

            // Keeps answers in a stack so that answers can be popped off
            Stack<String> answerStack = new Stack<String>();

            /*
            ~ Button Events ~

            BUTTON VAR NAME         BUTTON FUNCTION

            button_add              Concats "+" to input
            button_sub              Concats "-" to input
            button_mul              Concats "*" to input
            button_div              Concats "/" to input
            button_br1              Concats "(" to input
            button_br2              Concats ")" to input

            button_0                Concats "0" to input
            .                       .
            .                       .
            .                       .
            button_9                Concats "9" to input

            button_equ              Takes input string, computes answer and sets it to output string
            button_can              Clears input


             */

            button_add.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_add.getText());
            });

            button_sub.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sub.getText());
            });

            button_mul.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "*");
            });

            button_div.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "/");
            });

            button_br1.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_br1.getText());
            });

            button_br2.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_br2.getText());
            });

            button_can.setOnAction(value -> {
                input.setText("");
            });

            button_0.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_0.getText());
            });

            button_1.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_1.getText());
            });

            button_2.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_2.getText());
            });

            button_3.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_3.getText());
            });

            button_4.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_4.getText());
            });

            button_5.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_5.getText());
            });

            button_6.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_6.getText());
            });

            button_7.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_7.getText());
            });

            button_8.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_8.getText());
            });

            button_9.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_9.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_dec.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_sqr.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_dec.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_dec.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_dec.getText());
            });

            button_sin.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_sin.getText());
            });

            button_cos.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_cos.getText());
            });

            button_tan.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_tan.getText());
            });

            button_ln.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_ln.getText());
            });

            button_sec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_sec.getText());
            });

            button_tan.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_tan.getText());
            });

            button_cot.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_cot.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_sqr.getText());
            });

            button_sqt.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "sqrt");
            });

            button_rot.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "root");
            });


            button_fac.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "fact");
            });

            button_csc.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_csc.getText());
            });

            button_log.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_log.getText());
            });

            button_sinh.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_sinh.getText());
            });

            button_cosh.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_cosh.getText());
            });

            button_tanh.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_tanh.getText());
            });

            button_pi.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "p");
            });

            button_e.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + button_e.getText());
            });

            button_etx.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "e^");
            });

            button_ans.setOnAction(value -> {
                String currentInput = input.getText();
                if (!answerStack.empty())
                    input.setText(currentInput + answerStack.peek());
            });



            Parser parser = new Parser();

            ExpressionValidator validator = new ExpressionValidator();

            // equals button -> compute input function string using Dan's PARSER
            button_equ.setOnAction(value -> {
                String currentInput = input.getText();
                String currentOutput = output.getText();
                String answer;

                try {
                    answer = Parser.parse(currentInput);
                } catch (SuccessfulAssignmentException a){
                    input.setStyle("-fx-focus-color:#00B200;-fx-text-box-border: #00B200; -fx-border-width: 5px ;");
                    answer = a.getMessage();
                    console.setText(answer);
                    return;
                } catch (SyntaxErrorException s){
                    input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border:#CE0000; -fx-border-width: 5px ;");
                    answer = s.getMessage();
                    console.setText(answer);
                    input.setText("");
                    input.setText(currentInput);
                    return;
                } catch (MathErrorException m){
                    input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border:#CE0000; -fx-border-width: 5px ;");
                    answer = m.getMessage();
                    console.setText(answer);
                    input.setText("");
                    input.setText(currentInput);
                    return;
                } catch (ImaginaryNumberException i){
                    input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px ;");
                    answer = i.getMessage();
                    console.setText(answer);
                    return;
                } catch (Exception e) {
                    input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px ;");
                    console.setText("Unknown error: please try another function");
                    return;
                }

                input.setStyle("-fx-text-box-border: black;");
                output.setText("\n" + currentInput + " " + button_equ.getText() +"\n" + answer + "\n" + currentOutput);
                answerStack.push(answer);
                input.setText("");
                console.setText("");
            });

            // Equals event for text input + press enter
            input.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.ENTER){
                    String currentInput = input.getText().replace("\n","");
                    String currentOutput = output.getText();
                    String answer;

                    try {
                        answer = parser.parse(currentInput);
                    } catch (SuccessfulAssignmentException a) {
                        input.setStyle("-fx-focus-color:#00B200;-fx-text-box-border: #00B200; -fx-border-width: 5px ;");
                        answer = a.getMessage();
                        console.setText(answer);
                        return;
                    } catch (SyntaxErrorException s){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border:#CE0000; -fx-border-width: 5px;");
                        answer = s.getMessage();
                        console.setText(answer);
                        return;
                    } catch (MathErrorException m){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px;");
                        answer = m.getMessage();
                        console.setText(answer);
                        return;
                    } catch (ImaginaryNumberException i){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px;");
                        answer = i.getMessage();
                        console.setText(answer);
                        return;
                    } catch (Exception e){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px;");
                        console.setText("Unknown error: please try another function");
                        return;
                    }


                    System.out.print(answer);

                    input.setStyle("-fx-text-box-border: black;");
                    output.setText("\n" + currentInput + " " + button_equ.getText() +"\n" + answer + "\n" + currentOutput);
                    answerStack.push(answer);
                    input.setText("");
                    console.setText("");

                }
            });

            button_clear_input.setOnAction(value -> {
                output.setText("");
                answerStack.clear();
            });

            BorderPane rootPane = new BorderPane();
            MenuBar menu = new MenuBar();

            Menu themeMenu = new Menu("Themes");
            themeMenu.setMnemonicParsing(true);

            Menu helpMenu = new Menu("Help");
            helpMenu.setMnemonicParsing(true);

            menu.getMenus().add(themeMenu);
            menu.getMenus().add(helpMenu);
            rootPane.setTop(menu);

            VBox vbox = new VBox(
                    rootPane,   // Menu tabs
                    numGrid,    // Grid of Numbers
                    input,      // Text for input
                    console     // Error messaging
            );

            VBox vbox2 = new VBox(
                    output,
                    button_clear_input
            );

            HBox hbox = new HBox(
                    vbox,
                    vbox2
                        // Text for output
            );

            //Creating a scene object
            Scene scene = new Scene(hbox,900, 600);

            //Set css style
            scene.getStylesheets().add("View/dark.css");


            // Add new menu items to Skins tab
            MenuItem theme1 = new MenuItem("Light");
            theme1.setOnAction(ae -> {
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets()
                        .add("View/light.css");
            });

            MenuItem theme2 = new MenuItem("Dark");
            theme2.setOnAction(ae -> {
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets()
                        .add("View/dark.css");
            });

            themeMenu.getItems()
                    .addAll(theme1,
                            theme2);

            MenuItem openMan = new MenuItem("Open Manual");
            openMan.setOnAction(ae -> {
                TextArea manual = new TextArea("Manual for Eternity Calculator :\n" +
                        "\n" +
                        "*Description : Eternity Calculator is a calculator that \n" +
                        "                             supports many mathematical functions while\n" +
                        "                             maintaining a simple and elegant look. Along \n" +
                        "                             with its simple looks, Eternity has a range \n" +
                        "                             of useful features, all of which will be \n" +
                        "                             mentioned below.\n" +
                        "\n" +
                        "*Requirements : \n" +
                        "\n" +
                        "    - Java 8 or lower\n" +
                        "\n" +
                        "*Features : \n" +
                        "\n" +
                        "    (1) Support for Natural Language : (cos(2)^sqrt(2))\n" +
                        "\n" +
                        "    (2) Assign constants : \n" +
                        "\t- ex : a = 34\n" +
                        "\t- reserved letters : e (Eulers' number) , p (pi)\n" +
                        "\t- letters must be in lower case\n" +
                        "\n" +
                        "\n" +
                        "    (2) Support for multiple themes (Dark/Light)\n" +
                        "\n" +
                        "    (4) Error messages with feedback\n" +
                        "\n" +
                        "(1) Supported Functions and Syntax \n" +
                        "\n" +
                        "    ( Capital words/letters refer to user input )\n" +
                        "\n" +
                        "        -Logarithm base 10 : log(N)\n" +
                        "\n" +
                        "        -Logarithm with user defined base : log(B,N)\n" +
                        "\n" +
                        "        -Linear logarithm : ln(N)\n" +
                        "\n" +
                        "        -nth Root of a number: root(BASE,EXPONENT)\n" +
                        "\n" +
                        "        -Square root : sqrt(N)\n" +
                        "\n" +
                        "        -Cosine : cos(N)\n" +
                        "\n" +
                        "        -Sine : sin(N)\n" +
                        "\n" +
                        "        -Secant : sec(N)\n" +
                        "\n" +
                        "        -Cosecant : csc(N)\n" +
                        "\n" +
                        "        -Cotangent : cot(N)\n" +
                        "\n" +
                        "        -e to the X : e^N\n" +
                        "\n" +
                        "        -Power : N^X\n" +
                        "\n" +
                        "        -Factorial : fact(N) Where N is an Integer\n" +
                        "\n" +
                        "(2) Assigning constants \n" +
                        "      \n" +
                        "      Alphabetical constants are assigned by typing a letter\n" +
                        "\n" +
                        "      followed by an '=' sign and the value you wish to assign \n" +
                        "\n" +
                        "      to it.\n" +
                        "\n" +
                        "      Note : (1) alphabetical constants must be in lower case\n" +
                        "\n" +
                        "                   (2) e and p are reserved characters for Eulers' \n" +
                        "   \n" +
                        "                          number and pi\n" +
                        "\n" +
                        "(3) Changing Themes\n" +
                        "    \n" +
                        "      To changes theme, go to the Themes button in the top \n" +
                        "\n" +
                        "      menu bar, and select which theme you wish to use.\n\n" +
                        "Author: Daniel Fahkr");

                HBox manHbox = new HBox(
                        manual
                );

                manual.setPrefSize(700,700);
                Scene secondScene = new Scene(manHbox, 700, 500);
                secondScene.getStylesheets().add("View/light.css");
                Stage newWindow = new Stage();
                newWindow.setTitle("Manual");
                newWindow.setScene(secondScene);

                // Set position of new window
                newWindow.setX(stage.getX() + 200);
                newWindow.setY(stage.getY() + 100);

                newWindow.show();
            });

            helpMenu.getItems().add(openMan);

            // Setting title to Stage
            stage.setTitle("Calculator");
            stage.setResizable(false);
            // Adding scene to the stage
            stage.setScene(scene);
            input.requestFocus();
            // Displaying the contents of the stage
            stage.show();
        }


    public static void main(String[] args) {
    	
    	String fn1 = "test(a,b,c) = 15 * a + b + c + b^2 + cos(c)";
    	String fn2 = "fun(x,y,z) = x + y + z";
    	String fn3 = "joke(x, y, z) = x^y - z";
    	
    	System.out.println("Print original functions:");
    	System.out.println(fn1);
    	System.out.println(fn2);
    	System.out.println(fn3);
    	System.out.println();
    	
    	// seed the user function map with some bogus functions
//    	Model.UserFunctions.setFunction(fn1);
//    	Model.UserFunctions.setFunction(fn2);
//    	Model.UserFunctions.setFunction(fn3);
//    	
//    	System.out.println("Print new functions:");
//    	System.out.println(Model.UserFunctions.getFunction("test").getExpression());
//    	System.out.println(Model.UserFunctions.getFunction("fun").getExpression());
//    	System.out.println(Model.UserFunctions.getFunction("joke").getExpression());
//    	System.out.println();
    	
    	String fn4 = "yolo((14 * cos(9)), 16 * 14)";
    	
    	
    	String fn5 = "tim(x,y) = x + y";
    	            // 0123456789012345
    	String test = "tim(15, cos(28))";
    	
    	Matcher m = Model.UserFunctions.tokenizeFunctionCall(test);
    	
    	ArrayList<String> list = Model.UserFunctions.functionCallParameters(m);
    	
    	
    	
            launch(args);
    }
}
