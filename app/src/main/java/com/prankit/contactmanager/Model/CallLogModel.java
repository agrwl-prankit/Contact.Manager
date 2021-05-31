package com.prankit.contactmanager.Model;

public class CallLogModel {
    private String number, time, date, month, year, duration, type;

    public CallLogModel(){}

    public CallLogModel(String number, String time, String date, String month, String year, String duration, String type) {
        this.number = number;
        this.time = time;
        this.date = date;
        this.month = month;
        this.year = year;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
