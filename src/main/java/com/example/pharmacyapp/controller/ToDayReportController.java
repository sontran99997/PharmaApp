package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.ReportDAO;
import com.example.pharmacyapp.Model.MedReport;
import com.example.pharmacyapp.Model.MedicineSale;
import com.example.pharmacyapp.Model.Report;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

public class ToDayReportController {
    @FXML
    TableView<MedReport> tblExportReport,tblImportReport;
    @FXML
    TableColumn<MedReport,Integer> clmID1,clmID2,clmQua1,clmQua2;
    @FXML
    TableColumn<MedReport,String> clmName1,clmName2,clmTotal1,clmTotal2;
    @FXML
    TableColumn<MedReport,Float> clmPrice1,clmPrice2;
    @FXML
    ComboBox<String> cbbShift;
    @FXML
    Label lblExpTotal,lblImpTotal;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void initialize() throws SQLException, ParseException {

        clmID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmQua1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmTotal1.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        clmID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmQua2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmTotal2.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        setCbbShift();
        cbbShift.getSelectionModel().selectFirst();
        getDetailSelected();

    }

    public void setCbbShift() throws SQLException {

        cbbShift.setItems(FXCollections.observableArrayList(
                "All Day",
                "1",
                "2"
        ));

    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public void getDetailSelected() throws SQLException, ParseException {
        tblImportReport.getItems().clear();
        tblExportReport.getItems().clear();
//        String date = convertDate(LocalDateTime.now());
        String date = "2023/07/20"; //test chuc nang
        int shift = cbbShift.getSelectionModel().getSelectedIndex();
        if (shift == 1){
            ResultSet rs1 = ReportDAO.getDailyExportReport(date,shift);
            while(rs1.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs1.getInt(1);
                name = rs1.getString(2);
                price = rs1.getFloat(3);
                qua = rs1.getInt(4);
                total = rs1.getString(5);
                String b = money(Float.parseFloat(total));
                tblExportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }

            ResultSet rs2 = ReportDAO.getDailyImportReport(date,shift);
            while(rs2.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs2.getInt(1);
                name = rs2.getString(2);
                price = rs2.getFloat(3);
                qua = rs2.getInt(4);
                total = rs2.getString(5);
                String b = money(Float.parseFloat(total));
                tblImportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }
        } else if (shift == 2) {
            ResultSet rs1 = ReportDAO.getDailyExportReport(date,shift);
            while(rs1.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs1.getInt(1);
                name = rs1.getString(2);
                price = rs1.getFloat(3);
                qua = rs1.getInt(4);
                total = rs1.getString(5);
                String b = money(Float.parseFloat(total));
                tblExportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }

            ResultSet rs2 = ReportDAO.getDailyImportReport(date,shift);
            while(rs2.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs2.getInt(1);
                name = rs2.getString(2);
                price = rs2.getFloat(3);
                qua = rs2.getInt(4);
                total = rs2.getString(5);
                String b = money(Float.parseFloat(total));
                tblImportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }
        } else if (shift == 0){
            ResultSet rs1 = ReportDAO.getAllDailyExportReport(date);
            while(rs1.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs1.getInt(1);
                name = rs1.getString(2);
                price = rs1.getFloat(3);
                qua = rs1.getInt(4);
                total = rs1.getString(5);
                String b = money(Float.parseFloat(total));
                tblExportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }

            ResultSet rs2 = ReportDAO.getAllDailyImportReport(date);
            while(rs2.next()){
                int id,qua;
                String name,total;
                float price;
                id = rs2.getInt(1);
                name = rs2.getString(2);
                price = rs2.getFloat(3);
                qua = rs2.getInt(4);
                total = rs2.getString(5);
                String b = money(Float.parseFloat(total));
                tblImportReport.getItems().add(new MedReport(id,name,price,qua,b));
            }
        }
        setLblExpTotalPrice();
        setLblImpTotalPrice();

    }

    public void setLblExpTotalPrice() throws ParseException {
        float totalPice = 0;
        if (!tblExportReport.getItems().isEmpty()){
            for (int idx = 0; idx < tblExportReport.getItems().size(); idx++){
                MedReport data = tblExportReport.getItems().get(idx);
                totalPice = totalPice + parse(data.getTotalPrice(),vietNam()).floatValue();
            }
            lblExpTotal.setText(money(totalPice));
        }
    }
    public void setLblImpTotalPrice() throws ParseException {
        float totalPice = 0;
        if (!tblImportReport.getItems().isEmpty()){
            for (int idx = 0; idx < tblImportReport.getItems().size(); idx++){
                MedReport data = tblImportReport.getItems().get(idx);
                totalPice = totalPice + parse(data.getTotalPrice(),vietNam()).floatValue();
            }
            lblImpTotal.setText(money(totalPice));
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
    public void setBtnConfirm(){
        this.stage.close();
    }

}
