package com.jinwan.appproject.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.Data.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.recycler.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private Button toggleCalendar;

//  데이터
    private List<Schedule> scheduleList;

//  recyclerview adapter
    private ScheduleAdapter scheduleAdapter;

    private boolean istf = true;

    @Nullable @Override
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
                }
                else {
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
                showScheduleDialog(year, month, dayOfMonth);
            }
        });

        return  view;
    }

    //  일정 추가 method
    private void showScheduleDialog(int year, int month, int day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("일정 추가");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);
        builder.setView(dialogView);

        EditText titleEditText = dialogView.findViewById(R.id.schedule_edit);
        EditText timeEditText = dialogView.findViewById(R.id.timeEditText);

        builder.setPositiveButton("추가", (dialog, which) -> {
            String title = titleEditText.getText().toString();
            String time = timeEditText.getText().toString();
            scheduleList.add(new Schedule(title, String.format("%d-%02d-%02d", year, month + 1, day), time));
            scheduleAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd){

    }
    public void onTimeSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd){
        String hourString = hourO
    }

}