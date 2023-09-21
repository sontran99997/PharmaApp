package com.example.pharmacyapp;

import com.example.pharmacyapp.controller.DashBoardController;
import com.example.pharmacyapp.controller.PharmaController;
import com.example.pharmacyapp.controller.SalesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource("AddNewCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1340, 785);

        DashBoardController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}