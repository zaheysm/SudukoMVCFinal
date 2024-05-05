package com.main.sudukomvcfinal;

import com.Application.SudokoView;
import com.Model.*;
import com.main.Controller.SudokuController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStater extends Application {

    /*
     * Program entry point , calls the launch method to begin program
     * @params args
     */
    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SudokoView view=new SudokoView();
        sudukoModel model=new sudukoModel();
        SudokuController controller=new SudokuController(view,model,primaryStage);
    }
}
