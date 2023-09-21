package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.DashBoardDAO;
import com.example.pharmacyapp.DAO.ThuocDAO;
import com.example.pharmacyapp.Model.TopThuoc;
import com.example.pharmacyapp.PharmaApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.Locale;

public class DashBoardController {
    @FXML
    Pane mainView;
    @FXML
    Button btnAddNewCus,btnAddNewMed,btnAddNewSup,btnReport;
    @FXML
    TableView<TopThuoc> tblThuoc;
    @FXML
    TableColumn<TopThuoc,String> clmName,clmType,clmSup,clmExpiredDay;
    @FXML
    TableColumn<TopThuoc,Float> clmPrice;
    @FXML
    TableColumn<TopThuoc, Integer> clmCode,clmQua;
    @FXML
    Label lblMa;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void initialize() {
        clmCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("tenThuoc"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmSup.setCellValueFactory(new PropertyValueFactory<>("nhaCC"));
        clmQua.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        clmExpiredDay.setCellValueFactory(new PropertyValueFactory<>("ngayHetHan"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<>("gia"));
        try {
            getDSThuoc();
            setLblTotal();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setMainView(Pane mainView) {
        this.mainView = mainView;

    }
    private PharmaController pharmaController;

    public void setLblMa(String text){
        this.lblMa.setText(text);
    }
    public void setController(PharmaController pharmaController){
        this.pharmaController = pharmaController;
    }

    public void getDSThuoc() throws SQLException {
        ResultSet rs = ThuocDAO.getTopThuoc();

        while(rs.next()){
            int code,soLuong;
            String tenThuoc,type,ncc,ngayHetHan,gia;

            code = rs.getInt(1);
            tenThuoc = rs.getString(2);
            type = rs.getString(3);
            ncc = rs.getString(4);
            soLuong = rs.getInt(5);
            ngayHetHan = rs.getString(6);
            gia = rs.getString(7);
            tblThuoc.getItems().add(new TopThuoc(code,tenThuoc,type,ncc,soLuong,ngayHetHan, money(Float.parseFloat(gia)*2)));
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
    Label lblTotalExpired,lblTotalMed,lblTotalSup,lblTotalCus;
    public void setLblTotal() throws SQLException {
        ResultSet rsMed = DashBoardDAO.getTotalMedicine();
        while(rsMed.next()){
            lblTotalMed.setText(String.valueOf(rsMed.getInt(1)));
        }
        ResultSet rsCus = DashBoardDAO.getTotalCustomer();
        while(rsCus.next()){
            lblTotalCus.setText(String.valueOf(rsCus.getInt(1)));
        }
        ResultSet rsSup = DashBoardDAO.getTotalSupplier();
        while(rsSup.next()){
            lblTotalSup.setText(String.valueOf(rsSup.getInt(1)));
        }
        ResultSet rsExp = DashBoardDAO.getTotalMedExpired();
        while (rsExp.next()){
            lblTotalExpired.setText(String.valueOf(rsExp.getInt(1)));
        }
    }


    public void setBtnAddNewMed() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewMedicine.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 598, 700);
        AddNewMedicineController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setController(this);
        controller.setLblTenNV(lblMa.getText());
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void setBtnAddNewCus() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewCustomer.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 616, 441);
        AddNewCustomerController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setDashController(this);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void setBtnAddNewSupp() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("AddNewSupplier.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 602, 468);
        AddNewSupplierController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        controller.setDashBoardController(this);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
    public void setBtnDailyReport() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("ToDayReport.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 1009, 710);
        ToDayReportController controller = fxmlLoader.getController();
        controller.setStage(stage1);
        stage1.setScene(scene);
        stage1.resizableProperty().setValue(false);
        stage1.initStyle(StageStyle.UNIFIED);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

}
