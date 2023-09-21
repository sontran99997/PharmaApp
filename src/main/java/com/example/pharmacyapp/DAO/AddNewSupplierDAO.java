package com.example.pharmacyapp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewSupplierDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static int insertNewSupplier(String tenNCC,String nguoiDD,int sdt,String diaChi,String note) throws SQLException {
        String sql = "insert into NhaCungCap(TenNCC,NguoiDaiDien,SDT,DiaChiNCC,Note,Ktra)\n" +
                "values (?,?,?,?,?,1)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,tenNCC);
        statement.setString(2,nguoiDD);
        statement.setInt(3,sdt);
        statement.setString(4,diaChi);
        statement.setString(5,note);
        return statement.executeUpdate();
    }
}
