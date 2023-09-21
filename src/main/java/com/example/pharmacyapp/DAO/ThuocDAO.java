package com.example.pharmacyapp.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThuocDAO {
    private static Connection connection =ConnectDB.getInstance();

    public static ResultSet getTopThuoc() throws SQLException {
        ResultSet rs;
        String sql = "select distinct Thuoc.MaThuoc,TenThuoc,TenLT,TenNCC,Thuoc.SoLuong,NgayHetHan,ChiTietPNH.DonGia \n" +
                "from Thuoc,LoaiThuoc,NhaCungCap,ChiTietPNH\n" +
                "where LoaiThuoc.MaLT = Thuoc.MaLT and NhaCungCap.MaNCC = Thuoc.MaNCC and Thuoc.MaThuoc = ChiTietPNH.MaThuoc and Thuoc.Ktra = 'True'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }


}
