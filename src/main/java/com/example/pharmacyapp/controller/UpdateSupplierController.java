package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewSupplierDAO;
import com.example.pharmacyapp.DAO.SupplierDAO;
import com.example.pharmacyapp.Model.Supplier;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateSupplierController {
    @FXML
    TextField txtName,txtPerson,txtPhone,txtAddress,txtNote;
    @FXML
    Button btnSave,btnClose;
    @FXML
    Label lblErrPhone1,lblErrPhone,lblErrName,lblErrPer,lblErrAdd;
    private SupplierController supplierController;
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
    public void setEnableBtnSave(){
        String name = txtName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String phone = txtPhone.getText();
        boolean isPhoneDisable = phone.isEmpty() || phone.trim().isEmpty();
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        boolean isPassFormat = !phone.matches(passpat.pattern());
        String address = txtAddress.getText();
        boolean isAddDisable = address.isEmpty() || address.trim().isEmpty();
        String person = txtPerson.getText();
        boolean isPerDisable = person.isEmpty() || person.trim().isEmpty();

        lblErrName.visibleProperty().set(isNameDisable);
        lblErrPhone.visibleProperty().set(isPhoneDisable);
        lblErrAdd.visibleProperty().set(isAddDisable);
        lblErrPer.visibleProperty().set(isPerDisable);

        boolean isDisable = isNameDisable || isPhoneDisable|| isAddDisable|| isPassFormat|| isPerDisable;
        btnSave.setDisable(isDisable);
    }
    public void setBtnClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close!!");
        alert.setHeaderText("Exit this Form ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (supplierController != null){
                supplierController.initialize();
            }

            this.stage.close();
        }

    }

    public void setSupplierController(SupplierController supplierController1) {
        this.supplierController = supplierController1;
    }
    public void setBtnSave() throws SQLException {
        int id = Integer.parseInt(lblID.getText());
        String name = txtName.getText();
        String person = txtPerson.getText();
        int phone = Integer.parseInt(txtPhone.getText());
        String address = txtAddress.getText();

        String note = "";
        if(!txtNote.getText().isEmpty()){
            note = txtNote.getText();
        }
        int succ = SupplierDAO.updateCustomer(id,name,person,phone,address,note);
        if(succ > 0){
            System.out.println("Success!!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insert Infomation!!");
            alert.setHeaderText(null);
            alert.setContentText("Insert Success!!");
            alert.showAndWait();
            supplierController.initialize();
        }
    }
    @FXML
    Label lblID;
    public void setInfomation(Supplier selected) {
        lblID.setText(String.valueOf(selected.getId()));
        txtName.setText(selected.getName());
        txtPerson.setText(selected.getPerson());
        txtPhone.setText(String.valueOf(selected.getPhone()));
        txtAddress.setText(selected.getAddress());
        txtNote.setText(selected.getNote());
    }
}
