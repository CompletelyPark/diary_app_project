package com.jinwan.appproject.schedule;

public class Schedule {
    private String name;
    private String memo;
    private String dayFirst;
    private String dayLast;
    private String timeFirst;
    private String timeLast;

    public Schedule(String name, String memo, String dayFirst, String dayLast, String timeFirst, String timeLast) {
        this.name = name;
        this.memo = memo;
        this.dayFirst = dayFirst;
        this.dayLast = dayLast;
        this.timeFirst = timeFirst;
        this.timeLast = timeLast;
    }

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
