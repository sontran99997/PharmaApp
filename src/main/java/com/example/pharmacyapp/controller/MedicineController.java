package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.DAO.MedicineDAO;
import com.example.pharmacyapp.DAO.ThuocDAO;
import com.example.pharmacyapp.Model.Customer;
import com.example.pharmacyapp.Model.Thuoc;
import com.example.pharmacyapp.PharmaApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MedicineController {
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
        clmID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        clmQua.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmSup.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        clmImportDay.setCellValueFactory(new PropertyValueFactory<>("importDay"));
        clmExpiredDay.setCellValueFactory(new PropertyValueFactory<>("expiredDay"));
        clmPurchase.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        clmSell.setCellValueFactory(new PropertyValueFactory<>("sell"));
        tblThuoc.getItems().clear();
        setCbbType();
        getDSThuoc();
    }

    @FXML
    TableView<Thuoc> tblThuoc;
    @FXML
    TableColumn<Thuoc,String> clmName,clmType,clmQua,clmSup,clmImportDay,clmExpiredDay;
    @FXML
    TableColumn<Thuoc,Integer> clmID,clmUnit;
    @FXML
    TableColumn<Thuoc,Float> clmPurchase,clmSell;
    @FXML
    ComboBox<String> cbbType;
    public void setCbbType() throws SQLException {
        ResultSet rs = AddNewMedicineDAO.getType();
        ObservableList<String> options = FXCollections.observableArrayList();
        while(rs.next()){
            options.addAll(rs.getString(1));
        }
        cbbType.getItems().addAll(options);
    }

    public void getDSThuoc() throws SQLException {


        ResultSet rs = MedicineDAO.getDSThuoc();
        while (rs.next()){
            String name,type,unit,sup,importDay,expiredDay,purchase,sell;
            int id,qua;
            id = rs.getInt(1);
            name = rs.getString(2);
            type = rs.getString(3);
            unit = rs.getString(4);
            qua = rs.getInt(5);
            sup = rs.getString(6);
            importDay = rs.getString(7);
            expiredDay = rs.getString(8);
            purchase = rs.getString(9);
            sell = String.valueOf(Float.parseFloat(purchase)*2);
            tblThuoc.getItems().add(new Thuoc(id,name,type,unit,qua,sup,importDay,expiredDay,money(Float.parseFloat(purchase)),money(Float.parseFloat(sell))));

        }
    }

    public Locale vietNam(){
        Locale local = new Locale("vi","VN");
        return local;
    }
    public String money(float money){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(vietNam());
        return formatter.format(money);
    }
    @FXML
    TextField txtName,txtSupp;
    public void search() throws SQLException {
        String nameMed = "";
        String supp = "";
        String typeMed = "";
        if (!txtName.getText().isEmpty()){
            nameMed = txtName.getText().trim();
        }
        if (!txtSupp.getText().isEmpty()){
            supp = txtSupp.getText().trim();
        }
        if(cbbType.getSelectionModel().getSelectedIndex() != -1){
            typeMed = cbbType.getSelectionModel().getSelectedItem();
        }

        tblThuoc.getItems().clear();
        ResultSet rs = MedicineDAO.getSearchThuoc(nameMed,supp,typeMed);
        while (rs.next()){
            String name,type,unit,sup,importDay,expiredDay,purchase,sell;
            int id,qua;
            id = rs.getInt(1);
            name = rs.getString(2);
            type = rs.getString(3);
            unit = rs.getString(4);
            qua = rs.getInt(5);
            sup = rs.getString(6);
            importDay = rs.getString(7);
            expiredDay = rs.getString(8);
            purchase = rs.getString(9);
            sell = String.valueOf(Float.parseFloat(purchase)*2);
            tblThuoc.getItems().add(new Thuoc(id,name,type,unit,qua,sup,importDay,expiredDay,money(Float.parseFloat(purchase)),money(Float.parseFloat(sell))));

        }
    }

    @FXML
    Button btnAdd,btnUpdate,btnDel;
    public void setBtnAddNewMed() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewMedicine.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 598, 700);
        AddNewMedicineController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setMedicineContoller(this);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void setBtnDelete() throws SQLException {

        if (!tblThuoc.getItems().isEmpty()){
            Thuoc selected = tblThuoc.getSelectionModel().getSelectedItem();
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
                    int check = MedicineDAO.deleteMedicine(selected.getID());
                    if (check > 0){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Delete Infomation!!");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Delete Success!!");
                        alert1.showAndWait();
                        tblThuoc.getItems().remove(selected);
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

        if (!tblThuoc.getItems().isEmpty()){
            Thuoc selected = tblThuoc.getSelectionModel().getSelectedItem();
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");
                alert.showAndWait();

            }else {
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("UpdateMedicine.fxml"));
                Stage stage1 = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 598, 700);
                UpdateMedicineController controller = fxmlLoader.getController();
                controller.setStage(stage1);
                controller.setMedicineController(this);
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
