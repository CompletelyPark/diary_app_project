package com.jinwan.appproject.list;

public class Diary_out {
    private int id;
    private String title;
    private int imageId; // 이미지 리소스 id

    public Diary_out(int id, String title, int imageId) {
        this.id = id;
        this.title = title;
        this.imageId = imageId;
    }

    public Diary_out(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}
