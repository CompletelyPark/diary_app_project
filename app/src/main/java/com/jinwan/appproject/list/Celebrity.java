package com.jinwan.appproject.list;

public class Celebrity {
    private int id;
    private String memo;
    private String date;

    public Celebrity(String memo, String date) {
        this.memo = memo;
        this.date = date;
    }

    public Celebrity(int id, String memo, String date) {
        this.id = id;
        this.memo = memo;
        this.date = date;
    }

    public int getId() {return id;}

    public String getMemo() {return memo;}

    public String getDate() {return date;}
}
