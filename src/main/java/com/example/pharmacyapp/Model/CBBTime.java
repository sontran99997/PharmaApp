package com.example.pharmacyapp.Model;

public class CBBTime {
    private String timer;
    private int value;

    public CBBTime(String timer, int value) {
        this.timer = timer;
        this.value = value;
    }

    public String getTimer() {
        return timer;
    }

    public int getValue() {
        return value;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
