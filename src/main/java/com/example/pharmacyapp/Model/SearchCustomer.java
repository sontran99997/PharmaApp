package com.example.pharmacyapp.Model;

public class SearchCustomer {
    private int id;
    private String name;
    private int phone;
    private String add;
    private String note;

    public SearchCustomer(int id, String name, int phone, String add, String note) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.add = add;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getAdd() {
        return add;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
