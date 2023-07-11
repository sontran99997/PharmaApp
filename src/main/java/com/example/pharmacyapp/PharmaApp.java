package com.example.pharmacyapp;

import com.example.pharmacyapp.controller.PharmaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PharmaApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);



        PharmaController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

//        primaryStage.setTitle("Tran Ngoc Son");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
