package com.jinwan.appproject.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.Data.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.helper.DateUtils;
import com.jinwan.appproject.recycler.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private Button toggleCalendar;

    //  데이터
    private List<Schedule> scheduleList;

    //  recyclerview adapter
    private ScheduleAdapter scheduleAdapter;

    private boolean istf = true;
    private long selectedDate; // 선택한 날짜를 저장할 변수
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerView);
        toggleCalendar = view.findViewById(R.id.toggleCalendar);
        toggleCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (istf) {
                    calendarView.setVisibility(View.VISIBLE);
                    istf = false;
                    Log.d("Tag", "open");
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_upward_24px));
                } else {
                    calendarView.setVisibility(View.GONE);
                    Log.d("Tag", "close");
                    istf = true;
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_downward_24px));
                }
            }
        });

        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 일정 추가 다이얼로그 띄우기
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                selectedDate = calendar.getTimeInMillis();

                showDialog();
            }
        });
        return view;
    }


    private void showDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(calendarView.getContext());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = sdf.format(selectedDate);
        builder.setTitle(dateString);

        View dialogView = getLayoutInflater().inflate(R.layout.schedule_add_dialog, null);
        builder.setView(dialogView);


        LinearLayout layout_schedule = dialogView.findViewById(R.id.layout_add_schedule);
        layout_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", "layout_schedule_clicked");
                View dialogView1 = getLayoutInflater().inflate(R.layout.add_schedule, null);
                builder.setTitle("일정 추가");
                builder.setView(dialogView1);
                builder.setPositiveButton("확인",null);
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        LinearLayout layout_celebrity = dialogView.findViewById(R.id.layout_add_celebrity);
        layout_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", "layout_celebrity_clicked");
                View dialogView2 = getLayoutInflater().inflate(R.layout.add_celebrity, null);
                builder.setTitle("기념일 추가");
                builder.setView(dialogView2);
                builder.setNeutralButton("기념일 삭제",null);
                builder.setPositiveButton("확인",null);
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

}