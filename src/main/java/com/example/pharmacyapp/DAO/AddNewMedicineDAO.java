package com.example.pharmacyapp.DAO;

import java.sql.*;

public class AddNewMedicineDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getType() throws SQLException {
        ResultSet rs;
        String sql = "select TenLT from LoaiThuoc";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getUnit() throws SQLException {
        ResultSet rs;
        String sql = "select TenDDVT from LoaiDonViTinh";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getSupplier() throws SQLException {
        ResultSet rs;
        String sql = "select TenNCC from NhaCungCap";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getMa(String ma) throws SQLException {
        ResultSet rs;
        String sql = "select MaNV,TenNV from NhanVien where MaNV = '"+ma+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static int createMaPhieuNhap(String ma,String date,int ca) throws SQLException {
        String sql = "insert into PhieuNhapHang (MaNV,NgayNhap,MaCLV)\n" +
                "values (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,ma);
        statement.setString(2,date);

        statement.setInt(3,ca);
        return statement.executeUpdate();
    }
    public static ResultSet getMaPhieuNhap() throws SQLException {
        ResultSet rs;
        String sql = "select Max(MaPNH) from PhieuNhapHang";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getMedInfo(int id) throws SQLException {
        ResultSet rs;
        String sql = "select distinct Thuoc.TenThuoc,LoaiThuoc.TenLT,LoaiDonViTinh.TenDDVT,NhaCungCap.TenNCC,ChiTietPNH.DonGia\n" +
                "from Thuoc,LoaiThuoc,LoaiDonViTinh,NhaCungCap,ChiTietPNH\n" +
                "where Thuoc.MaThuoc = "+id+" and NhaCungCap.MaNCC = Thuoc.MaNCC and LoaiThuoc.MaLT = Thuoc.MaLT \n" +
                "and LoaiDonViTinh.MaDVT = Thuoc.MaDVT and Thuoc.MaThuoc=ChiTietPNH.MaThuoc";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet checkID(int id) throws SQLException {
        ResultSet rs;
        String sql = "select MaThuoc from Thuoc where MaThuoc = "+id;
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static int insertHaveID(int maPNH, int id, int soLuong, float gia) throws SQLException {
        String sql = "INSERT INTO ChiTietPNH (MaPNH, MaThuoc, SoLuong, DonGia)\n" +
                "VALUES (?, ?, ?, ?)\n" +
                "UPDATE Thuoc\n" +
                "set SoLuong = (select (select SUM(ChiTietPNH.SoLuong) from ChiTietPNH where ChiTietPNH.MaThuoc = ?)-(select SUM(ChiTietPXH.SoLuong) from ChiTietPXH where ChiTietPXH.MaThuoc = ?))\n" +
                "where Thuoc.MaThuoc = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,maPNH);
        statement.setInt(2,id);
        statement.setInt(3,soLuong);
        statement.setFloat(4,gia);
        statement.setInt(5,id);
        statement.setInt(6,id);
        statement.setInt(7,id);
        return statement.executeUpdate();
    }
    public static int insertNonID(int maLT,int maNCC,int maDVT,int soLuong, String ngayNhap, String ngayHetHan, String tenThuoc, int maPNH, float gia) throws SQLException {
        String sql = "Insert into Thuoc(MaLT,MaNCC,MaDVT,SoLuong,NgayNhap,NgayHetHan,TenThuoc,Ktra)\n" +
                "values (?,?,?,?,?,?,?,1)\n" +
                "INSERT INTO ChiTietPNH (MaPNH, MaThuoc, SoLuong, DonGia)\n" +
                "VALUES (?,(select MAX(MaThuoc) from Thuoc), ?, ?)\n" +
                "Insert into ChiTietPXH(MaPXH, MaThuoc, SoLuong, DonGia)\n" +
                "values (1,(select MAX(MaThuoc) from Thuoc),0,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,maLT);
        statement.setInt(2,maNCC);
        statement.setInt(3,maDVT);
        statement.setInt(4,soLuong);
        statement.setString(5,ngayNhap);
        statement.setString(6,ngayHetHan);
        statement.setString(7,tenThuoc);

        statement.setInt(8,maPNH);
        statement.setInt(9,soLuong);
        statement.setFloat(10,gia);

        statement.setFloat(11,gia*2);

        return statement.executeUpdate();
    }
}
