package com.jinwan.appproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat mformat = new SimpleDateFormat("M월 d일");
    public static String getCurrentDateFormatted(){
        long mnow = System.currentTimeMillis();
        Date mdate = new Date(mnow);
        return mformat.format(mdate);

    }
}
