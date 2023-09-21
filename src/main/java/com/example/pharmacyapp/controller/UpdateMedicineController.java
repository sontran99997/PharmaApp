package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import com.example.pharmacyapp.DAO.MedicineDAO;
import com.example.pharmacyapp.Model.Thuoc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class UpdateMedicineController {
    @FXML
    ComboBox<String> cbbType,cbbSup,cbbUnit,cbbExpDate;
    @FXML
    Button btnSave,btnClose;
    @FXML
    TextField txtMedName,txtPrice;
    @FXML
    DatePicker txtDate;
    @FXML
    Label lblID,lblErrType,lblErrUnit,lblErrDate1,lblErrSup,lblErrName,lblErrPrice,lblErrQuan,lblQuantity;
    private MedicineController medicineController;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void initialize() throws SQLException {
        setCbbType();
        setCbbUnit();
        setCbbSup();
        setCbbExpDate();
        setDate();
        btnSave.setDisable(true);
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        txtPrice.addEventFilter(KeyEvent.KEY_TYPED,checkNum);

    }
    public void setMedicineController(MedicineController medicineController1){
        this.medicineController =medicineController1;
    }
    public void setCbbType() throws SQLException {
        ResultSet rs = AddNewMedicineDAO.getType();
        ObservableList<String> options = FXCollections.observableArrayList();
        while(rs.next()){
            options.addAll(rs.getString(1));
        }
        cbbType.getItems().addAll(options);
    }
    public void setCbbSup() throws SQLException {
        ResultSet rs = AddNewMedicineDAO.getSupplier();
        ObservableList<String> options = FXCollections.observableArrayList();
        while(rs.next()){
            options.addAll(rs.getString(1));
        }
        cbbSup.getItems().addAll(options);
    }
    public void setCbbUnit() throws SQLException {
        ResultSet rs = AddNewMedicineDAO.getUnit();
        ObservableList<String> options = FXCollections.observableArrayList();
        while(rs.next()){
            options.addAll(rs.getString(1));
        }
        cbbUnit.getItems().addAll(options);
    }
    public void setDate(){
        txtDate.setValue(LocalDate.from(LocalDateTime.now()));
    }
    public void setCbbExpDate() throws SQLException {

        cbbExpDate.setItems(FXCollections.observableArrayList(
                "3 months",
                "6 months",
                "1 year",
                "2 years",
                "3 years"
        ));
    }


    public void setInfomation(Thuoc selected) {
        lblID.setText(String.valueOf(selected.getID()));
        txtMedName.setText(selected.getName());
        cbbType.setValue(selected.getType());
        cbbUnit.setValue(selected.getUnit());
        txtPrice.setText(String.valueOf(selected.getPurchase()));
        cbbSup.setValue(selected.getSupplier());
        lblQuantity.setText(String.valueOf(selected.getQuantity()));

    }
    public void setBtnClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close!!");
        alert.setHeaderText("Exit this Form ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(medicineController != null){
                medicineController.initialize();
            }

            this.stage.close();
        }

    }
    public void enableBtnSaveFromCbb(){
        int isTypeSelected = cbbType.getSelectionModel().getSelectedIndex();
        int isUnitSelected = cbbUnit.getSelectionModel().getSelectedIndex();
        int isExpDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();
        int isSupSelected = cbbSup.getSelectionModel().getSelectedIndex();

        if(isTypeSelected == -1){
            lblErrType.visibleProperty().set(true);
        }else{
            lblErrType.visibleProperty().set(false);
        }
        if(isUnitSelected == -1){
            lblErrUnit.visibleProperty().set(true);
        }else{
            lblErrUnit.visibleProperty().set(false);
        }
        if(isExpDateSelected == -1){
            lblErrDate1.visibleProperty().set(true);
        }else{
            lblErrDate1.visibleProperty().set(false);
        }
        if(isSupSelected == -1){
            lblErrSup.visibleProperty().set(true);
        }else{
            lblErrSup.visibleProperty().set(false);
        }
        String name = txtMedName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String price = txtPrice.getText();
        boolean isPriceDisable = price.isEmpty() || price.trim().isEmpty();


        boolean isDisable = isNameDisable || isPriceDisable
                || isTypeSelected == -1|| isUnitSelected == -1|| isExpDateSelected == -1|| isSupSelected == -1;
        btnSave.setDisable(isDisable);

    }
    public void setEnableBtnSave(){
        String name = txtMedName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String price = txtPrice.getText();
        boolean isPriceDisable = price.isEmpty() || price.trim().isEmpty();


        lblErrName.visibleProperty().set(isNameDisable);
        lblErrPrice.visibleProperty().set(isPriceDisable);

        int isTypeSelected = cbbType.getSelectionModel().getSelectedIndex();
        int isUnitSelected = cbbUnit.getSelectionModel().getSelectedIndex();
        int isExpDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();
        int isSupSelected = cbbSup.getSelectionModel().getSelectedIndex();

        boolean isDisable = isNameDisable || isPriceDisable
                || isTypeSelected == -1|| isUnitSelected == -1|| isExpDateSelected == -1|| isSupSelected == -1;
        btnSave.setDisable(isDisable);
    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public int getMonth(int selected){
        int plusmonth = 0;
        if (selected == 0){
            plusmonth = 3;
        }else if(selected == 1){
            plusmonth = 6;
        }else if(selected == 2){
            plusmonth = 12;
        }else if(selected == 3){
            plusmonth = 24;
        }else{
            plusmonth = 36;
        }
        return plusmonth;
    }
    public void setBtnSave() throws SQLException, ParseException {
        LocalDateTime dateIn = txtDate.getValue().atStartOfDay();

        int useDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();

        LocalDateTime dateExp = dateIn.plusMonths(getMonth(useDateSelected));

        //xu ly du lieu truoc khi nhap

        int id = Integer.parseInt(lblID.getText());
        String name = txtMedName.getText();
        int type = cbbType.getSelectionModel().getSelectedIndex()+1;
        int unit = cbbUnit.getSelectionModel().getSelectedIndex()+1;
        String dateArrive = convertDate(dateIn);
        String dateExpired = convertDate(dateExp);
//        parse(txtPrice.getText(),vietNam()).floatValue()
        float price = parse(txtPrice.getText(),vietNam()).floatValue();
        int supp = cbbSup.getSelectionModel().getSelectedIndex()+1;



        //bien check thanh cong
        int succ = MedicineDAO.updateMedicine(id,name,type,unit,dateArrive,dateExpired,price,supp);
        if(succ > 0){
            System.out.println("Success!!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Infomation!!");
            alert.setHeaderText(null);
            alert.setContentText("Update Success!!");
            alert.showAndWait();

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
}
