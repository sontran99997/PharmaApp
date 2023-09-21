package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.DAO.SaleDAO;
import com.example.pharmacyapp.Model.SearchCustomer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class SearchCustomerController {
    private Stage stage;
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    TableView<SearchCustomer> tblCus;
    @FXML
    TableColumn<SearchCustomer,Integer> clmID,clmPhone;
    @FXML
    TableColumn<SearchCustomer,String> clmAdd,clmName,clmNote;
    private SalesController salesController;
    public void setSearchController(SalesController salesController1) {
        this.salesController = salesController1;
    }
    public void initialize() throws SQLException {
        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clmAdd.setCellValueFactory(new PropertyValueFactory<>("add"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        checkPhoneNum();
        getDSKH();

    }
    public void getDSKH() throws SQLException {
        ResultSet rs = SaleDAO.getDSKH();

        while(rs.next()){
            int id,sdt;
            String name,note,diaChi;
            id = rs.getInt(1);
            name = rs.getString(2);
            sdt = rs.getInt(3);
            diaChi = rs.getString(4);
            note = rs.getString(5);

            tblCus.getItems().add(new SearchCustomer(id,name,sdt,diaChi,note));
        }
    }
    @FXML
    TextField txtName,txtPhone;
    @FXML
    Label lblErrCusPhone1;
    public void checkPhoneNum(){
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        txtPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(passpat.pattern()) && !Objects.equals(newValue,"")){
                lblErrCusPhone1.visibleProperty().set(true);
            }else {
                lblErrCusPhone1.visibleProperty().set(false);
            }
        });
        txtPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > 10){
                String temp = newValue.substring(0,10);
                txtPhone.setText(temp);
            }
        });
    }
    public void getPhoneNum(String phone) throws SQLException {
        txtPhone.setText(phone);
        search();
    }
    public void search() throws SQLException {
        String nameCus = "";
        String phone = "";
        if (!txtName.getText().isEmpty()){
            nameCus = txtName.getText().trim();
        }
        if (!txtPhone.getText().isEmpty()){
            int p = Integer.parseInt(txtPhone.getText().trim());
            if (p != 0){
                phone = String.valueOf(p);
            }
        }

        tblCus.getItems().clear();
        ResultSet rs = CustomerDAO.search(nameCus,phone);
        while(rs.next()){
            int id,sdt;
            String name,benh,diaChi;
            id = rs.getInt(1);
            name = rs.getString(2);
            sdt = rs.getInt(3);
            benh = rs.getString(4);
            diaChi = rs.getString(6);

            tblCus.getItems().add(new SearchCustomer(id,name,sdt,diaChi,benh));
        }
    }
    public void setBtnConfirm(){

        if (!tblCus.getItems().isEmpty()){
            SearchCustomer selected = tblCus.getSelectionModel().getSelectedItem();
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");

                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Accept!!");
                alert.setHeaderText(null);
                alert.setContentText("Click Ok Button to Choose!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    salesController.setInfoCustomer(selected);
                    if(!salesController.tblAddMedicine.getItems().isEmpty()){
                        salesController.btnYes.setDisable(false); // Enable BtnConfirm
                    }
                    this.stage.close();
                }

            }

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!! Table is NULL!!");
            alert.setHeaderText(null);
            alert.setContentText("Table is not included anything!!");
            alert.showAndWait();
        }
    }

}
