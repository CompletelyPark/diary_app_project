package com.jinwan.appproject.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.jinwan.appproject.Data.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.recycler.ScheduleAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment{

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private Button toggleCalendar;
    private ImageButton btn_plus;

    FragmentManager fragmentManager = getParentFragmentManager();

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
        btn_plus =view.findViewById(R.id.btn_plus);
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

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerdialog();


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

    private void DatePickerdialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            Long startDate = selection.first;
            Long endDate = selection.second;

            // Formating the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            // Creating the date range string
            String selectedDateRange = startDateString + " - " + endDateString;

        });

        // Showing the date picker dialog
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

}