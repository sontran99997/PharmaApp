package com.example.pharmacyapp.DAO;

import java.sql.*;

public class AddNewCusDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static int insertNewCustomer(String ten,String diaChi,String ngay,int sdt,String note) throws SQLException {
        String sql = "insert into KhachHang(TenKH,DiaChiKH,NgayTao,SDT,NguyenNhanBenh,Ktra)\n" +
                "values (?,?,?,?,?,1)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,ten);
        statement.setString(2,diaChi);
        statement.setString(3,ngay);
        statement.setInt(4,sdt);
        statement.setString(5,note);
        return statement.executeUpdate();
    }

}
