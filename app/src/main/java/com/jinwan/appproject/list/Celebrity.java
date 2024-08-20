package com.jinwan.appproject.list;

public class Celebrity {
    private int id;
    private String memo;

    public Celebrity(String memo) {
        this.memo = memo;
    }

    public Celebrity(int id, String memo) {
        this.id = id;
        this.memo = memo;
    }


    public String getMemo() {
        return memo;
    }

}
