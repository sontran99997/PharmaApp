package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.AddNewMedicineDAO;
import com.example.pharmacyapp.DAO.SaleDAO;
import com.example.pharmacyapp.Model.MedicineSale;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class BillController {
    @FXML
    Label lblCusName,lblCusPhone,lblCusAdd,lblTotalPrice,lblErrCash,lblDate,lblMaNV;
    @FXML
    TableView<MedicineSale> tblBillMedicine;
    @FXML
    TableColumn<MedicineSale,String> clmBillName,clmBillQua,clmBillTotal;
    @FXML
    TableColumn<MedicineSale,Integer> clmBillID;
    @FXML
    TextField txtCash;
    @FXML
    Label lblCusID;
    @FXML
    Button btnCash;
    private SalesController salesController;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void initialize(){
        clmBillID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmBillName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmBillQua.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmBillTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        EventHandler<KeyEvent> checkNum = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!event.getCharacter().matches("[0-9]")){
                    event.consume();
                }
            }
        };
        setLblDate();
        txtCash.addEventFilter(KeyEvent.KEY_TYPED,checkNum);
        btnCash.setDisable(true);

    }
    public void setLblDate(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String datetext = date.format(formatter);
        lblDate.setText(datetext);
    }
    public void setSalesController(SalesController salesController1){
        this.salesController = salesController1;
    }

    public void setInfoCustomer(int id, String name, String add, int phone){
        lblCusID.setText(String.valueOf(id));
        lblCusName.setText(name);
        lblCusPhone.setText("0"+phone);
        lblCusAdd.setText(add);
    }
    public void getDataMed(TableView<MedicineSale> tblBill) throws ParseException {
        this.tblBillMedicine.getItems().addAll(tblBill.getItems());
        setLblTotalPrice();
    }
    public void setLblTotalPrice() throws ParseException {
        float totalPice = 0;
        if (!tblBillMedicine.getItems().isEmpty()){
            for (int idx = 0; idx < tblBillMedicine.getItems().size(); idx++){
                MedicineSale data = tblBillMedicine.getItems().get(idx);
                totalPice = totalPice + parse(data.getTotal(),vietNam()).floatValue();
            }
            lblTotalPrice.setText(money(totalPice));
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
    public void setEnableBtnCash() {
        if(txtCash.getText().startsWith("0")){
            txtCash.clear();
        }
        String money = txtCash.getText();
        boolean isCashDisable = money.isEmpty() || money.trim().isEmpty();
        boolean isDisable = isCashDisable;
        btnCash.setDisable(isDisable);


    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public void setBtnCash() throws ParseException, SQLException {
        float cash = Float.parseFloat(txtCash.getText());
        float totalPrice = parse(lblTotalPrice.getText(),vietNam()).floatValue();
        if (cash < totalPrice){
            lblErrCash.visibleProperty().set(true);
        }else{
            lblErrCash.visibleProperty().set(false);
            float change = cash - totalPrice;

            String date = convertDate(LocalDateTime.now());
            String maNV = lblMaNV.getText();

            int ca = 0;
            LocalDateTime datenow = LocalDateTime.now();
            LocalDateTime checkdate = LocalDateTime.of(LocalDate.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),14,00);
            if(datenow.isBefore(checkdate)){
                ca = 1;
            }else {
                ca =2;
            }
            int maKH = Integer.parseInt(lblCusID.getText());

            int maPX = getMaPhieuXuat(maNV,date, ca,maKH);


            int check = 0;
            for (int idx = 0; idx < tblBillMedicine.getItems().size(); idx++){
                MedicineSale data = tblBillMedicine.getItems().get(idx);
                float price = parse(data.getTotal(),vietNam()).floatValue() /data.getQuantity();
                check = SaleDAO.insertBill(maPX,data.getId(),data.getQuantity(),price);
            }
            if (check > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!!");
                alert.setHeaderText("Payment success!!");
                alert.setContentText("Your Change "+money(change));
                alert.showAndWait();
                this.stage.close();
                this.salesController.mainAppController.setBtnDash();
                this.salesController.mainAppController.setBtnSale();
            }
        }
    }
    public int getMaPhieuXuat(String maNV, String date, int ca, int maKH) throws SQLException {
        //tao MaPhieuNhap
        int checkInsert = SaleDAO.createMaPhieuXuat(maNV,date,ca,maKH);
        if(checkInsert > 0){
            System.out.println("Tao Thanh Cong");
        }
        ResultSet rs = SaleDAO.getMaPhieuXuat();
        int maPhieuXuat = 0;
        while (rs.next()){
            maPhieuXuat = rs.getInt(1);
        }
        return maPhieuXuat;
    }

    public void setMaNV(String text) {
        lblMaNV.setText(text);
    }
}
