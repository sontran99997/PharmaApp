package com.example.pharmacyapp.Model;

public class TopThuoc {
    private int code;
    private String tenThuoc;
    private String type;
    private String nhaCC;
    private int soLuong;
    private String ngayHetHan;
    private String gia;

    public TopThuoc(int code, String tenThuoc, String type, String nhaCC, int soLuong, String ngayHetHan, String gia) {
        this.code = code;
        this.tenThuoc = tenThuoc;
        this.type = type;
        this.nhaCC = nhaCC;
        this.soLuong = soLuong;
        this.ngayHetHan = ngayHetHan;
        this.gia = gia;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNhaCC(String nhaCC) {
        this.nhaCC = nhaCC;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getCode() {
        return code;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getType() {
        return type;
    }

    public String getNhaCC() {
        return nhaCC;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public String getGia() {
        return gia;
    }
}
