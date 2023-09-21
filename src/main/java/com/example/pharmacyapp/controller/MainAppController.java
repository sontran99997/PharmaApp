package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import com.example.pharmacyapp.DAO.LoginDAO;
import com.example.pharmacyapp.DAO.NhanVienDAO;
import com.example.pharmacyapp.Model.NhanVien;
import com.example.pharmacyapp.PharmaApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    @FXML
    private Pane rightPane;
    @FXML
    private Pane mainView;
    private Pane dashBoard = null;
    private Pane medicine = null;
    private Pane customer = null;
    private Pane sale = null;
    private Pane supplier = null;
    private Pane report = null;
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

    @FXML
    Label lblName;

    @FXML
    Label lblMa;

    public void setBtnDash() {
        if (mainView != dashBoard){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("DashBoard.fxml"));
                dashBoard = fxmlLoader.load();
                DashBoardController controller = fxmlLoader.getController();
                controller.setMainView(mainView);
                controller.setController(this.pharmaController);
                controller.setLblMa(lblMa.getText());
                controller.setStage(stage);
                mainView = dashBoard;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnMedicine() {

        if (mainView != medicine){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("MedicineView.fxml"));
                medicine = fxmlLoader.load();
                MedicineController controller = fxmlLoader.getController();
                controller.setMainView(mainView);
                controller.setStage(stage);
                mainView = medicine;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnCustomer() {

        if (mainView != customer){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("CustomerView.fxml"));
                customer = fxmlLoader.load();
                CustomerController controller = fxmlLoader.getController();
                controller.setMainView(mainView);
                controller.setStage(stage);
                mainView = customer;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnSale() {
        if (mainView != sale){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("SalesView.fxml"));
                sale = fxmlLoader.load();
                SalesController controller = fxmlLoader.getController();
                controller.setMainController(this);
                controller.setMainView(mainView);
                controller.setStage(stage);
                controller.setMaNV(lblMa.getText());
                mainView = sale;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnSupplier() {
        if (mainView != supplier){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("SupplierView.fxml"));
                supplier = fxmlLoader.load();
                SupplierController controller = fxmlLoader.getController();
                controller.setMainView(mainView);
                controller.setStage(stage);
                mainView = supplier;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnReport() {
        if (mainView != report){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("ReportView.fxml"));
                report = fxmlLoader.load();
                ReportController controller = fxmlLoader.getController();
                controller.setMainView(mainView);
                controller.setStage(stage);
                mainView = report;
                rightPane.getChildren().clear();
                rightPane.getChildren().add(mainView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setBtnLogOut() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit!!");
        alert.setHeaderText("Exit this App ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            this.stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            PharmaController controller = fxmlLoader.getController();
            controller.setStage(this.stage);
            stage.setScene(scene);
            stage.show();
        }


    }

    private PharmaController pharmaController;
    public void setLblMa(String text) {
        lblMa.setText(text);
    }
    public void setController(PharmaController pharmaController){
        this.pharmaController = pharmaController;
    }
    public void setLblName(String text){
        lblName.setText(text);
    }
}
