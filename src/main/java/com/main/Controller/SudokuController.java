package com.main.Controller;
import com.Application.*;
import com.Model.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SudokuController {
    /**
     *  will hold the number clicked on the button
     */

    private static String numToEnter = "";
    /**
     * the size of the suduko
     */
    private static int size;
    /**
     * array list to hold tghe numbers being entered by th player
     */
    static ArrayList<ArrayList<String>> addnum = new ArrayList<ArrayList<String>>();
    /**
     * array lists is being used to hold rows
     */
    static ArrayList<String> toAddnum = new ArrayList<>();
    /**
     * create instantiate of loadSaveFile class
     */
    private static LoadSaveFile loadSaveFile=new LoadSaveFile();
    /**
     * create textFiled Array
     */
    static TextField[][] textField;
    /**
     * set the main path of the source
     */
    private static String path= Paths.get(".").toAbsolutePath().normalize().toString();
    /**
     * set Timer
     */
    private static Timer timer = new Timer();
    /**
     * the seconds
     */
    private static int seconds = 0;
    /**
     * start the timer task
     */
    private static TimerTask timertask;
    /**
     * create ViewApplication
     */
    static SudokoView view;
    /**
     * create model class
     */
    static sudukoModel model;

    public SudokuController(SudokoView view,sudukoModel model,Stage stage){
        this.view=view;
        this.model=model;

        this.view.CraeteLeftPane(stage);
    }

    /*
     * Creates constructor SudokuController and accepts variable size of type int
     * Depending on the size of the accepted parameter, the SudokuController
     * Constructor will go one by one to clear the ArrayList object tmp
     * To interact with the user of the program
     * @param size
     */
    public SudokuController(int size) {
        this.size = size;

        toAddnum.clear();
        addnum.clear();
        int sqrsize=size*size;
        for (int i = 0; i < sqrsize; i++) {
            toAddnum.add(i, " ");
            ArrayList<String> tmp = new ArrayList<>();
            for (int j = 0; j < sqrsize; j++) {
                tmp.add("");

            }
            addnum.add(i, tmp);
        }

    }


    /**
     * create ComboBox
     */
    static ComboBox<String> dimCombo;


    /**
     *   will hold true or false if the player is on play mode or design
     */
    private static String designOrPlay = "design";

    /*
     * Creates ondimComboSelect of return type void which accepts param event
     * And allows for the size of the Sudoku game board to be adjusted based on
     * The switch case options available to the user
     */
    public static void ondimComboSelect(Event event) throws IOException {
        //empty string
        String s = "";
        //get the source of the event
        dimCombo = (ComboBox) event.getSource();
        //get the value of the combo
        s = dimCombo.getValue();
        //if the case is null to set up for 2x2
        if (s == null) {
            s = "2x2";
        }
        //print the current size
        System.out.println(s);
        //set the stage
        Stage stage = (Stage) dimCombo.getScene().getWindow();
        //close the tage
        stage.close();
        //get view



        //create the menu in view
    //    view.createMenu();

     //   view.CraeteLeftPane(stage);
        //add the children to the main menu
        view.Mainpane(s,stage);
    }



    /**
     * Creates method handleButtonAction that will handle the click button event and identify
     * which buttons have been clicked by user and reacts accordingly
     *
     * @param event identifies the event that being is done and handles that specific event
     * @return void
     */
    public static void handleButtonAction(ActionEvent event)  {


        //will get the button that been clicked on
        Button button = (Button) event.getSource();
        //will hold the text of the button
        String action = button.getText();
        //Condition that will check what the button is clicked
        if (action.equals("Load")) {
            System.out.println("load a file");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(path));
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(button.getScene().getWindow());
            if(file!=null) {
                addnum = loadSaveFile.loadFile(file.getPath(), String.valueOf(size), addnum);
                if (designOrPlay.equals("design")) {
                    setRows();

                }
            }

        } else if (action.equals("Save")) {
            System.out.println("save a file");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(path));
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            //set the filters
            fileChooser.getExtensionFilters().add(extFilter);
            //get the dialog for file chooser
            File file = fileChooser.showSaveDialog(button.getScene().getWindow());

            //if there is no file chooses do nothing
            if(file!=null) {
                try {
                    //save the file
                    loadSaveFile.saveFile(addnum, file.getPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }  else {
            //get the number from the button
            numToEnter = action;
        }


    }

    /**
     * method taht will create a random number
     * @param event event handle
     * @param textFields get the TextFiled
     */
    public static void handleRandomSudoko(ActionEvent event, TextField[][] textFields) {
        //two dim to the array from SudokuGenerator
        int board[][];
        //create SudokuGenerator class
        SudokuGenerator gen = new SudokuGenerator(size);
        //start generating the random array
        gen.generate();
        //test for the rows
        gen.generateRandom(1);
        //test for the columns
        gen.generateRandom(0);
        //return the array
        board = gen.getArrShuffle();

        //double check that there no error for shuffle and fill the info to the array
        for (int i = 0; i < addnum.size(); i++) {
            for (int j = 0; j < addnum.size(); j++) {
                numToEnter = Integer.toString(board[i][j]);

                addnum.get(i).set(j, numToEnter);
                textFields[i][j].setText(numToEnter);
            }
        }

    }


    /**
     * Creates method handleTextFieldAction of return type void which accepts param variable event
     * This method handles the text field once clicked on and will either fill it up or not based on user actions
     *
     * @param event handles mouse event clicked
     */
    public static void handleTextFieldAction(MouseEvent event) {
        TextField textfield = (TextField) event.getSource();
        if (textfield.getText() == null)
            return;

        String tmp = textfield.getId();
        String place[] = tmp.split("_");
        int p1 = Integer.parseInt(place[0]);
        int p2 = Integer.parseInt(place[1]);

        if (designOrPlay.equals("design")) {

            //  toAddnum.addAll(addnum.get(p1));
            // Condition to check if the number being add is already exist
            model.setDesignMode(textfield,p1,p2,numToEnter);


        } else {
            String s = addnum.get(p1).get(p2);
            System.out.println(s);
            if (textfield.getText().equals("")) {
                String point=SudokoView.getPoints().getText();
                System.out.println(s);
                int newpoint;
                if (!s.equals(numToEnter)) {
                    textfield.setText("");
                    newpoint=Integer.parseInt(point)-size;
                    if (newpoint<0){
                        SudokoView.getPoints().setText("0");
                    }else{
                        SudokoView.getPoints().setText(Integer.toString(newpoint));
                    }
                } else {
                    textfield.setText(numToEnter);
                    newpoint=Integer.parseInt(point)+size;
                    SudokoView.getPoints().setText(Integer.toString(newpoint));
                }
            }


        }
        isDone();
    }

    /**
     * Creates method checkRow of return type boolean to check if the same number is already in the row
     *
     * @param row identifies the row of the number we are checking
     * @return boolean true or false if the number has been found
     */
    private static boolean checkRow(int row) {
        for (int i = 0; i < addnum.size(); i++) {
            if (addnum.get(i).get(row).equals(numToEnter)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkBox(int row, int col) {
        int newRow = row - row % size;
        int newCol = col - col % size;
        for (int i = newRow; i < newRow + size; i++) {
            for (int j = newCol; j < newCol + size; j++) {
                if (addnum.get(j).get(i).equals(numToEnter)) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Creates method setRows of return type void in case moving from one mode to another to clear and set the mode as needed
     *
     * @return void
     */
    public static void setRows() {
        textField=SudokoView.getTextField();
        if(textField!= null){
            for (int i = 0; i < textField.length; i++) {
                for (int j = 0; j < textField.length; j++) {
                    String s = addnum.get(i).get(j);
                    System.out.println(s);

                    textField[i][j].setText(s);

                }
            }
        }

    }

    /**
     * Creates method checkColumn of return type boolean to check if the same number is already in the column
     *
     * @param column is the column of the number we are checking for to identify a duplicate
     * @return boolean returns true or false if the number is found
     */
    private static boolean checkcolumn(int column) {

        if (addnum.get(column).contains(numToEnter)) {
            return false;
        }

        return true;

    }

    /**
     * Creates method radioButtonAction of return type void which accepts param textField
     * future method to switch between play mode of the game or design mode
     *
     * @return void
     */
    public static void radioButtonAction() {
        if (designOrPlay.equals("Play")) {
            playGame();
        }
    }

    /**
     * Creates method playGame of return type void which accepts param textField
     * And is used to clear all the textFields
     *
     * @return void
     */
    public static void playGame() {
        textField = SudokoView.getTextField();
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField.length; j++) {
                System.out.println(textField[i][j].getText());
                textField[i][j].setText("");
            }
        }

    }

    /**
     * set the game difficulty
     * @param event event handle
     */
    public static void GameDifficulty(Event event) {
        //get the textfiled
        textField = SudokoView.getTextField();
        //get the source of the combobox
        ComboBox difCombo = (ComboBox) event.getSource();
        //get the value of the combo box
        String s = difCombo.getValue().toString();
        //set all rows in the textfiled to empty
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField.length; j++) {
                textField[i][j].setText("");
            }
        }
        //set the difficulty and call the function ncreateDiff
        switch (s) {
            case "Easy":
                createDiff(0.75, textField);
                break;
            case "Medium":
                createDiff(0.50, textField);
                break;
            case "Hard":
                createDiff(0.25, textField);
                break;
        }
    }

    /**
     * set the difficulty of the game
     * @param percent the percent of difficulty
     * @param textField get the TextFiled
     */
    private static void createDiff(double percent, TextField textField[][]) {
        //create tmp arraylist
        ArrayList<String> tmp = new ArrayList<>();
        //create random
        Random rand = new Random();
        //variable to get the random
        int r1;
        //loop on the array
        for (int i = 0; i < addnum.size(); i++) {
            for (int j = 0; j < addnum.size() * percent; j++) {
                //get a random number
                r1 = rand.nextInt(size * size);
                //if the number is already chosen re-choose or add to to tmp array
                if (tmp.equals(i + "" + r1)) {
                    i--;
                } else {
                    tmp.add(i + "" + r1);
                }
            }
        }
        //loop which number to open
        for (String s : tmp) {
            int num1, num2;
            num1 = Character.getNumericValue(s.charAt(0));
            num2 = Character.getNumericValue(s.charAt(1));
            textField[num1][num2].setText(addnum.get(num1).get(num2));
        }


    }

    /**
     * Creates method isDesignOrPlay of return type boolean which
     * gets the status between design and play mode and takes no param
     *
     * @return boolean true or false
     */

    public static String isDesignOrPlay(ActionEvent event) {


        RadioButton rd = (RadioButton) event.getSource();
        designOrPlay = rd.getText();
        return designOrPlay;
    }

    /**
     * create the option to select a color
     * @return array of colors
     */
    public static Color[] colorSelect() {
        //create stage to select the  color
        Stage stage = new Stage();
        //modality stage set
        stage.initModality(Modality.APPLICATION_MODAL);
        //create a final array for the colors
        final Color[] color = new Color[2];
        //create anchor pane
        AnchorPane pane = new AnchorPane();
        //create a color picker
        ColorPicker pickColor = new ColorPicker();
        //event handling to select a color
        pickColor.setOnAction(e -> {
            // Color c = pickColor.getValue();
            //add the color to array in place of one
            color[0] = pickColor.getValue();

        });
        //set layout for the color place in the pane
        pickColor.setLayoutX(10);
        pickColor.setLayoutY(50);
        //create a color picker
        ColorPicker pickColor1 = new ColorPicker();
        //event handling to select a color
        pickColor1.setOnAction(e -> {
            color[1] = pickColor1.getValue();
        });
        //set layout for the color place in the pane
        pickColor1.setLayoutX(140);
        pickColor1.setLayoutY(50);
        //add the color selector in the pane
        pane.getChildren().add(pickColor);
        pane.getChildren().add(pickColor1);
        //create the scene
        Scene scene = new Scene(pane, 250, 220);
        //set the title for change the color
        stage.setTitle("Change color");
        //add the scene to the stage
        stage.setScene(scene);
        //show the pane
        stage.showAndWait();
        //return the color
        return color;

    }

    /**
     * function that change the color in of the textfiled background
     * @param textField textfiled array
     * @param colors the colors to change to
     */
    public static void changeColor(TextField textField[][], Color colors[]) {
        //create a background color for color 1
        Background background = new Background(new BackgroundFill(colors[0],
                CornerRadii.EMPTY, Insets.EMPTY));
        //create a background color for color 2
        Background background1 = new Background(new BackgroundFill(colors[1],
                CornerRadii.EMPTY, Insets.EMPTY));
        //create tmp background for the change
        Background tmpBackGround = background;
        //create border for the textfiled
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT));
        //counter for the grid box for the suduko
        int c = 1;
        //counter for the grid box for the suduko
        int c1;
        //looping on the textfiled
        for (int i = 0; i < size * size; i++) {
            //initialise counter
            c1 = 1;
            //looping on the textfiled
            for (int j = 0; j < size * size; j++) {
                //set the temp background for the textfiled
                textField[i][j].setBackground(tmpBackGround);
                //set the border color
                textField[i][j].setBorder(border);
                //check on what box your found and change the color as needed
                if (size * size % 2 != 0 && size * size - 1 == j) {
                    continue;

                } else {
                    //if get to the size change to the other color
                    if (c1 == size) {
                        //check what color you are on and change it
                        if (tmpBackGround.equals(background)) {
                            tmpBackGround = background1;

                        } else {
                            tmpBackGround = background;
                        }
                        //set counter to 0
                        c1 = 0;
                    }
                }
                //counter increment
                c1++;


            }
            //change between the start of the box
            if (c == size) {
                if (tmpBackGround.equals(background)) {
                    tmpBackGround = background1;
                } else {
                    tmpBackGround = background;
                }
                c = 0;
            }
            //increment the counter
            c++;

        }

    }

    /**
     * start the timer to count by 1 seconds
     * @param timerText get textfiled timer
     */
    public static void startTimer(TextField timerText) {
        //get the task of the timer
        timertask = new TimerTask() {
            //run the timer
            @Override
            public void run() {
                //increment the seconds by one
                seconds++;
                //set the timerText TextField
                timerText.setText(Integer.toString(seconds));
            }
        };
        //try to get the rate
        try {
            //time by 1000 ms
            timer.scheduleAtFixedRate(timertask, 0, 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


    }

    /**
     * stop the timer and initialize it by 0
     * @param textTimer get TextField timer
     */
    public static void stoptimer(TextField textTimer){
        //seconds to 0
        seconds=0;
        //stop the timer
        timer.cancel();
        //set the texfield  to 0
        textTimer.setText("0");

    }

    /**
     * test if the user done with the game
     */
    private static void isDone(){
        //get the textFiled
        textField=SudokoView.getTextField();
        //initialize boolean as done
        boolean done=true;
        //loop on the textFiled to test if all are not empty
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField.length; j++) {
                if(textField[i][j].getText().equals("")){
                    done=false;
                }
            }
        }
        //is done
        if(done) {
            //go to winner method in the view
            SudokoView.winner();
            //stop the timer method called
            stoptimer(SudokoView.getTimer());
        }
    }

    /**
     * method to handle the menu item events
     * @param event get the event handler
     */
    public static void menuItemHandel(Event event) {
        //will get the button that been clicked on
        MenuItem item = (MenuItem) event.getSource();
        //will hold the text of the button
        String action = item.getText();
        // get the scene
        Scene scene = item.getParentPopup().getScene();
        //initialize the file choose
        FileChooser fileChooser = new FileChooser();
        //set the directory for the file chooser
        fileChooser.setInitialDirectory(new File(path));
        //filter the extension for the user
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //Condition that will check what the menu item is clicked
        if (action.equals("Load")) {
            //open the window of load
            File file = fileChooser.showOpenDialog(scene.getWindow());
            //if there is file chosen
            if(file!=null){
                //use loadSaveFile class to return the file content
                addnum = loadSaveFile.loadFile(file.getPath(), String.valueOf(size), addnum);
                //if in the design set the rows
                if (designOrPlay.equals("design")) {
                    setRows();

                }
            }

            //if save menu item selected
        } else if (action.equals("Save")) {
            //open the window of save
            File file = fileChooser.showSaveDialog(scene.getWindow());
            try {
                //save the file using loadSaveFile class
                loadSaveFile.saveFile(addnum, file.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //if about is selected
        }else if(action.equals("About")){
            //open the bout method from the view
            SudokoView.about();
        }
    }

}
