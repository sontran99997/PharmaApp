package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import com.example.pharmacyapp.DAO.NhanVienDAO;
import com.example.pharmacyapp.Model.CBBTime;
import com.example.pharmacyapp.Model.NhanVien;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class AddNewMedicineController {
    @FXML
    ComboBox<String> cbbType,cbbSup,cbbExpDate,cbbUnit;

    @FXML
    DatePicker txtDate;
    @FXML
    Label lblTenNV;
    @FXML
    TextField txtMedName,txtPrice,txtQuantity, txtID;
    @FXML
    Label lblMa;
    private DashBoardController dashBoardController;
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
        setLblMaPN();
        setDate();
        btnSearch.setDisable(true);
        btnSave.setDisable(true);
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        txtID.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
        txtPrice.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
        txtQuantity.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
    }
    @FXML
    Label lblMaPN;
    private void setLblMaPN() throws SQLException {
        lblMaPN.setText(String.valueOf(getMaPhieuNhap()));
    }
    public void setController(DashBoardController dashBoardController1){
        this.dashBoardController = dashBoardController1;
    }


    public void setLblTenNV(String maNV) throws SQLException {
        ResultSet rs = AddNewMedicineDAO.getMa(maNV);
        while (rs.next()){
            lblMa.setText(rs.getString(1));
            lblTenNV.setText(rs.getString(2));
        }

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
    public int getMaPhieuNhap() throws SQLException {
        int ca = 0;
        LocalDateTime datenow = LocalDateTime.now();
        LocalDateTime checkdate = LocalDateTime.of(LocalDate.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),14,00);
        if(datenow.isBefore(checkdate)){
            ca = 1;
        }else {
            ca =2;
        }
        String datetext = convertDate(datenow);


        String ma = lblMa.getText();

        int checkInsert = AddNewMedicineDAO.createMaPhieuNhap(ma,datetext,ca);
        if(checkInsert > 0){
            System.out.println("Tao Thanh Cong");
        }
        ResultSet rs = AddNewMedicineDAO.getMaPhieuNhap();
        int maPhieuNhap = 0;
        while (rs.next()){
            maPhieuNhap = rs.getInt(1);
        }
        return maPhieuNhap;
    }
    public boolean checkID(int id) throws SQLException {
        ResultSet rs = AddNewMedicineDAO.checkID(id);
        if (!rs.next()){
            return false;
        }else {
            return true;
        }
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
        }else if(selected == 1){
            plusmonth = 12;
        }else if(selected == 1){
            plusmonth = 24;
        }else{
            plusmonth = 36;
        }
        return plusmonth;
    }
    public void setBtnSave() throws SQLException {
        LocalDateTime dateIn = txtDate.getValue().atStartOfDay();

        int useDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();

        LocalDateTime dateExp = dateIn.plusMonths(getMonth(useDateSelected));

        //xu ly du lieu truoc khi nhap
        int maPN = Integer.parseInt(lblMaPN.getText());

        String name = txtMedName.getText();
        int type = cbbType.getSelectionModel().getSelectedIndex()+1;
        int unit = cbbUnit.getSelectionModel().getSelectedIndex()+1;
        String dateArrive = convertDate(dateIn);
        String dateExpired = convertDate(dateExp);
        float price = Float.parseFloat(txtPrice.getText());
        int supp = cbbSup.getSelectionModel().getSelectedIndex()+1;
        int quantity = Integer.parseInt(txtQuantity.getText());


        //bien check thanh cong
        int succ = 0;

        if(!txtID.getText().isEmpty()){//insert have an id
            int id = Integer.parseInt(txtID.getText());
            succ = AddNewMedicineDAO.insertHaveID(maPN,id,quantity,price);
            if(succ > 0){
                System.out.println("Success!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Insert Infomation!!");
                alert.setHeaderText(null);
                alert.setContentText("Insert Success!!");
                alert.showAndWait();
            }

        }else{//insert don't have an id
            succ = AddNewMedicineDAO.insertNonID(type,supp,unit,quantity,dateArrive,dateExpired,name,maPN,price);
            if(succ > 0){
                System.out.println("Success!!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Insert Infomation!!");
                alert.setHeaderText(null);
                alert.setContentText("Insert Success!!");
                alert.showAndWait();
            }
        }
        txtID.clear();
        txtMedName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        cbbType.setValue("Choose a Value");
        cbbUnit.setValue("Choose a Value");
        cbbExpDate.setValue("Choose a Value");
        cbbSup.setValue("Choose a Value");
        lblErrName.visibleProperty().set(false);
        lblErrType.visibleProperty().set(false);
        lblErrUnit.visibleProperty().set(false);
        lblErrDate.visibleProperty().set(false);
        lblErrDate1.visibleProperty().set(false);
        lblErrPrice.visibleProperty().set(false);
        lblErrSup.visibleProperty().set(false);
        lblErrQuan.visibleProperty().set(false);
        setDate();
    }
    @FXML
    Label lblErrID,lblErrName,lblErrType,lblErrUnit,lblErrDate,lblErrDate1,lblErrPrice,lblErrSup,lblErrQuan;
    @FXML
    Button btnSearch,btnSave;
    public void setEnableBtnSearch(){
        String id = txtID.getText();
        boolean isDisable = id.isEmpty() || id.trim().isEmpty();
        btnSearch.setDisable(isDisable);
    }
    public void setEnableBtnSave(){
        String name = txtMedName.getText();
        boolean isNameDisable = name.isEmpty() || name.trim().isEmpty();
        String price = txtPrice.getText();
        boolean isPriceDisable = price.isEmpty() || price.trim().isEmpty();
        String quantity = txtQuantity.getText();
        boolean isQuaDisable = quantity.isEmpty() || quantity.trim().isEmpty();

        lblErrName.visibleProperty().set(isNameDisable);
        lblErrPrice.visibleProperty().set(isPriceDisable);
        lblErrQuan.visibleProperty().set(isQuaDisable);

        int isTypeSelected = cbbType.getSelectionModel().getSelectedIndex();
        int isUnitSelected = cbbUnit.getSelectionModel().getSelectedIndex();
        int isExpDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();
        int isSupSelected = cbbSup.getSelectionModel().getSelectedIndex();

        boolean isDisable = isNameDisable || isPriceDisable|| isQuaDisable
                || isTypeSelected == -1|| isUnitSelected == -1|| isExpDateSelected == -1|| isSupSelected == -1;
        btnSave.setDisable(isDisable);
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
        String quantity = txtQuantity.getText();
        boolean isQuaDisable = quantity.isEmpty() || quantity.trim().isEmpty();
        boolean isDisable = isNameDisable || isPriceDisable|| isQuaDisable
                || isTypeSelected == -1|| isUnitSelected == -1|| isExpDateSelected == -1|| isSupSelected == -1;
        btnSave.setDisable(isDisable);

    }

    public void setBtnSearch() throws SQLException {
        if (!Objects.equals(txtID.getText(),"")){
            int id = Integer.parseInt(txtID.getText());

            ResultSet rs = AddNewMedicineDAO.getMedInfo(id);
            if (!rs.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!! ID Not Found!!");
                alert.setHeaderText(null);
                alert.setContentText("ID is Not Exist!");
                alert.showAndWait();
                txtID.setText("");
            }
            else {
                txtMedName.setText(rs.getString(1));
                cbbType.setValue(rs.getString(2));
                cbbUnit.setValue(rs.getString(3));
                cbbSup.setValue(rs.getString(4));
                txtPrice.setText(String.valueOf(rs.getFloat(5)));
                int isExpDateSelected = cbbExpDate.getSelectionModel().getSelectedIndex();
                String quantity = txtQuantity.getText();
                boolean isQuaDisable = quantity.isEmpty() || quantity.trim().isEmpty();
                if (isExpDateSelected == -1)
                {
                    lblErrDate1.visibleProperty().set(true);
                }
                lblErrQuan.visibleProperty().set(isQuaDisable);
            }
        }else {
            lblErrID.visibleProperty().set(true);
        }

    }
    public void setBtnClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close!!");
        alert.setHeaderText("Exit this Form ??");
        alert.setContentText("Click Ok Button to Exit!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (dashBoardController != null){
                dashBoardController.initialize();
            }
            if(medicineController != null){
                medicineController.initialize();
            }

            this.stage.close();
        }

    }
    private MedicineController medicineController;
    public void setMedicineContoller(MedicineController medicineController1) {
        this.medicineController = medicineController1;
    }
}
