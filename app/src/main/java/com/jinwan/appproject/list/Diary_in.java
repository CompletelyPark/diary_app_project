package com.jinwan.appproject.list;

public class Diary_in {
    private int id;
    private String title;
    private String text;
    private String fontName; // font 이름 일단은
    private float fontSize; // textsize 값 직접 저장하는 방식으로
    private int styleType; // Typeface 정수 값
    private int isUnderline; // underline 인지 아닌지 확인하는 변수 0일때 not underline, 1일때 underline
    private int alignment;
    private int imageId;

    public Diary_in(int id, String title, String text, String fontName, float fontSize, int styleType, int isUnderline, int alignment, int imageId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.styleType = styleType;
        this.isUnderline = isUnderline;
        this.alignment = alignment;
        this.imageId = imageId;
    }

    public Diary_in(String title, String text, String fontName, float fontSize, int styleType, int isUnderline, int alignment, int imageId) {
        this.title = title;
        this.text = text;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.styleType = styleType;
        this.isUnderline = isUnderline;
        this.alignment = alignment;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getFontName() {
        return fontName;
    }

    public float getFontSize() {
        return fontSize;
    }

    public int getStyleType() {
        return styleType;
    }

    public int getIsUnderline() {
        return isUnderline;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getImageId() {
        return imageId;
    }
    
}
