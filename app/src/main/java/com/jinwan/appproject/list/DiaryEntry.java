package com.jinwan.appproject.list;

import java.io.Serializable;
import java.util.Date;

public class DiaryEntry implements Serializable {
    private int id;
    private String title;
    private String content;
    private String font;
    private int fontSize;
    private boolean isBold;
    private boolean isItalic;
    private int weatherIcon;
    private Date date;


    public DiaryEntry(int id, String title, String content, String font, int fontSize,
                      boolean isBold, boolean isItalic, int weatherIcon, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.font = font;
        this.fontSize = fontSize;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.weatherIcon = weatherIcon;
        this.date = date;
    }


    // Getter와 Setter 메서드
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }


    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
