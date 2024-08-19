package com.jinwan.appproject.diary;

public class DiaryEntry {
    private String title;
    private String content;
    private int textSize;
    private int alignment;
    private String weatherIcon;

    public DiaryEntry(String title, String content, int textSize, int alignment, String weatherIcon) {
        this.title = title;
        this.content = content;
        this.textSize = textSize;
        this.alignment = alignment;
        this.weatherIcon = weatherIcon;
    }

    // Getters and setters for each field
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getAlignment() {
        return alignment;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
