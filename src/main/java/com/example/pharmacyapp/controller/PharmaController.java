package com.example.pharmacyapp.controller;

import com.example.pharmacyapp.DAO.LoginDAO;
import com.example.pharmacyapp.Model.NhanVien;
import com.example.pharmacyapp.PharmaApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PharmaController implements Initializable {



    @FXML
    Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    @FXML
    Button btnLogin;
    @FXML
    Label lblErrNick, lblErrUser, lblErrPass,lblErrPass1, lblErrLogin;
    @FXML
    TextField txtNickName;
    @FXML
    PasswordField txtPass;
    public String tenNV;
    public String maNV;
    public void initialize(URL url, ResourceBundle rb){
        nickNameCheck();
        passWordCheck();
        txtPass.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                btnLogin.fire();
            }
        });
        txtNickName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.TAB){
                txtPass.requestFocus();
            }
            if(event.getCode() == KeyCode.ENTER){
                btnLogin.fire();
            }
        });

    }


    public void setBtnLogin() throws SQLException {
        if (Objects.equals(txtNickName.getText(),"") || Objects.equals(txtPass.getText(),"")){
            if(Objects.equals(txtNickName.getText(),"") && Objects.equals(txtPass.getText(),"")){
                lblErrUser.visibleProperty().set(true);
                lblErrPass1.visibleProperty().set(true);
            }
            else if (Objects.equals(txtNickName.getText(),"")){
                lblErrUser.visibleProperty().set(true);
            }else {
                lblErrPass1.visibleProperty().set(true);
            }
            lblErrLogin.visibleProperty().set(false);
        }
        else if (passValid()) {
            lblErrLogin.visibleProperty().set(false);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(PharmaApp.class.getResource("MainApp.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainAppController controller = fxmlLoader.getController();
                controller.setStage(stage);
                controller.setController(this);
                controller.setLblMa(maNV);
                controller.setLblName(tenNV);
                stage.setScene(scene);
                stage.centerOnScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            lblErrLogin.visibleProperty().set(true);
        }
    }
    boolean passValid() throws SQLException {
        ResultSet rs = LoginDAO.getLogin();
        while (rs.next()){
            String user,pass;
            user = rs.getString(4);
            pass = rs.getString(5);
            if (Objects.equals(txtNickName.getText(),user) && Objects.equals(txtPass.getText(),pass)){
                maNV = rs.getString(1);
                tenNV = rs.getString(2);
                return true;
            }
        }
        return false;
    }

    private void nickNameCheck(){
        Pattern userpat = Pattern.compile("PM[0-9]{3}");
        txtNickName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(userpat.pattern()) && !Objects.equals(newValue,"")){
                lblErrNick.visibleProperty().set(true);
            }else {
                lblErrNick.visibleProperty().set(false);
            }
        });
        txtNickName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (Objects.equals(newValue,"")){
                lblErrUser.visibleProperty().set(true);
            }else {
                lblErrUser.visibleProperty().set(false);
            }
        });
    }
    private void passWordCheck(){
        Pattern passpat = Pattern.compile("[0-9]{4}");
        txtPass.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(passpat.pattern()) && !Objects.equals(newValue,"")){
                lblErrPass.visibleProperty().set(true);
            }else {
                lblErrPass.visibleProperty().set(false);
            }
        });
        txtPass.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (Objects.equals(newValue,"")){
                lblErrPass1.visibleProperty().set(true);
            }else {
                lblErrPass1.visibleProperty().set(false);
            }
        });
    }


}
