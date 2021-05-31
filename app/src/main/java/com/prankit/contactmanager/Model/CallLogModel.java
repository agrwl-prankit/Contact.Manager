package com.prankit.contactmanager.Model;

public class CallLogModel {
    private String number, time, duration, type;

    public CallLogModel(){}

    public CallLogModel(String number, String time, String duration, String type) {
        this.number = number;
        this.time = time;
        this.duration = duration;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
