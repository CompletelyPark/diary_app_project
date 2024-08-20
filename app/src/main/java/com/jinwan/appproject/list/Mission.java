package com.jinwan.appproject.list;

public class Mission {

    private String memo;
    private String timeFirst;
    private String timeLast;

    public Mission(String memo, String timeFirst, String timeLast) {
        this.memo = memo;
        this.timeFirst = timeFirst;
        this.timeLast = timeLast;
    }

    public String getMemo2() {
        return memo;
    }

    public String getTimeFirst2() {
        return timeFirst;
    }

    public String getTimeLast2() {
        return timeLast;
    }

}
