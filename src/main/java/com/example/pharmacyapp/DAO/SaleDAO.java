package com.example.pharmacyapp.DAO;

import java.sql.*;

public class SaleDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getDSThuoc() throws SQLException {
        ResultSet rs;
        String sql = "select distinct Thuoc.MaThuoc,Thuoc.TenThuoc,Thuoc.SoLuong,ChiTietPNH.DonGia*2\n" +
                    "from Thuoc,ChiTietPNH\n" +
                    "where Thuoc.MaThuoc = ChiTietPNH.MaThuoc";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getToThuocView(int id) throws SQLException {
        ResultSet rs;
        String sql = "select Thuoc.MaThuoc,ChiTietPNH.DonGia \n" +
                    "from Thuoc,ChiTietPNH\n" +
                    "where Thuoc.MaThuoc = ChiTietPNH.MaThuoc and Thuoc.MaThuoc = "+id;
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet checkCustomer(int phone) throws SQLException {
        ResultSet rs;
        String sql = "select KhachHang.MaKH,KhachHang.TenKH,KhachHang.SDT,KhachHang.DiaChiKH,KhachHang.NguyenNhanBenh from KhachHang\n" +
                "where KhachHang.SDT = "+phone;
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static int createMaPhieuXuat(String ma,String date,int ca, int maKH) throws SQLException {
        String sql = "insert into PhieuXuatHang (MaNV,NgayXuat,MaCLV,MaKH)\n" +
                "values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,ma);
        statement.setString(2,date);
        statement.setInt(3,ca);
        statement.setInt(4,maKH);
        return statement.executeUpdate();
    }
    public static ResultSet getMaPhieuXuat() throws SQLException {
        ResultSet rs;
        String sql = "select Max(MaPXH) from PhieuXuatHang";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }


    public static int insertBill(int maPXH, int maThuoc, int soLuong, float donGia) throws SQLException {
        String sql = "insert into ChiTietPXH(MaPXH,MaThuoc,SoLuong,DonGia)\n" +
                "values (?,?,?,?)\n" +
                "UPDATE Thuoc\n" +
                "set SoLuong = (select (select SUM(ChiTietPNH.SoLuong) from ChiTietPNH where ChiTietPNH.MaThuoc = ?)-(select SUM(ChiTietPXH.SoLuong) from ChiTietPXH where ChiTietPXH.MaThuoc = ?))\n" +
                "where Thuoc.MaThuoc = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,maPXH);
        statement.setInt(2,maThuoc);
        statement.setInt(3,soLuong);
        statement.setFloat(4,donGia);
        statement.setInt(5,maThuoc);
        statement.setInt(6,maThuoc);
        statement.setInt(7,maThuoc);
        return statement.executeUpdate();
    }
    public static ResultSet getDSKH() throws SQLException {
        ResultSet rs;
        String sql = "select KhachHang.MaKH,KhachHang.TenKH ,KhachHang.SDT,KhachHang.DiaChiKH,KhachHang.NguyenNhanBenh\n" +
                "from KhachHang";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getNoInfo() throws SQLException {
        ResultSet rs;
        String sql = "select KhachHang.MaKH,KhachHang.TenKH ,KhachHang.SDT,KhachHang.DiaChiKH,KhachHang.NguyenNhanBenh\n" +
                "from KhachHang where KhachHang.MaKH = 31";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
}
