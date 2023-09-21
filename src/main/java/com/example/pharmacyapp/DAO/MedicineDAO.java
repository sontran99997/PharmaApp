package com.example.pharmacyapp.DAO;

import java.sql.*;

public class MedicineDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getDSThuoc() throws SQLException {
        ResultSet rs;
        String sql = "select distinct Thuoc.MaThuoc,TenThuoc,TenLT,TenDDVT,Thuoc.SoLuong,NhaCungCap.TenNCC,Thuoc.NgayNhap,Thuoc.NgayHetHan,ChiTietPNH.DonGia\n" +
                "from Thuoc,NhaCungCap,LoaiThuoc,LoaiDonViTinh,ChiTietPNH\n" +
                "where Thuoc.MaThuoc = ChiTietPNH.MaThuoc and Thuoc.MaLT = LoaiThuoc.MaLT and Thuoc.MaNCC = NhaCungCap.MaNCC and Thuoc.MaDVT = LoaiDonViTinh.MaDVT and Thuoc.Ktra = 'True'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getSearchThuoc(String name, String supp, String type) throws SQLException {
        ResultSet rs;
        String sql = "select distinct Thuoc.MaThuoc,TenThuoc,TenLT,TenDDVT,Thuoc.SoLuong,NhaCungCap.TenNCC,Thuoc.NgayNhap,Thuoc.NgayHetHan,ChiTietPNH.DonGia \n" +
                "from Thuoc,NhaCungCap,LoaiThuoc,LoaiDonViTinh,ChiTietPNH\n" +
                "where Thuoc.MaLT = LoaiThuoc.MaLT and Thuoc.MaNCC = NhaCungCap.MaNCC and Thuoc.MaDVT = LoaiDonViTinh.MaDVT and ChiTietPNH.MaThuoc = Thuoc.MaThuoc and Thuoc.Ktra = 'True' \n" +
                "and Thuoc.TenThuoc like'%"+name+"%' and NhaCungCap.TenNCC like'%"+supp+"%' and LoaiThuoc.TenLT like N'%"+type+"%'\n" +
                "order by Thuoc.MaThuoc";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static int deleteMedicine(int id) throws SQLException {
        String sql = "Update Thuoc\n" +
                "set Thuoc.Ktra = 0\n" +
                "where Thuoc.MaThuoc = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        return statement.executeUpdate();
    }
    public static int updateMedicine(int id,String name,int type,int unit,String dateImport, String dateExpried, float price,int supp) throws SQLException {
        String sql = "update Thuoc\n" +
                "set Thuoc.TenThuoc = ?, Thuoc.MaLT = ?,Thuoc.MaDVT = ?, Thuoc.NgayNhap = ?, Thuoc.NgayHetHan = ?, Thuoc.MaNCC = ?\n" +
                "where Thuoc.MaThuoc = ?\n" +
                "update ChiTietPNH\n" +
                "set ChiTietPNH.DonGia = ?\n" +
                "where ChiTietPNH.MaThuoc = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setInt(2,type);
        statement.setInt(3,unit);
        statement.setString(4,dateImport);
        statement.setString(5,dateExpried);
        statement.setInt(6,supp);
        statement.setInt(7,id);
        statement.setFloat(8,price);
        statement.setInt(9,id);
        return statement.executeUpdate();
    }


}
