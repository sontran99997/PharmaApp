package com.example.pharmacyapp.DAO;

import com.example.pharmacyapp.Model.NhanVien;

import java.sql.*;

public class LoginDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getLogin() throws SQLException {
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select * from NhanVien");
        return rs;
    }
}
