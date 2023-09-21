package com.example.pharmacyapp.DAO;

import java.sql.*;

public class CustomerDAO {
    private static Connection connection =ConnectDB.getInstance();

    public static ResultSet getDSKH() throws SQLException {
        ResultSet rs;
        String sql = "exec PagingKH 1,20";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getPage(int i) throws SQLException {
        ResultSet rs;
        String sql = "exec PagingKH "+i+",20";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getNumPage() throws SQLException {
        ResultSet rs;
        String sql = "select (Count(*)/20)+1 from KhachHang";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getBill(int id) throws SQLException {
        ResultSet rs;
        String sql = "select  PhieuXuatHang.MaPXH, KhachHang.TenKH,PhieuXuatHang.NgayXuat,SUM(ChiTietPXH.DonGia*ChiTietPXH.SoLuong)\n" +
                "from ChiTietPXH,PhieuXuatHang, KhachHang\n" +
                "where ChiTietPXH.MaPXH = PhieuXuatHang.MaPXH and PhieuXuatHang.MaKH = KhachHang.MaKH and KhachHang.MaKH ="+id+"\n" +
                "group by PhieuXuatHang.MaPXH, KhachHang.TenKH,PhieuXuatHang.NgayXuat";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet search(String name ,String sdt) throws SQLException {
        ResultSet rs;
        String sql = "select KhachHang.MaKH,KhachHang.TenKH ,KhachHang.SDT,KhachHang.NguyenNhanBenh,KhachHang.NgayTao,KhachHang.DiaChiKH\n" +
                "from KhachHang\n" +
                "where KhachHang.TenKH like '%"+name+"%' and KhachHang.SDT like'%"+sdt+"%'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static int deleteCustomer(int id) throws SQLException {
        String sql = "Update KhachHang\n" +
                "set KhachHang.Ktra = 0\n" +
                "where KhachHang.MaKH = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        return statement.executeUpdate();
    }
    public static int updateCustomer(int id, String name, int phone, String address,String note) throws SQLException {
        String sql = "UPDATE KhachHang\n" +
                "set TenKH = ?, SDT = ?, DiaChiKH = ?, NguyenNhanBenh = ?\n" +
                "where MaKH = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setInt(2,phone);
        statement.setString(3,address);
        statement.setString(4,note);
        statement.setInt(5,id);
        return statement.executeUpdate();
    }

}
