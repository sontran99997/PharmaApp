package com.example.pharmacyapp.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NhanVienDAO {
    private static Connection connection =ConnectDB.getInstance();

    public static ResultSet getNV(String user) throws SQLException {
        ResultSet rs;
        String sql = "select * from NhanVien where NickName = '"+user+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
}
