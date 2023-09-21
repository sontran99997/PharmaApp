package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.CustomerDAO;
import com.example.pharmacyapp.DAO.ReportDAO;
import com.example.pharmacyapp.Model.Customer;
import com.example.pharmacyapp.Model.MedReport;
import com.example.pharmacyapp.Model.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportController {
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

    public void initialize() throws SQLException, ParseException {
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmShift.setCellValueFactory(new PropertyValueFactory<>("ca"));
        clmExport.setCellValueFactory(new PropertyValueFactory<>("exportMoney"));
        clmImport.setCellValueFactory(new PropertyValueFactory<>("importMoney"));

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
        setDate();
        getDSReport();
        tblImportReport.getItems().clear();
        tblExportReport.getItems().clear();
        getDetailReport();
    }

    @FXML
    TableView<Report> tblReport;
    @FXML
    TableColumn<Report,Integer> clmShift;
    @FXML
    TableColumn<Report,String> clmDate,clmExport,clmImport;
    @FXML
    DatePicker txtStart,txtEnd;
    public void setDate(){
        txtStart.setValue(LocalDate.from(LocalDateTime.now().plusDays(-3)));
        txtEnd.setValue(LocalDate.from(LocalDateTime.now()));
    }
    public String convertDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return date.format(formatter);
    }
    public void getDSReport() throws SQLException, ParseException {
        String dateStart = convertDate(txtStart.getValue().atStartOfDay());
        String dateEnd = convertDate(txtEnd.getValue().atStartOfDay());
        tblReport.getItems().clear();
        tblImportReport.getItems().clear();
        tblExportReport.getItems().clear();
        getDetailReport();


        ResultSet rs = ReportDAO.getDSReport(dateStart,dateEnd);

        while(rs.next()){
            String date,importMoney,exportMoney;
            int shift;
            date = rs.getString(1);
            shift = rs.getInt(2);
            exportMoney = rs.getString(3);
            importMoney = rs.getString(4);

            tblReport.getItems().add(new Report(date,shift,money(Float.parseFloat(exportMoney)),money(Float.parseFloat(importMoney))));
        }
    }

    @FXML
    TableView<MedReport> tblExportReport,tblImportReport;
    @FXML
    TableColumn<MedReport,Integer> clmID1,clmID2,clmQua1,clmQua2;
    @FXML
    TableColumn<MedReport,String> clmName1,clmName2,clmTotal1,clmTotal2;
    @FXML
    TableColumn<MedReport,Float> clmPrice1,clmPrice2;
    public void getDetailReport() throws SQLException, ParseException {
        String dateStart = convertDate(txtStart.getValue().atStartOfDay());
        String dateEnd = convertDate(txtEnd.getValue().atStartOfDay());

        ResultSet rs1 = ReportDAO.getExportReport(dateStart,dateEnd);
        while(rs1.next()){
            int id,qua;
            String name,total;
            float price;
            id = rs1.getInt(1);
            name = rs1.getString(2);
            price = rs1.getFloat(3);
            qua = rs1.getInt(4);
            total = rs1.getString(5);
            tblExportReport.getItems().add(new MedReport(id,name,price,qua,money(Float.parseFloat(total))));
        }

        ResultSet rs2 = ReportDAO.getImportReport(dateStart,dateEnd);
        while(rs2.next()){
            int id,qua;
            String name,total;
            float price;
            id = rs2.getInt(1);
            name = rs2.getString(2);
            price = rs2.getFloat(3);
            qua = rs2.getInt(4);
            total = rs2.getString(5);
            tblImportReport.getItems().add(new MedReport(id,name,price,qua,money(Float.parseFloat(total))));
        }
        setLblExpTotalPrice();
        setLblImpTotalPrice();
    }
    public void getDetailSelected() throws SQLException, ParseException {
        tblImportReport.getItems().clear();
        tblExportReport.getItems().clear();

        Report seleceted = tblReport.getSelectionModel().getSelectedItem();
        String date = seleceted.getDate();
        int shift = seleceted.getCa();

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
            tblExportReport.getItems().add(new MedReport(id,name,price,qua,money(Float.parseFloat(total))));
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
            tblImportReport.getItems().add(new MedReport(id,name,price,qua,money(Float.parseFloat(total))));
        }
        setLblExpTotalPrice();
        setLblImpTotalPrice();
    }
    @FXML
    Label lblExpTotal,lblImpTotal;
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
}
