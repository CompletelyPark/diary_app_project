package com.jinwan.appproject.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat mformat1 = new SimpleDateFormat("M월 d일");
    public static String getCurrentDateFormattedMonthDay(){
        long mnow1 = System.currentTimeMillis();
        Date mdate1 = new Date(mnow1);
        return mformat1.format(mdate1);

    }

    private static final SimpleDateFormat mformat2 = new SimpleDateFormat("Y년 M월 d일");
    public static String getCurrentDateFormattedMonthDayYear(){
        long mnow2 = System.currentTimeMillis();
        Date mdate2 = new Date(mnow2);
        return mformat2.format(mdate2);

    }

}
