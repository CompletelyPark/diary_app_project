package com.jinwan.appproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.adapter.CelebrityAdapter;
import com.jinwan.appproject.adapter.ScheduleAdapter;
import com.jinwan.appproject.decorator.CustomCalendarDecorator;
import com.jinwan.appproject.decorator.TodayDecorator;
import com.jinwan.appproject.decorator.SaturdayDecorator;
import com.jinwan.appproject.dialog.ScheduleDialog;
import com.jinwan.appproject.decorator.SundayDecorator;


import com.jinwan.appproject.list.Celebrity;
import com.jinwan.appproject.list.Schedule;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView materialCalendarView;

    private List<Schedule> scheduleList;
    private List<Celebrity> celebrityList;
    private ScheduleAdapter scheduleAdapter;
    private CelebrityAdapter celebrityAdapter;
    private long selectedDate; // 선택한 날짜를 저장할 변수

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        materialCalendarView = view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//      Recyclerview 에 여러개의 Adapter 를 설정하는 것은 불가능하다


        // Calendar date selection listener
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() - 1;
                int dayOfMonth = date.getDay();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedDate = calendar.getTimeInMillis();

                // Show schedule dialog
                new ScheduleDialog(getContext(),scheduleAdapter,scheduleList,celebrityAdapter,celebrityList, selectedDate).show();
            }
        });

        SundayDecorator sundayDecorator = new SundayDecorator();
        SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
        TodayDecorator todayDecorator = new TodayDecorator(getContext());

        CustomCalendarDecorator decorator = new CustomCalendarDecorator(getContext());
        materialCalendarView.addDecorator(decorator);

        materialCalendarView.addDecorator(saturdayDecorator);
        materialCalendarView.addDecorator(sundayDecorator);
        materialCalendarView.addDecorator(todayDecorator);

        return view;
    }
}
