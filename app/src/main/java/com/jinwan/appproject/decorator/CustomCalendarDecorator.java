package com.jinwan.appproject.decorator;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CustomCalendarDecorator implements DayViewDecorator {
    private final int weekdayTextColor;

    public CustomCalendarDecorator(Context context) {
        // 색상 속성 가져오기
        int backgroundColor = getColorFromAttr(context, androidx.appcompat.R.attr.colorPrimary);
        weekdayTextColor = getColorFromAttr(context, com.google.android.material.R.attr.colorOnPrimary);
    }

    private int getColorFromAttr(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // 모든 날짜를 꾸미기 위해 true를 반환
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
    }

}
