package com.jinwan.appproject.list;

public class Schedule {
    private int id;
    private String name;
    private String memo;
    private String dayFirst;
    private String dayLast;
    private String timeFirst;
    private String timeLast;

    public Schedule(int id, String name, String memo, String dayFirst, String dayLast, String timeFirst, String timeLast) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.dayFirst = dayFirst;
        this.dayLast = dayLast;
        this.timeFirst = timeFirst;
        this.timeLast = timeLast;
    }

    public Schedule(String name, String memo, String dayFirst, String dayLast, String timeFirst, String timeLast) {
        this.name = name;
        this.memo = memo;
        this.dayFirst = dayFirst;
        this.dayLast = dayLast;
        this.timeFirst = timeFirst;
        this.timeLast = timeLast;
    }

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public String getMemo() {
        return memo;
    }

    public String getDayFirst() {
        return dayFirst;
    }

    public String getDayLast() {
        return dayLast;
    }

    public String getTimeFirst() {
        return timeFirst;
    }

    public String getTimeLast() {
        return timeLast;
    }
}
