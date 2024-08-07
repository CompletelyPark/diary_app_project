package com.jinwan.appproject.recycler;

// MyData.java
// 단순 데이터 정의 예제
// 추후에 일정 등의 정보 관리
public class MyData {
    private String title;
    private String description;

    public MyData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
