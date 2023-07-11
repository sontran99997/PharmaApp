package com.example.pharmacyapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    Pane rightPane;
    @FXML
    Button btnDash,btnMedicine,btnSale;
    @FXML
    Pane mainView;
    private Stage stage;
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setRightPane(AnchorPane rightPane){
        this.rightPane = rightPane;
    }
}
