package com.jinwan.appproject.decorator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.jinwan.appproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class TodayDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private final CalendarDay date;

    public TodayDecorator(Context context) {
        // drawable 리소스를 가져옵니다.
        drawable = ContextCompat.getDrawable(context, R.drawable.calendar_circle_gray);
        date = CalendarDay.today(); // 오늘 날짜를 가져옵니다.
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // 지정된 날짜가 오늘 날짜와 같은지 확인합니다.
        return day != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // 오늘 날짜에 대해 drawable을 설정합니다.
        if (view != null && drawable != null) {
            view.setBackgroundDrawable(drawable);
        }
    }
}
