package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewCusDAO;
import com.example.pharmacyapp.DAO.SaleDAO;
import com.example.pharmacyapp.Model.InfoMedicineSale;
import com.example.pharmacyapp.Model.MedicineSale;
import com.example.pharmacyapp.Model.SearchCustomer;
import com.example.pharmacyapp.PharmaApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class SalesController {
    @FXML
    Pane mainView;
    @FXML
    TableView<InfoMedicineSale> tblMedicine;
    @FXML
    TableColumn<InfoMedicineSale,Integer> clmMedID,clmMedQua;
    @FXML
    TableColumn<InfoMedicineSale,String> clmMedName,clmMedPrice;
    @FXML
    ComboBox<Integer> cbbID;
    @FXML
    ComboBox<String> cbbName;
    @FXML
    Label lblQuantity;
    @FXML
    Label lblDate;
    @FXML
    TextField txtQuantity;
    @FXML
    Label lblErrQua,lblErrID,lblErrName,lblMaNV;
    @FXML
    TableView<MedicineSale> tblAddMedicine;
    @FXML
    TableColumn<MedicineSale, Integer> clmAddID,clmAddQua,clmAddTotal;
    @FXML
    TableColumn<MedicineSale,String> clmAddName;
    @FXML
    Label lblTotalPrice;
    @FXML
    Button btnSearch,btnAddNewCus, btnYes;
    @FXML
    Label lblCusID,lblErrCusPhone,lblErrCusAdd,lblErrCusName,lblErrCusPhone1;
    @FXML
    TextField txtCusName,txtCusPhone,txtCusAdd;
    @FXML
    TextArea txtCusNote;



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
    MainAppController mainAppController;
    public void setMainController(MainAppController mainAppController1) {
        this.mainAppController = mainAppController1;
    }

    public void initialize() throws SQLException, ParseException {
        clmMedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmMedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmMedQua.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmMedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        clmAddID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmAddName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddQua.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmAddTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        txtQuantity.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
        txtCusPhone.addEventFilter(KeyEvent.KEY_TYPED,checkNum);

        setCbbID();
        setCbbName();
        setLblDate();
        getMedInfo();
        setLblTotalPrice();
        checkPhoneNum();
        btnAddNewCus.setDisable(true);
        btnYes.setDisable(true);
    }


    public void getMedInfo() throws SQLException {
        ResultSet rs = SaleDAO.getDSThuoc();
        while(rs.next()){
            int id,quantity;
            String name,price;
            id = rs.getInt(1);
            name = rs.getString(2);
            quantity = rs.getInt(3);
            price = rs.getString(4);

            tblMedicine.getItems().add(new InfoMedicineSale(id,name,quantity,money(Float.parseFloat(price))));
        }
    }

    public void setCbbID() throws SQLException {
        ResultSet rs = SaleDAO.getDSThuoc();
        ObservableList<Integer> options = FXCollections.observableArrayList();
        while (rs.next()){
            options.add(rs.getInt(1));
        }
        cbbID.getItems().addAll(options);
    }
    public void setCbbName() throws SQLException {
        ResultSet rs = SaleDAO.getDSThuoc();
        ObservableList<String> options = FXCollections.observableArrayList();
        while (rs.next()){
            options.add(rs.getString(2));
        }
        cbbName.getItems().addAll(options);
    }
    public void setSelectedMedicine(){
        InfoMedicineSale selected = tblMedicine.getSelectionModel().getSelectedItem();
        cbbID.setValue(selected.getId());
        cbbName.setValue(selected.getName());
        lblQuantity.setText(String.valueOf(selected.getQuantity()));
    }
    public void setSelectedFromCBBID() throws SQLException {
        ResultSet rs = SaleDAO.getDSThuoc();
        int id = cbbID.getValue();
        while (rs.next()){
            if(Objects.equals(rs.getInt(1),id)){
                cbbName.setValue(rs.getString(2));
                lblQuantity.setText(String.valueOf(rs.getInt(3)));
            }

        }
    }
    public void setSelectedFromCBBName() throws SQLException {
        ResultSet rs = SaleDAO.getDSThuoc();
        String name = cbbName.getValue();
        while (rs.next()){
            if (Objects.equals(rs.getString(2),name)){
                cbbID.setValue(rs.getInt(1));
                lblQuantity.setText(String.valueOf(rs.getInt(3)));
            }
        }
    }

    public void setLblDate(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String datetext = date.format(formatter);
        lblDate.setText(datetext);
    }

    public void btnAdd() throws SQLException, ParseException {
        //check value from label and textfield
        if(Objects.equals(lblQuantity.getText(),"") || Objects.equals(txtQuantity.getText(),"") || cbbID.getValue() == null || cbbName.getValue() == null
                || Integer.parseInt(lblQuantity.getText()) < Integer.parseInt(txtQuantity.getText())){
            if (cbbID.getValue() == null){
                lblErrID.visibleProperty().set(true);
            }else {
                lblErrID.visibleProperty().set(false);
            }
            if (cbbName.getValue() == null){
                lblErrName.visibleProperty().set(true);
            }else {
                lblErrName.visibleProperty().set(false);
            }
            if(Objects.equals(lblQuantity.getText(),"") ||Objects.equals(txtQuantity.getText(),"")
                    || Integer.parseInt(lblQuantity.getText()) < Integer.parseInt(txtQuantity.getText())
                    || Integer.parseInt(txtQuantity.getText()) <=0){
                lblErrQua.visibleProperty().set(true);
            }
        }else {
            lblErrID.visibleProperty().set(false);
            lblErrName.visibleProperty().set(false);
            lblErrQua.visibleProperty().set(false);
            int id = cbbID.getValue();
            String name = cbbName.getValue();
            int quantity = Integer.parseInt(txtQuantity.getText());

            ResultSet rs = SaleDAO.getToThuocView(id);
            while (rs.next()){
                //check value and add value from form
                if (tblAddMedicine.getItems().isEmpty()){
                    tblAddMedicine.getItems().add(new MedicineSale(id,name,quantity,money(2*quantity*rs.getInt(2))));
                    setLblTotalPrice();
                    if(!lblCusID.getText().isEmpty()){// Enable BtnConfirm
                        btnYes.setDisable(false);
                    }
                    return;
                }
                else {
                    for (int idx = 0; idx < tblAddMedicine.getItems().size(); idx++) {
                        MedicineSale data = tblAddMedicine.getItems().get(idx);
                        if (data.getId() == id) {
                            int oldQuantity = data.getQuantity();
                            if ((oldQuantity + quantity) <= Integer.parseInt(lblQuantity.getText())) {
                                data.setQuantity(oldQuantity + quantity);

                            } else {
                                data.setQuantity(Integer.parseInt(lblQuantity.getText()));
                            }
                            data.setTotal(money(2 * data.getQuantity() * rs.getInt(2)));
                            tblAddMedicine.getItems().set(idx, data);
                            setLblTotalPrice();
                            return;
                        }
                    }
                    int i = 0;
                    for (int idx = 0; idx < tblAddMedicine.getItems().size(); idx++) {
                        MedicineSale data = tblAddMedicine.getItems().get(idx);
                        if (data.getId() == id){
                            i=1;
                        }}

                    if (i == 0) {
                        tblAddMedicine.getItems().add(new MedicineSale(id, name, quantity, money(2 * quantity * rs.getInt(2))));
                        setLblTotalPrice();
                    }
                }
                return;
            }
        }
    }
    public void setBtnDelete() throws ParseException {
        if (!tblAddMedicine.getItems().isEmpty()){
            MedicineSale selectedItem = tblAddMedicine.getSelectionModel().getSelectedItem();
            if (selectedItem == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! Could not found!!");
                alert.setHeaderText(null);
                alert.setContentText("Please! Choose an Item!!");

                alert.showAndWait();
            }else {
                tblAddMedicine.getItems().remove(selectedItem);
                setLblTotalPrice();
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!! Table is NULL!!");
            alert.setHeaderText(null);
            alert.setContentText("Table is not included anything!!");
            alert.showAndWait();
        }
    }

    public void setLblTotalPrice() throws ParseException {
        float totalPice = 0;
        if (!tblAddMedicine.getItems().isEmpty()){
            for (int idx = 0; idx < tblAddMedicine.getItems().size(); idx++){
                MedicineSale data = tblAddMedicine.getItems().get(idx);
                totalPice = totalPice + parse(data.getTotal(),vietNam()).floatValue();
            }
            lblTotalPrice.setText(money(totalPice));
        }
    }

    public void searchCus() throws IOException, SQLException {
        String phone = "";
        if (!txtCusPhone.getText().isEmpty()){
            phone = txtCusPhone.getText();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("SearchCustomer.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 716, 536);
        SearchCustomerController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setSearchController(this);
        controller.getPhoneNum(phone);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void setInfoCustomer(SearchCustomer customer){
        lblCusID.setText(String.valueOf(customer.getId()));
        txtCusName.setText(customer.getName());
        txtCusPhone.setText(String.valueOf(customer.getPhone()));
        txtCusAdd.setText(customer.getAdd());
        txtCusNote.setText(customer.getNote());
    }

    public void checkPhoneNum(){
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        txtCusPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(passpat.pattern()) && !Objects.equals(newValue,"")){
                lblErrCusPhone1.visibleProperty().set(true);
            }else {
                lblErrCusPhone1.visibleProperty().set(false);
            }
        });
        txtCusPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (Objects.equals(newValue,"")){
                lblErrCusPhone.visibleProperty().set(true);
            }else {
                lblErrCusPhone.visibleProperty().set(false);
            }
        });
        txtCusPhone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > 10){
                String temp = newValue.substring(0,10);
                txtCusPhone.setText(temp);
            }
        });
    }
    public void setEnableBtnAddCus(){
        String name = txtCusName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String phone = txtCusPhone.getText();
        boolean isPhoneDisable = phone.isEmpty() || phone.trim().isEmpty();
        Pattern passpat = Pattern.compile("0*[0-9]{9}");
        boolean isPassFormat = !phone.matches(passpat.pattern());
        String address = txtCusAdd.getText();
        boolean isAddDisable = address.isEmpty() || address.trim().isEmpty();

        lblErrCusName.visibleProperty().set(isNameDisable);
        lblErrCusPhone.visibleProperty().set(isPhoneDisable);

        lblErrCusAdd.visibleProperty().set(isAddDisable);
        boolean isDisable = isNameDisable || isPhoneDisable|| isAddDisable|| isPassFormat;
        btnAddNewCus.setDisable(isDisable);
    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public boolean checkCus(int phone) throws SQLException {
        ResultSet rs = SaleDAO.checkCustomer(phone);
        while (rs.next()){
            int sdt = rs.getInt(3);
            if (Objects.equals(sdt,phone)){
                return true;
            }
        }
        return false;
    }
    public void addNewCus() throws SQLException {
        String name = txtCusName.getText();
        String phone = txtCusPhone.getText();
        String address = txtCusAdd.getText();
        String date = convertDate(LocalDateTime.now());
        String note = "";
        if(!txtCusNote.getText().isEmpty()){
            note = txtCusNote.getText();
        }

        if (checkCus(Integer.parseInt(phone))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!! Phone Number was Exist!!");
            alert.setHeaderText(null);
            alert.setContentText("Please go to Search Button to get this Infomation!!");
            alert.showAndWait();
        }else {
            int checkInsert = AddNewCusDAO.insertNewCustomer(name,address,date, Integer.parseInt(phone),note);
            if (checkInsert > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!!!!");
                alert.setHeaderText(null);
                alert.setContentText("Create new Customer!!");
                alert.showAndWait();
                ResultSet rs = SaleDAO.checkCustomer(Integer.parseInt(phone));
                while (rs.next()){
                    lblCusID.setText(String.valueOf(rs.getInt(1)));
                }
            }
        }

    }
    public BigDecimal parse(String amount, Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
    }
    public Locale vietNam(){
        Locale local = new Locale("vi","VN");
        return local;
    }
    public String money(float money){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(vietNam());
        return formatter.format(money);
    }

    public void setBtnConfirm() throws IOException, ParseException {

        String name = txtCusName.getText();
        String add = txtCusAdd.getText();
        int phone = Integer.parseInt(txtCusPhone.getText());
        int id = 0;
        if (lblCusID.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Please, check if the Customer is Registered!!");
            alert.showAndWait();
            return;
        }else{
            id = Integer.parseInt(lblCusID.getText());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("Bill.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 648, 700);
        BillController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setSalesController(this);
        controller.setMaNV(lblMaNV.getText());
        controller.getDataMed(tblAddMedicine);
        controller.setInfoCustomer(id,name,add,phone);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void setMaNV(String text) {
        lblMaNV.setText(text);
    }

    ///Add
    @FXML
    CheckBox chkNo;
    public void checkBoxNoInfo() throws SQLException {
        ResultSet rs = SaleDAO.getNoInfo();
        if(chkNo.isSelected()){
            btnSearch.setDisable(true);
            btnAddNewCus.setDisable(true);
            txtCusName.clear();
            txtCusName.editableProperty().set(false);
            txtCusPhone.clear();
            txtCusPhone.editableProperty().set(false);
            txtCusAdd.clear();
            txtCusAdd.editableProperty().set(false);
            txtCusNote.clear();
            txtCusNote.editableProperty().set(false);
            lblErrCusPhone.visibleProperty().set(false);
            while (rs.next()){
                lblCusID.setText(String.valueOf(rs.getInt(1)));
                txtCusName.setText(rs.getString(2));
                txtCusPhone.setText(rs.getString(3));
                txtCusAdd.setText(rs.getString(4));
            }
            lblErrCusPhone1.visibleProperty().set(false);
        }else{
            btnSearch.setDisable(false);
            txtCusName.clear();
            txtCusPhone.clear();
            txtCusAdd.clear();
            txtCusNote.clear();
            txtCusName.editableProperty().set(true);
            txtCusPhone.editableProperty().set(true);
            txtCusAdd.editableProperty().set(true);
            txtCusNote.editableProperty().set(true);
            lblErrCusPhone.visibleProperty().set(false);
            lblErrCusPhone1.visibleProperty().set(false);
        }

    }

}
