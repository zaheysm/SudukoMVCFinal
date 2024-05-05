package com.Application;
import com.main.Controller.*;
import com.Model.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Course: CST8221-302 Java Application Programming
 * @Author Zahi Masarwa & Josh Bernstein-Mason
 * Filename: SudokoView.java
 * Creates class SudokoView which is used to produce a 3x3 tiled layout for the user to play the game
 * @see SudokuController

 */

public class SudokoView {

    /**
     * create menu bar for the user to choose from
     */
    MenuBar menuBar = new MenuBar();
    /**
     * TextFiled to hold the number of the suduko
     */
    static TextField[][] textField;
    /**
     * array list to hold the buttons used
     */
    ArrayList<Button> button=new ArrayList<>();


    /**
     * size of the suduko
     */
    private static int size;
    /**
     * size of the square of the suduko
     */
    int sizeSqr;
    /**
     * place of the button on layoutx
     */
    double btnLayoutX=58.0;
    /**
     * place of the button on layouty
     */
    double btnLayoutY;
    /**
     * call the controller class
     */
    //SudokuController controller;
    /**
     * the path of the main suduko
     */
    static String path= Paths.get(".").toAbsolutePath().normalize().toString();


    public SudokoView(){

    }

    /**
     * constructor to build the view the size and the layout
     * @param size
     * @param layoutY
     */
    public SudokoView(int size,double layoutY){
        //initialize the size
        this.size=size;
        //initialize the size of box
        sizeSqr=size*size;
        //initialize the textfiled array
        textField=new TextField[sizeSqr][sizeSqr];
        //initialize the button array
        button=new ArrayList<>();
        //initialize the controller
       //controller=new SudokuController(size);
        //initialize the layout
        btnLayoutY=layoutY;
    }

    /**
     * place of the textfiled on layoutx
     */
    double layoutX=58.0;
    /**
     * place of the textfiled on layouty
     */
    double layoutY=49.0;


    /**
     * main anchor pane that hold the changeable view
     */
    static AnchorPane pane=new AnchorPane();

    /**
     * method that will the create the panel according to the size sent to the class and show the view
     * @param stage
     */
    public void CreatePanel(Stage stage){

        //loop that will start creating the textfiled
        for (int j = 0; j < sizeSqr; j++) {
            // vbox to hold text filed in horizontal
            VBox vBox=new VBox();
            //nested loop that will create the column
            for (int i = 0; i < sizeSqr; i++) {
                //the id of text filed
                String str=Integer.toString(j)+"_"+Integer.toString(i);
                //temporary TextField created to hold all the info for the text filed
                TextField tmp=new TextField();
                //set text field id
                tmp.setId(str);
                // text filed not editable
                tmp.setEditable(false);
                //create mouse click even for the text filed
                tmp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // call the controller to hold the event
                        SudokuController.handleTextFieldAction(event);
                    }
                });
                //add the temp text filed to the array
                textField[j][i]=tmp;
                //add the text filed to the vbox
                vBox.getChildren().add(textField[j][i]);
            }
            //layouts of the vbox
            vBox.setLayoutX(layoutX);
            vBox.setLayoutY(layoutY);
            //the size of vbox
            vBox.setMaxHeight(106.0);

            // if the size of the box more than 3x3 change the layout size
            if (size>3){
                vBox.setMaxWidth(27.0);
                layoutX+=27;
            }else {
                vBox.setMaxWidth(23.0);
                layoutX+=24.0;
            }


            // add the vbox to the pane that hold only textfiled and button for the game
            pane.getChildren().add(vBox);
        }


        // create tmp button
        Button tmp;

        //loop to for the array to add the buttons
        for (int i = 0; i <size*size; i++) {
            //initialize the new button
            tmp=new Button();
            //set the text of the button
            tmp.setText(Integer.toString(i+1));
            // the layout of the button
            tmp.setLayoutX(btnLayoutX);
            tmp.setLayoutY(btnLayoutY);

            //event handler for the button
            tmp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //call controller for the event
                    SudokuController.handleButtonAction(event);
                }
            });
            //add the button to the array
            button.add(tmp);
            //set the layout x for the button
            btnLayoutX+=25;
        }
        //loop to all the buttons to the pan
        for (Button btn:button) {
            pane.getChildren().add(btn);
        }


    }

    /**
     * method to return the current text filed
     * @return textfiled
     */
    public static TextField[][] getTextField() {
        return textField;
    }


    /**
     * method to return the menu
     * @return menu
     */
    public MenuBar getMenuBar() { return menuBar; }

    /**
     * create a color variable
     */
    Color[] colors;

    /**
     * function that will create the menu
     *
     */
    public void createMenu(){
        //menu for the file option
        Menu file=new Menu("File");
        //menu for the view option
        Menu view=new Menu("View");
        //menu for the help option
        Menu help=new Menu("Help");
        //menu item to load a file
        MenuItem load=new MenuItem("Load");
        //menu item to save a file
        MenuItem save=new MenuItem("Save");
        //menu item to change the color
        MenuItem color=new MenuItem("Color");
        //menu item for the about
        MenuItem about=new MenuItem("About");
        //try the images icons
        try{
            //icon for the save
            ImageView saveImage=new ImageView(path+"\\images\\Save-icon.png");
            saveImage.setFitWidth(20);
            saveImage.setFitHeight(20);
            save.setGraphic(saveImage);

            //icon for the load
            ImageView loadImage=new ImageView(path+"\\images\\open-icon.png");
            loadImage.setFitWidth(20);
            loadImage.setFitHeight(20);
            load.setGraphic(loadImage);

            //icon for the about
            ImageView aboutImage=new ImageView(path+"\\images\\about.png");
            aboutImage.setFitWidth(20);
            aboutImage.setFitHeight(20);
            about.setGraphic(aboutImage);
        }catch (Exception e){ // catch exception
            System.out.println("File not found");
        }






        //event handling for load in the menu
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SudokuController.menuItemHandel(actionEvent);
            }
        });
        //event handling for save in the menu
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SudokuController.menuItemHandel(actionEvent);
            }
        });
        //event handling for about in the menu
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SudokuController.menuItemHandel(actionEvent);
            }
        });
        //event handling for color in the menu
        color.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                colors=SudokuController.colorSelect();
                SudokuController.changeColor(textField,colors);

            }
        });
        //add menu items to the menu
        file.getItems().add(load);
        file.getItems().add(save);
        view.getItems().add(color);
        help.getItems().add(about);
        //add all menus to the menu bar
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(view);
        menuBar.getMenus().add(help);



    }

    /**
     * function that will open an error dialog
     */
    public static void showErrDialog(){
        //create a stage for the dialog
        Stage stage = new Stage();
        //initialize the stage for modality
        stage.initModality(Modality.APPLICATION_MODAL);
        //create pan
        AnchorPane pane = new AnchorPane();
        //try for the images
        try{
            //show the image for the error
            ImageView err=new ImageView(path+"\\images\\sudoku_err.png");
            err.setFitWidth(250);
            err.setFitHeight(220);
            pane.getChildren().add(err);
        }catch (Exception e){
            System.err.println("Error loading images");
        }

        //create a scene
        Scene scene = new Scene(pane, 250, 220);
        //title for the stage
        stage.setTitle("Error loading the file");
        //set the stage scene
        stage.setScene(scene);
        //show the stage
        stage.showAndWait();
    }

    /**
     * method that will open a winner dialog
     */
    public  static void winner(){
        double result=0;
        //create dialog
        Dialog<String> dialog=new Dialog<>();
        //set the title the date
        dialog.setTitle("You are done great job");
        //get the points at end the game
        String point=getPoints().getText();
        //get the time at end the game
        String timer=getTimer().getText();
        //calculate the result the user
        if(!timer.equals("0")|| !point.equals("0")){
             result=Integer.parseInt(timer)/Integer.parseInt(point);
        }
        //add the result to the dialog
        dialog.setContentText("your Result are : "+result);

        //try
        try{
            //set the image
            ImageView win=new ImageView(path+"\\images\\sudoku_winner.png");
            win.setFitWidth(100);
            win.setFitHeight(150);
            dialog.getDialogPane().setGraphic(win);
        }catch (Exception e){
            //print error
            System.err.println("Error loading images");
        }
        // create submit button
        ButtonType btnType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //add the submit button
        dialog.getDialogPane().getButtonTypes().add(btnType);


        //show the dialog
        dialog.showAndWait();
    }

    /**
     * method that will open the dialog
     */
    public static void about(){
        //carete a new dialog
        Dialog<String> dialog=new Dialog<>();
        //set the title of the dialog
        dialog.setTitle("About");

        //try
        try{
            //set an image
            ImageView about=new ImageView(path+"\\images\\sudoku_about.png");
            about.setFitWidth(250);
            about.setFitHeight(200);
            dialog.getDialogPane().setGraphic(about);
        }catch (Exception e){//catch error
            System.err.println("Error loading images");
        }
        //submit button for done
        ButtonType btnType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //add button to the dialog
        dialog.getDialogPane().getButtonTypes().add(btnType);

        //dialog.setGraphic(about);
        dialog.showAndWait();
    }


    public void loadOrSave(String action,Scene scene,String designOrPlay) {

        ArrayList<ArrayList<String>> addnum=sudukoModel.getAddnum();
        LoadSaveFile loadSaveFile = new LoadSaveFile();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(path));
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        //set the filters
        fileChooser.getExtensionFilters().add(extFilter);
        if (action.equals("Save")) {
            File file = fileChooser.showSaveDialog(scene.getWindow());

            //if there is no file chooses do nothing
            if (file != null) {
                try {
                    //save the file
                    loadSaveFile.saveFile(addnum, file.getPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else if(action.equals("Load")){
            File file = fileChooser.showOpenDialog(scene.getWindow());
            if(file!=null) {
                addnum = loadSaveFile.loadFile(file.getPath(), String.valueOf(size), addnum);
                if (designOrPlay.equals("design")) {
                    sudukoModel.setRows();

                }
            }
        }
    }

    /**
     * Creates ArrayList variable button
     */
    ArrayList<Button> buttonLeftPane=new ArrayList<Button>();


    /**
     * text filed to set the points
     */
    static TextField points=new TextField();
    /**
     * label to set the points
     */
    Label pointlbl=new Label();

    /**
     * get the anchor pane that hold unchangeable area
     * @return AnchorPane
     */
    public static AnchorPane getPane() {
        return pane;
    }

    /**
     * create AnchorPane
     */
    static AnchorPane leftPane=new AnchorPane();

    /**
     * method that get the timer TextFiled
     * @return timer TextFiled
     */
    public static TextField getTimer() {
        return timer;
    }

    /**
     * create TextField to hold the timer
     */
    static TextField timer=new TextField();

    /*
     * Creates an overrided method start of return type void which accepts parameter stage
     * This method initiates the Labels and radio buttons while creating an object of SudokuController
     * Named controller
     * Extends SudokuApplication and overrides the .jar start application
     * Accepts stage input from launch method
     * @params stage
     */
    public void CraeteLeftPane(Stage stage)  {

        //radio button for design the game
        RadioButton Design=new RadioButton();
        //radio button for paly the game
        RadioButton Play=new RadioButton();
        //label to say mode
        Label mode=new Label();
        //label to say Difficulty
        Label Difficulty=new Label();
        //label to say Dimension
        Label Dimension=new Label();
        //label to say time
        Label time=new Label();

        // States dimensions label mode
        mode.setText("Mode: ");
        mode.setLayoutX(14.0);
        mode.setLayoutY(25.0);
        //States dimensions Difficulty
        Difficulty.setText("Difficulty: ");
        Difficulty.setLayoutX(14.0);
        Difficulty.setLayoutY(57.0);
        //States Dimension
        Dimension.setText("Dimension: ");
        Dimension.setLayoutX(11.0);
        Dimension.setLayoutY(85.0);
        //put the radio button with group
        ToggleGroup groupRad=new ToggleGroup();
        //set text for radio button
        Design.setText("Design");
        Play.setText("Play");
        // Adds radio buttons to groups
        Play.setToggleGroup(groupRad);
        Design.setToggleGroup(groupRad);
        // States dimenions x and y
        Design.setLayoutX(52.0);
        Design.setLayoutY(25.0);

        Play.setLayoutX(117.0);
        Play.setLayoutY(25.0);
        //make design radio button selected
        Design.setSelected(true);



        //create a combo for difficulty
        ComboBox<String> difCombo=new ComboBox<String>();
        //set layouts
        difCombo.setLayoutX(67.0);
        difCombo.setLayoutY(57.0);
        //add the items to combo-list
        difCombo.getItems().addAll(
                "Easy",
                "Medium",
                "Hard"
        );
        //create event handling for difCombo
        difCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //event to set the difficulty
                SudokuController.GameDifficulty(actionEvent);
                //event to start the timer
                SudokuController.startTimer(timer);
            }
        });
        //create event handling for dimCombo
        ComboBox<String> dimCombo=new ComboBox<String>();
        //set the prompt the text
        dimCombo.setPromptText("2x2");
        //set layout
        dimCombo.setLayoutX(71.0);
        dimCombo.setLayoutY(85);
        // add the items for teh combo list
        dimCombo.getItems().addAll(
                "2x2",
                "3x3",
                "4x4",
                "5x5",
                "6x6"
        );

        //add event handle for dim combo
        dimCombo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                try {
                    // call to create the size of the suduko
                    SudokuController.ondimComboSelect(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //create a temporary button
        Button tmp=new Button();

        //set the button
        tmp.setText("Save");
        tmp.setLayoutX(91);
        tmp.setLayoutY(188);
        //create event handler button to save
        tmp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SudokuController.handleButtonAction(event);
            }
        });
        buttonLeftPane.add(tmp);
        //create a temporary button
        tmp=new Button();

        //set load button
        tmp.setText("Load");
        tmp.setLayoutX(41);
        tmp.setLayoutY(188);

        //create event handler button to load
        tmp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difCombo.setPromptText("");
                SudokuController.handleButtonAction(event);
            }
        });
        //add the button to arraylist
        buttonLeftPane.add(tmp);

        //set the button
        tmp=new Button();
        //set a random button
        tmp.setText("Random");
        tmp.setLayoutX(28.0);
        tmp.setLayoutY(121.0);
        tmp.setPrefHeight(50);
        tmp.setPrefWidth(127);
        //set a handle to for random button
        tmp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TextField [][] textField=SudokoView.getTextField();
                SudokuController.handleRandomSudoko(event,textField);
            }
        });
        //add button
        buttonLeftPane.add(tmp);


        //add teh button to the array
        for (Button btn:buttonLeftPane) {
            pane.getChildren().add(btn);
        }
        //set the Time label
        time.setText("Timer :");
        time.setLayoutX(12);
        time.setLayoutY(380);

        //set the timer
        timer.setText("0");
        timer.setLayoutX(52);
        timer.setLayoutY(380);
        timer.setMaxWidth(30);
        timer.setMaxHeight(30);
        //set timer textFiled as not editable
        timer.setEditable(false);

        //set label point label
        pointlbl.setText("Points :");
        pointlbl.setLayoutY(400);
        pointlbl.setLayoutX(12);
        //set Textfiled points
        points.setText("0");
        points.setLayoutX(52);
        points.setLayoutY(400);
        points.setEditable(false);
        points.setMaxHeight(30);
        points.setMaxWidth(30);
        difCombo.setDisable(true);

        //handle design radio button
        Design.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //enable the button
                button.get(2).setDisable(false);
                button.get(0).setDisable(false);
                //disable the difcombo
                difCombo.setDisable(true);
                //set the rows of the suduko from the controller
                SudokuController.setRows();
                //stop the timer
                SudokuController.stoptimer(timer);
            }
        });
        //handle play radio button action
        Play.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Creates override method handle of return type void
             * For ActionEvent event
             * @params event
             * @returns void
             */
            @Override
            public void handle(ActionEvent event) {
                //set if the game is play or design
                SudokuController.isDesignOrPlay(event);
                //radio button to set the rows and columns
                SudokuController.radioButtonAction();
                //disable save and random button
                button.get(2).setDisable(true);
                button.get(0).setDisable(true);
                //enable the difcombo
                difCombo.setDisable(false);

            }
        });
        //add all items to the pane
        leftPane.getChildren().add(Design);
        leftPane.getChildren().add(Play);
        leftPane.getChildren().add(dimCombo);
        leftPane.getChildren().add(mode);
        leftPane.getChildren().add(Difficulty);
        leftPane.getChildren().add(Dimension);
        leftPane.getChildren().add(difCombo);
        leftPane.getChildren().add(timer);
        leftPane.getChildren().add(time);
        leftPane.getChildren().add(points);
        leftPane.getChildren().add(pointlbl);
        leftPane.setMaxWidth(120);
        //set the scene
        Scene scene = new Scene(leftPane);
        //stage title
        stage.setTitle("Hello!");
        //add the scene to stage
        stage.setScene(scene);
        //show the stage
        stage.show();
        //create event handling
        ActionEvent t=new ActionEvent();
        Event e=new ActionEvent();
        //start an  event
        dimCombo.getOnAction().handle(t.copyFor(dimCombo,e.getTarget()) );
    }

    /**
     * methd that return the TextFild ppoints
     * @return textfiled points
     */
    public static TextField getPoints() {
        return points;
    }


    public void Mainpane(String dim,Stage stage){
        size=Integer.parseInt(String.valueOf(dim.charAt(0)));
        sizeSqr=size*size;
        textField=new TextField[sizeSqr][sizeSqr];
        //switch statments
        switch (dim) {
            case "2x2":
                //set the view for 2x2
                size=2;
                layoutY=336;
                CreatePanel(stage);
                stage.setWidth(400);
                stage.setHeight(500);

                pane.setLayoutX(200);
                break;
            case "3x3":
                //set the view for 3x3
                size=3;
                layoutY=336;
                CreatePanel(stage);
                stage.setWidth(470.0);
                stage.setHeight(600);
                pane.setLayoutX(290);
                break;
            case "4x4":
                //set the view for 4x4
                size=4;
                layoutY=500;
                CreatePanel(stage);
                stage.setWidth(770);
                stage.setHeight(600);
                pane.setLayoutX(580);
                break;
            case "5x5":
                //set the view for 5x5
                size=5;
                layoutY=700;
                CreatePanel(stage);
                stage.setMaximized(true);
                pane.setLayoutX(820);
                pane.setLayoutY(50);

                break;
            case "6x6":
                //set the view for 6x6
                size=6;
                layoutY=950;
                CreatePanel(stage);
                stage.setMaximized(true);
                pane.setLayoutX(1030);
                pane.setLayoutY(50);
                break;
        }
        //create the main the pane
        AnchorPane mainPane = new AnchorPane();

        //add the children to the main menu
        mainPane.getChildren().add(getPane());
        mainPane.getChildren().add(leftPane);
        mainPane.getChildren().add(getMenuBar());
        //set scene
        Scene scene;
        //if the size is 6x6 or 5x5 set it as a ScrollPane else normal
        if (dim.equals("6x6") || dim.equals("5x5")) {
            ScrollPane scPane = new ScrollPane();
            scPane.setContent(mainPane);
            scene = new Scene(scPane);
        } else {
            scene = new Scene(mainPane);
        }

        //set the scene stage
        stage.setScene(scene);
        //set the title
        stage.setTitle("Hello!");
        //set the stage
        stage.show();
    }

    public static int getSize() {
        return size;
    }




}


