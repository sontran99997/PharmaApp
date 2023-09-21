package com.example.pharmacyapp.Model;

public class Report{
    private String date;
    private int ca;
    private String exportMoney;
    private String importMoney;

    public Report(String date, int ca, String exportMoney, String importMoney) {
        this.date = date;
        this.ca = ca;
        this.exportMoney = exportMoney;
        this.importMoney = importMoney;
    }

    public String getDate() {
        return date;
    }

    public int getCa() {
        return ca;
    }

    public String getExportMoney() {
        return exportMoney;
    }

    public String getImportMoney() {
        return importMoney;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public void setExportMoney(String exportMoney) {
        this.exportMoney = exportMoney;
    }

    public void setImportMoney(String importMoney) {
        this.importMoney = importMoney;
    }
}
