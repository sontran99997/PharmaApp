package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.Model.Bill;
import com.example.pharmacyapp.Model.Customer;
import com.example.pharmacyapp.PharmaApp;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerController{
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
        clmSTT.setCellValueFactory(new PropertyValueFactory<Customer, Customer>(""));
//        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        clmDes.setCellValueFactory(new PropertyValueFactory<>("benh"));
        clmImpDay.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("diaChi"));


        clmBillID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmBillName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmBillDay.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmBillTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblCustomer.getItems().clear();
        setSTT();
        getDSKH();
        checkPhoneNum();
    }

    @FXML
    TableView<Customer> tblCustomer;
    @FXML
    TableColumn<Customer,Integer> clmPhone;
    @FXML
    TableColumn<Customer,String> clmAddress,clmImpDay,clmDes,clmName;
    @FXML
    TableColumn<Customer, Customer> clmSTT;
    public void setSTT(){
        clmSTT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, Customer>, ObservableValue<Customer>>() {
            @Override public ObservableValue<Customer> call(TableColumn.CellDataFeatures<Customer, Customer> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        clmSTT.setCellFactory(new Callback<TableColumn<Customer, Customer>, TableCell<Customer, Customer>>() {
            @Override public TableCell<Customer, Customer> call(TableColumn<Customer, Customer> param) {
                return new TableCell<Customer, Customer>() {
                    @Override protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            int page = Integer.parseInt(lblPaging.getText());
                            setText(String.valueOf((this.getTableRow().getIndex()+1)+((page-1)*15)));
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        clmSTT.setSortable(false);
    }


    public void getDSKH() throws SQLException {
        ResultSet rs = CustomerDAO.getDSKH();

        while(rs.next()){
            int id,sdt;
            String name,benh,ngayTao,diaChi;
            id = rs.getInt(1);
            name = rs.getString(2);
            sdt = rs.getInt(3);
            benh = rs.getString(4);
            ngayTao = rs.getString(5);
            diaChi = rs.getString(6);

            tblCustomer.getItems().add(new Customer(id,name,sdt,benh,ngayTao,diaChi));

        }
    }


    @FXML
    TableView<Bill> tblBill;
    @FXML
    TableColumn<Bill,Integer> clmBillID;
    @FXML
    TableColumn<Bill,String> clmBillName,clmBillDay;
    @FXML
    TableColumn<Bill,Float> clmBillTotal;
    public void setRecordSelected() throws SQLException {

        tblBill.getItems().clear();
        Customer selected = tblCustomer.getSelectionModel().getSelectedItem();
        ResultSet rs = CustomerDAO.getBill(selected.getId());
        while (rs.next()){
            int id;
            String name, date,total;
            id = rs.getInt(1);
            name = rs.getString(2);
            date = rs.getString(3);
            total = rs.getString(4);

            tblBill.getItems().add(new Bill(id,name,date,money(Float.parseFloat(total))));
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

    //Search
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

        tblCustomer.getItems().clear();
        ResultSet rs = CustomerDAO.search(nameCus,phone);
        while(rs.next()){
            int id,sdt;
            String name,benh,ngayTao,diaChi;
            id = rs.getInt(1);
            name = rs.getString(2);
            sdt = rs.getInt(3);
            benh = rs.getString(4);
            ngayTao = rs.getString(5);
            diaChi = rs.getString(6);

            tblCustomer.getItems().add(new Customer(id,name,sdt,benh,ngayTao,diaChi));
        }
    }
    //AddNew
    @FXML
    Button btnAddCus,btnDelete;
    public void setBtnAddNewCus() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewCustomer.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 616, 441);
        AddNewCustomerController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setCustomerController(this);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
    //Delete
    public void setBtnDelete() throws SQLException {

        if (!tblCustomer.getItems().isEmpty()){
            Customer selected = tblCustomer.getSelectionModel().getSelectedItem();
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
                    int check = CustomerDAO.deleteCustomer(selected.getId());
                    if (check > 0){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Delete Infomation!!");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Delete Success!!");
                        alert1.showAndWait();
                        tblCustomer.getItems().remove(selected);
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

        if (!tblCustomer.getItems().isEmpty()){
            Customer selected = tblCustomer.getSelectionModel().getSelectedItem();
            if(selected == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");
                alert.showAndWait();

            }else {
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("UpdateCustomer.fxml"));
                Stage stage1 = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 630, 532);
                UpdateCustomerController controller = fxmlLoader.getController();
                controller.setStage(stage1);
                controller.setCustomerController(this);
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

    @FXML
    Label lblPaging;
    @FXML
    Button btnPageNext,btnPageBack;
    public int getNumPage() throws SQLException {
        ResultSet rsnumPage = CustomerDAO.getNumPage();
        int numPage = 0;
        while (rsnumPage.next()){
            numPage = rsnumPage.getInt(1);
        }
        return numPage;
    }
    public void nextPage() throws SQLException {
        int page = Integer.parseInt(lblPaging.getText());// current page
        int numPage = getNumPage();

        if (page+1 <= numPage){
            btnPageBack.setDisable(false);
            if (page+1 == numPage){
                btnPageNext.setDisable(true);
            }
            lblPaging.setText(String.valueOf(page+1));
            tblCustomer.getItems().clear();
            ResultSet rs = CustomerDAO.getPage(page+1);
            while(rs.next()){
                int id,sdt;
                String name,benh,ngayTao,diaChi;
                id = rs.getInt(1);
                name = rs.getString(2);
                sdt = rs.getInt(3);
                benh = rs.getString(4);
                ngayTao = rs.getString(5);
                diaChi = rs.getString(6);
                tblCustomer.getItems().add(new Customer(id,name,sdt,benh,ngayTao,diaChi));
            }
        }
    }
    public void backPage() throws SQLException {
        int page = Integer.parseInt(lblPaging.getText());// current page
        int numPage = getNumPage();

        if (page-1 >= 1){
            btnPageNext.setDisable(false);
            if (page-1 == 1){
                btnPageBack.setDisable(true);
            }
            lblPaging.setText(String.valueOf(page-1));
            tblCustomer.getItems().clear();
            ResultSet rs = CustomerDAO.getPage(page-1);
            while(rs.next()){
                int id,sdt;
                String name,benh,ngayTao,diaChi;
                id = rs.getInt(1);
                name = rs.getString(2);
                sdt = rs.getInt(3);
                benh = rs.getString(4);
                ngayTao = rs.getString(5);
                diaChi = rs.getString(6);
                tblCustomer.getItems().add(new Customer(id,name,sdt,benh,ngayTao,diaChi));
            }
        }
    }
}
