package com.example.pharmacyapp.Model;

public class NhanVien {
    private String MaNV;
    private String TenNV;
    private String DiaChiNV;
    private String NickName;
    private String Password;

    public String getMaNV() {
        return MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public String getDiaChiNV() {
        return DiaChiNV;
    }

    public String getNickName() {
        return NickName;
    }

    public String getPassword() {
        return Password;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public void setDiaChiNV(String diaChiNV) {
        DiaChiNV = diaChiNV;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public NhanVien(String maNV, String tenNV, String diaChiNV, String nickName, String password) {
        MaNV = maNV;
        TenNV = tenNV;
        DiaChiNV = diaChiNV;
        NickName = nickName;
        Password = password;
    }
    public NhanVien(String maNV,String tenNV){
        MaNV = maNV;
        TenNV = tenNV;
    }

    public NhanVien() {
    }
}
