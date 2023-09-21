package com.example.pharmacyapp.Model;

public class InfoMedicineSale {
    private int id;
    private String name;
    private  int quantity;
    private String price;

    public InfoMedicineSale(int id, String name, int quantity, String price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quatity) {
        this.quantity = quatity;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
