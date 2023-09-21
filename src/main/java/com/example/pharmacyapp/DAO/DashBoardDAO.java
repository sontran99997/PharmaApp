package com.example.pharmacyapp.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashBoardDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getTotalMedicine() throws SQLException {
        ResultSet rs;
        String sql = "select count(Thuoc.MaThuoc) from Thuoc";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getTotalCustomer() throws SQLException {
        ResultSet rs;
        String sql = "select count(KhachHang.MaKH) from KhachHang";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getTotalSupplier() throws SQLException {
        ResultSet rs;
        String sql = "select count(NhaCungCap.MaNCC) from NhaCungCap";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getTotalMedExpired() throws SQLException {
        ResultSet rs;
        String sql = "exec [Thuoc_Het_Han] 30\n" +
                "SELECT @@ROWCOUNT;";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
}
