package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.DAO.SupplierDAO;
import com.example.pharmacyapp.DAO.ThuocDAO;
import com.example.pharmacyapp.Model.Customer;
import com.example.pharmacyapp.Model.Supplier;
import com.example.pharmacyapp.PharmaApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SupplierController{
    @FXML
    Pane mainView;
    private Stage stage;
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setMainView(Pane mainView){
        this.mainView = mainView;
    }

    public void initialize() throws SQLException {
        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmPer.setCellValueFactory(new PropertyValueFactory<>("person"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clmNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        tblSup.getItems().clear();
        getDSSupp();
        checkPhoneNum();
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        txtPhone.addEventFilter(KeyEvent.KEY_TYPED,checkNum);

    }

    @FXML
    TableView<Supplier> tblSup;
    @FXML
    TableColumn<Supplier,Integer> clmID,clmPhone;
    @FXML
    TableColumn<Supplier,String> clmName,clmAdd,clmPer,clmNote;
    public void getDSSupp() throws SQLException {
        ResultSet rs = SupplierDAO.getDSSup();

        while(rs.next()){
            int id,phone;
            String name,address,person,note;
            id = rs.getInt(1);
            name = rs.getString(2);
            person = rs.getString(3);
            phone = rs.getInt(4);
            note = rs.getString(5);
            address = rs.getString(6);
            tblSup.getItems().add(new Supplier(id,name,address,person,phone,note));
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
    public void search() throws SQLException {
        String nameSup = "";
        String phoneSup = "";
        if (!txtName.getText().isEmpty()){
            nameSup = txtName.getText().trim();
        }
        if (!txtPhone.getText().isEmpty()){
            int p = Integer.parseInt(txtPhone.getText().trim());
            if (p != 0){
                phoneSup = String.valueOf(p);
            }
        }

        tblSup.getItems().clear();
        ResultSet rs = SupplierDAO.getSearchSup(nameSup,phoneSup);
        while(rs.next()){
            int id,phone;
            String name,address,person,note;
            id = rs.getInt(1);
            name = rs.getString(2);
            person = rs.getString(3);
            phone = rs.getInt(4);
            note = rs.getString(5);
            address = rs.getString(6);
            tblSup.getItems().add(new Supplier(id,name,address,person,phone,note));
        }
    }
    @FXML
    Button btnAddSupp,btnUpdateSupp,btnDeleteSupp;
    public void setBtnAddNewSup() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewSupplier.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 602, 468);
        AddNewSupplierController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setSupplierController(this);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
    public void setBtnDelete() throws SQLException {

        if (!tblSup.getItems().isEmpty()){
            Supplier selected = tblSup.getSelectionModel().getSelectedItem();
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");

                alert.showAndWait();
            }else {

                //DAO xoa o day
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Accept Delete!!");
                alert.setHeaderText("Delete this Customer ??");
                alert.setContentText("Click Ok Button to Delete!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    int check = SupplierDAO.deleteSupplier(selected.getId());
                    if (check > 0){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Delete Infomation!!");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Delete Success!!");
                        alert1.showAndWait();
                        tblSup.getItems().remove(selected);
                    }
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
    public void setBtnUpdate() throws IOException {

        if (!tblSup.getItems().isEmpty()){
            Supplier selected = tblSup.getSelectionModel().getSelectedItem();
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");
                alert.showAndWait();

            }else {
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("UpdateSupplier.fxml"));
                Stage stage1 = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 602, 468);
                UpdateSupplierController controller = fxmlLoader.getController();
                controller.setStage(stage1);
                controller.setSupplierController(this);
                controller.setInfomation(selected);
                stage1.setScene(scene);
                stage1.resizableProperty().setValue(false);
                stage1.initStyle(StageStyle.UNIFIED);
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.show();
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
