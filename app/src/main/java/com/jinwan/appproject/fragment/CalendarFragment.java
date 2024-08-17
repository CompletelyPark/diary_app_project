package com.jinwan.appproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.schedule.Schedule;
import com.jinwan.appproject.helper.SaturdayDecorator;
import com.jinwan.appproject.helper.ScheduleDialog;
import com.jinwan.appproject.helper.SundayDecorator;
import com.jinwan.appproject.schedule.ScheduleAdapter;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView materialCalendarView;
    private Button toggleCalendar;
    private ScheduleAdapter scheduleAdapter;
    private List<Schedule> scheduleList;

    private boolean istf = true;
    private long selectedDate; // 선택한 날짜를 저장할 변수

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        materialCalendarView = view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        // Initialize schedule list
        scheduleList = new ArrayList<>();

        // Initialize adapter and set it to RecyclerView
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scheduleAdapter);

        // Calendar toggle button (open, close)
        toggleCalendar = view.findViewById(R.id.toggleCalendar);
        toggleCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (istf) {
                    materialCalendarView.setVisibility(View.VISIBLE);
                    istf = false;
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_upward_24px));
                } else {
                    materialCalendarView.setVisibility(View.GONE);
                    istf = true;
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_downward_24px));
                }
            }
        });

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
                new ScheduleDialog(getContext(), scheduleList, scheduleAdapter, selectedDate).show();
            }
        });

        SundayDecorator sundayDecorator = new SundayDecorator();
        SaturdayDecorator saturdayDecorator = new SaturdayDecorator();

        materialCalendarView.addDecorator(saturdayDecorator);
        materialCalendarView.addDecorator(sundayDecorator);

        return view;
    }
}
