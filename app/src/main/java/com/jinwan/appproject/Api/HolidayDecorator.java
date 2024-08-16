package com.jinwan.appproject.Api;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;
import java.util.Set;

public class HolidayDecorator implements DayViewDecorator {

    private final Set<CalendarDay> holidayDays;

    public HolidayDecorator(Set<CalendarDay> holidayDays) {
        this.holidayDays = holidayDays;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return holidayDays.contains(day);
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        // 공휴일의 배경색을 변경합니다.
        view.setBackgroundDrawable(new ColorDrawable(Color.RED)); // 원하는 색상으로 변경
    }
}
