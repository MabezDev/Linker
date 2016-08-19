package com.mabezdev;

import com.mabezdev.Controllers.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private RootController rootController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "FXML/RootController.fxml"
                )
        );
        loader.load();
        rootController = loader.getController();
        Parent root = loader.getRoot();
        primaryStage.setTitle("Linker");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        //stop controller
        rootController.stop();
        System.exit(0);
    }
}
