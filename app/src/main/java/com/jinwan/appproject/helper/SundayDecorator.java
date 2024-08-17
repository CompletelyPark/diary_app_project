package com.jinwan.appproject.helper;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class SundayDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (day != null) {
            // CalendarDay에서 LocalDate로 변환
            java.time.LocalDate localDate = java.time.LocalDate.of(day.getYear(), day.getMonth(), day.getDay());
            // LocalDate를 Date로 변환
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            calendar.setTime(date);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        if (view != null) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
}
