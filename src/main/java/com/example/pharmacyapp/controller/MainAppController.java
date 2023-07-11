package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.PharmaApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("DashBoard.fxml"));
            mainView = fxmlLoader.load();
//            DashBoardController controller = fxmlLoader.getController();
//            controller.setRightPane(rightPane);
            rightPane.getChildren().add(mainView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setRightPane(AnchorPane rightPane){
        this.rightPane = rightPane;
    }

    public void setBtnDash() {
        mainView.getChildren().clear();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("DashBoard.fxml"));
            mainView = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rightPane.getChildren().clear();
        rightPane.getChildren().add(mainView);
    }
    public void setBtnMedicine() {
        mainView.getChildren().clear();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("MedicineView.fxml"));
            mainView = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rightPane.getChildren().clear();
        rightPane.getChildren().add(mainView);
    }
//    public void setBtnSale() {
//        mainView.getChildren().clear();
//        try{
//            FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("SalesView.fxml"));
//            mainView = fxmlLoader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        rightPane.getChildren().clear();
//        rightPane.getChildren().add(mainView);
//    }


}
