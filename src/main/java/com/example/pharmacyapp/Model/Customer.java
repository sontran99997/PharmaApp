package com.example.pharmacyapp.Model;

public class Customer {
    private int id;
    private String name;
    private int sdt;
    private String benh;
    private String ngayTao;
    private String diaChi;

    public Customer(int id, String name, int sdt, String benh, String ngayTao, String diaChi) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
        this.benh = benh;
        this.ngayTao = ngayTao;
        this.diaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSdt() {
        return sdt;
    }

    public String getBenh() {
        return benh;
    }

    public String getNgayTao() {
        return ngayTao;
    }


    public String getDiaChi() {
        return diaChi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public void setBenh(String benh) {
        this.benh = benh;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }


    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
