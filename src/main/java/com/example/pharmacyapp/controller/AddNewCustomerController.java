package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewCusDAO;
import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddNewCustomerController {
    @FXML
    TextField txtName,txtPhone,txtAddress;
    @FXML
    TextArea txtNote;
    @FXML
    Button btnSave,btnClose;
    @FXML
    Label lblErrName,lblErrPhone,lblErrAddress,lblErrPhone1;
    private DashBoardController dashBoardController;
    private CustomerController customerController;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void initialize() throws SQLException{
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        txtPhone.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
        btnSave.setDisable(true);
        checkPhoneNum();
    }
    public void setDashController(DashBoardController dashBoardController1){
        this.dashBoardController = dashBoardController1;
    }
    public void setCustomerController(CustomerController customerController1){
        this.customerController = customerController1;
    }
    public void setBtnClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close!!");
        alert.setHeaderText("Exit this Form ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(dashBoardController != null){
                dashBoardController.initialize();
            }
            if(customerController != null){
                customerController.initialize();
            }
            this.stage.close();
        }

    }
    public void setEnableBtnSave(){
        String name = txtName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String phone = txtPhone.getText();
        boolean isPhoneDisable = phone.isEmpty() || phone.trim().isEmpty();
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        boolean isPassFormat = !phone.matches(passpat.pattern());
        String address = txtAddress.getText();
        boolean isAddDisable = address.isEmpty() || address.trim().isEmpty();

        lblErrName.visibleProperty().set(isNameDisable);
        lblErrPhone.visibleProperty().set(isPhoneDisable);
        lblErrAddress.visibleProperty().set(isAddDisable);
        boolean isDisable = isNameDisable || isPhoneDisable|| isAddDisable|| isPassFormat;
        btnSave.setDisable(isDisable);
    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public void checkPhoneNum(){
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        txtPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(passpat.pattern()) && !Objects.equals(newValue,"")){
                lblErrPhone1.visibleProperty().set(true);
            }else {
                lblErrPhone1.visibleProperty().set(false);
            }
        });
        txtPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (Objects.equals(newValue,"")){
                lblErrPhone.visibleProperty().set(true);
            }else {
                lblErrPhone.visibleProperty().set(false);
            }
        });
    }
    public void setBtnSave() throws SQLException {
        LocalDateTime datenow = LocalDateTime.now();

        //xu ly du lieu
        String name = txtName.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String address = txtAddress.getText();
        String date = convertDate(datenow);
        String note = "";


        if(!txtNote.getText().isEmpty()){
            note = txtNote.getText();
        }
        int succ = AddNewCusDAO.insertNewCustomer(name,address,date,phone,note);
        if(succ > 0){
            System.out.println("Success!!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insert Infomation!!");
            alert.setHeaderText(null);
            alert.setContentText("Insert Success!!");
            alert.showAndWait();
            if(dashBoardController != null){
                dashBoardController.initialize();
            }
            if(customerController != null){
                customerController.initialize();
            }
            txtName.clear();
            txtPhone.clear();
            txtAddress.clear();
            txtNote.clear();
        }
    }


}
