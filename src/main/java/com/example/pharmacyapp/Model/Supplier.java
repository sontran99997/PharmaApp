package com.example.pharmacyapp.Model;

public class Supplier {
    private int id;
    private String name;
    private String address;
    private String person;
    private int phone;
    private String note;

    public Supplier(int id, String name, String address, String person, int phone, String note) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.person = person;
        this.phone = phone;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPerson() {
        return person;
    }

    public int getPhone() {
        return phone;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
