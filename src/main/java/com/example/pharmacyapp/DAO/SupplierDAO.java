package com.example.pharmacyapp.DAO;

import java.sql.*;

public class SupplierDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getDSSup() throws SQLException {
        ResultSet rs;
        String sql = "select * from NhaCungCap\n" +
                "where NhaCungCap.Ktra = 1";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getSearchSup(String name, String phone) throws SQLException {
        ResultSet rs;
        String sql = "select * from NhaCungCap\n" +
                "where NhaCungCap.TenNCC like'%"+name+"%' and NhaCungCap.SDT like'%"+phone+"%'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static int deleteSupplier(int id) throws SQLException {
        String sql = "Update NhaCungCap\n" +
                "set NhaCungCap.Ktra = 0\n" +
                "where NhaCungCap.MaNCC = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        return statement.executeUpdate();
    }
    public static int updateCustomer(int id,String name,String nguoiDD,int sdt,String diaChi,String note) throws SQLException {
        String sql = "update NhaCungCap \n" +
                "set TenNCC = ?,NguoiDaiDien = ?, SDT = ?, DiaChiNCC = ?,Note = ?\n" +
                "where NhaCungCap.MaNCC = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setString(2,nguoiDD);
        statement.setInt(3,sdt);
        statement.setString(4,diaChi);
        statement.setString(5,note);
        statement.setInt(6,id);
        return statement.executeUpdate();
    }
}
