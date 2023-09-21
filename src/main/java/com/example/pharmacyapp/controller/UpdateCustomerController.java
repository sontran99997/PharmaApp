package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.Model.Customer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateCustomerController {
    @FXML
    Label lblID,lblErrPhone,lblErrPhone1;
    @FXML
    TextField txtName,txtPhone,txtAddress;
    @FXML
    TextArea txtNote;
    @FXML
    Button btnSave,btnClose;
    private CustomerController customerController;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void initialize() throws SQLException {
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

    public void setCustomerController(CustomerController customerController1){
        this.customerController = customerController1;
    }
    public void setInfomation(Customer cus){
        lblID.setText(String.valueOf(cus.getId()));
        txtName.setText(cus.getName());
        txtPhone.setText(String.valueOf(cus.getSdt()));
        txtAddress.setText(cus.getDiaChi());
        txtNote.setText(cus.getBenh());
    }
    public void setBtnClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close!!");
        alert.setHeaderText("Exit this Form ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

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

        lblErrPhone.visibleProperty().set(isPhoneDisable);
        boolean isDisable = isNameDisable || isPhoneDisable|| isAddDisable|| isPassFormat;
        btnSave.setDisable(isDisable);
    }

    public void setBtnSave() throws SQLException {
        int id = Integer.parseInt(lblID.getText());
        String name = txtName.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String address = txtAddress.getText();
        String note = "";
        if(!txtNote.getText().isEmpty()){
            note = txtNote.getText();
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Accept Update!!");
        alert.setHeaderText("Update this Customer ??");
        alert.setContentText("Click Ok Button to Update!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int check = CustomerDAO.updateCustomer(id,name,phone,address,note);
            if (check > 0){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Update Infomation!!");
                alert1.setHeaderText(null);
                alert1.setContentText("Update Success!!");
                alert1.showAndWait();
            }
        }
    }
}
