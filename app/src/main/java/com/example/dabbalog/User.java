package com.example.dabbalog;

public class User {
private String count,date,name;

    public User( String count,String date,String name) {
        this.count = count;
        this.date = date;
        this.name = name;
    }

    public User() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}