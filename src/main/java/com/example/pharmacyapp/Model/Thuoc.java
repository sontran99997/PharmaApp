package com.example.pharmacyapp.Model;

public class Thuoc {
    private int ID;
    private String name;
    private String type;
    private String unit;
    private int quantity;
    private String supplier;
    private String importDay;
    private String expiredDay;
    private String purchase;
    private String sell;

    public Thuoc(int ID, String name, String type, String unit, int quantity, String supplier, String importDay, String expiredDay, String purchase, String sell) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.quantity = quantity;
        this.supplier = supplier;
        this.importDay = importDay;
        this.expiredDay = expiredDay;
        this.purchase = purchase;
        this.sell = sell;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setImportDay(String importDay) {
        this.importDay = importDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getImportDay() {
        return importDay;
    }

    public String getExpiredDay() {
        return expiredDay;
    }

    public String getPurchase() {
        return purchase;
    }

    public String getSell() {
        return sell;
    }
}
