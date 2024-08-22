package com.jinwan.appproject.list;

import java.io.Serializable;

public class Diary_out implements Serializable {
    private int id;
    private String title;
    private int imageId; // 이미지 리소스 id

    public Diary_out(int id, String title, int imageId) {
        this.id = id;
        this.title = title;
        this.imageId = imageId;
    }

    public Diary_out(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}
