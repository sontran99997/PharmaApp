package com.example.pharmacyapp.Model;

public class Bill {
    private int id;
    private String name;
    private String date;
    private String total;

    public Bill(int id, String name, String date, String total) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
