package sample;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Main extends Application {



        @Override
        public void start(Stage stage) {

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


            // create operation buttons
            Button button_add = new Button("+");
            Button button_sub = new Button("-");
            Button button_mul = new Button("\u008E");
            Button button_div = new Button("\u008F");
            Button button_equ = new Button("=");
            Button button_br1 = new Button("(");
            Button button_br2 = new Button(")");
            Button button_sqr = new Button("^");
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

            Button button_can = new Button("C");

            button_add.setMinSize(60,60);
            button_sub.setMinSize(60,60);
            button_mul.setMinSize(60,60);
            button_div.setMinSize(60,60);
            button_equ.setMinSize(60,60);
            button_br1.setMinSize(60,60);
            button_br2.setMinSize(60,60);
            button_sqr.setMinSize(60,60);
            button_sqt.setMinSize(60,60);
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

            button_equ.getStyleClass().add("button_equ");

            // create number grid
            GridPane numGrid = new GridPane();

            // add number buttons to grid

            //row 1
            numGrid.add(button_7, 0, 0, 1, 1);
            numGrid.add(button_8, 1, 0, 1, 1);
            numGrid.add(button_9, 2, 0, 1, 1);
            numGrid.add(button_add, 3, 0, 1, 1);
            numGrid.add(button_mul, 4, 0, 1, 1);
            numGrid.add(button_br1, 5, 0, 1, 1);
            numGrid.add(button_br2, 6, 0, 1, 1);


            // row 2

            numGrid.add(button_4, 0, 1, 1, 1);
            numGrid.add(button_5, 1, 1, 1, 1);
            numGrid.add(button_6, 2, 1, 1, 1);
            numGrid.add(button_sub, 3, 1, 1, 1);
            numGrid.add(button_div, 4, 1, 1, 1);
            numGrid.add(button_sqr, 5, 1, 1, 1);
            numGrid.add(button_sqt, 6, 1, 1, 1);

            // row 3
            numGrid.add(button_1, 0, 2, 1, 1);
            numGrid.add(button_2, 1, 2, 1, 1);
            numGrid.add(button_3, 2, 2, 1, 1);
            numGrid.add(button_etx, 3, 2, 1, 1);
            numGrid.add(button_sin, 4, 2, 1, 1);
            numGrid.add(button_cos, 5, 2, 1, 1);
            numGrid.add(button_tan, 6, 2, 1, 1);

            // row 4
            numGrid.add(button_0, 0, 3, 1, 1);
            numGrid.add(button_dec, 1, 3, 1, 1);
            numGrid.add(button_can, 2, 3, 1, 1);
            numGrid.add(button_equ, 3, 3, 1, 1);
            numGrid.add(button_csc, 4, 3, 1, 1);
            numGrid.add(button_sec, 5, 3, 1, 1);
            numGrid.add(button_cot, 6, 3, 1, 1);

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


            // Clear History button
            Button button_clear_input = new Button("Clear History");

            button_clear_input.setPrefSize(500,0);

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
                input.setText(currentInput+ button_mul.getText());
            });

            button_div.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput + "/");
            });

            button_br1.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_br1.getText());
            });

            button_br2.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_br2.getText());
            });

            button_can.setOnAction(value -> {
                input.setText("");
            });

            button_0.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_0.getText());
            });

            button_1.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_1.getText());
            });

            button_2.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_2.getText());
            });

            button_3.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_3.getText());
            });

            button_4.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_4.getText());
            });

            button_5.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_5.getText());
            });

            button_6.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_6.getText());
            });

            button_7.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_7.getText());
            });

            button_8.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_8.getText());
            });

            button_9.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_9.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_dec.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sqr.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_dec.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_dec.getText());
            });

            button_dec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_dec.getText());
            });

            button_sin.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sin.getText());
            });

            button_cos.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_cos.getText());
            });

            button_tan.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_tan.getText());
            });

            button_csc.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_csc.getText());
            });

            button_sec.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sec.getText());
            });

            button_tan.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_tan.getText());
            });

            button_cot.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_cot.getText());
            });

            button_sqr.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sqr.getText());
            });

            button_sqt.setOnAction(value -> {
                String currentInput = input.getText();
                input.setText(currentInput+ button_sqt.getText());
            });

            Parser parser = new Parser();

            ExpressionValidator validator = new ExpressionValidator();

            // equals button -> compute input function string using Dan's PARSER
            button_equ.setOnAction(value -> {
                String currentInput = input.getText();
                String currentOutput = output.getText();
                String answer;

                try {
                    answer = parser.prioritiser(validator.validateExpression(currentInput));
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
                }
                input.setStyle("-fx-text-box-border: black;");
                output.setText("\n" + currentInput + " " + button_equ.getText() +"\n" + answer + "\n" + currentOutput);
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
                        answer = parser.prioritiser(validator.validateExpression(currentInput));
                    } catch (SyntaxErrorException s){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border:#CE0000; -fx-border-width: 5px ;");
                        answer = s.getMessage();
                        console.setText(answer);
                        //input.setText("");
                        //input.setText(currentInput);
                        return;
                    } catch (MathErrorException m){
                        input.setStyle("-fx-focus-color:#CE0000;-fx-text-box-border: #CE0000; -fx-border-width: 5px ;");
                        answer = m.getMessage();
                        console.setText(answer);
                        //input.setText("");
                        //input.setText(currentInput);
                        return;
                    }
                    System.out.print(answer);

                    // BOOBS
                    if (answer.equals("8008135.0") || answer.equals("5318008.0")){
                        output.setText("( . Y . )");
                        return;
                    }

                    input.setStyle("-fx-text-box-border: black;");
                    output.setText("\n" + currentInput + " " + button_equ.getText() +"\n" + answer + "\n" + currentOutput);
                    input.setText("");
                    console.setText("");

                }
            });

            button_clear_input.setOnAction(value -> {
                output.setText("");
            });

            BorderPane rootPane = new BorderPane();
            MenuBar menu = new MenuBar();

            Menu themeMenu = new Menu("Skins");
            themeMenu.setMnemonicParsing(true);

            Menu helpMenu = new Menu("Help");
            helpMenu.setMnemonicParsing(true);


            menu.getMenus().add(themeMenu);
            menu.getMenus().add(helpMenu);
            rootPane.setTop(menu);

            ColorPicker colorPicker = new ColorPicker();

            colorPicker.setOnAction(value -> {
                Color c = colorPicker.getValue();
                System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
            });

            VBox vbox = new VBox(
                    rootPane,
                    numGrid,    // Grid of Numbers
                    input,      // Text for input
                    console
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
            Scene scene = new Scene(hbox,800, 500);

            //Set css style
            scene.getStylesheets().add("sample/dark.css");


            // Add new menu items to Skins tab
            MenuItem theme1 = new MenuItem("Light");
            theme1.setOnAction(ae -> {
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets()
                        .add("sample/light.css");
            });

            MenuItem theme2 = new MenuItem("Dark");
            theme2.setOnAction(ae -> {
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets()
                        .add("sample/dark.css");
            });

            // Custom skin
//            MenuItem theme3 = new MenuItem("New...");
//            theme2.setOnAction(ae -> {
//                Color c = colorPicker.getValue();
//                System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
//            });


            themeMenu.getItems()
                    .addAll(theme1,
                            theme2);
                           // ,theme3);



            MenuItem openMan = new MenuItem("Open Manual");
            openMan.setOnAction(ae -> {

            });

            helpMenu.getItems().add(openMan);

            //Setting title to the Stage
            stage.setTitle("Calculator");

            //Adding scene to the stage
            stage.setScene(scene);

            //Displaying the contents of the stage
            stage.show();
            //javafx.scene.text.Font.getFamilies();
        }


    public static void main(String[] args) {
            launch(args);
    }
}
