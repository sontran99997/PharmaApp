package com.example.pharmacyapp.Model;

public class MedicineSale {
    private int id;
    private String name;
    private int quantity;
    private String total;

    public MedicineSale(int id, String name, int quantity, String total) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
