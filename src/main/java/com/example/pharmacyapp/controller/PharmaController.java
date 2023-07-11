package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.PharmaApp;
import com.example.pharmacyapp.controller.MainAppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PharmaController {


    public void initialize(URL url, ResourceBundle rb){
    }
    @FXML
    Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    @FXML
    Button btnLogin;

    public void setBtnLogin() {
        if (true) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("MainApp.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainAppController controller = fxmlLoader.getController();
                controller.setStage(stage);
                stage.setScene(scene);
                stage.centerOnScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
