package com.jinwan.appproject.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView materialCalendarView;

    private List<Schedule> scheduleList;
    private List<Celebrity> celebrityList;
    private ScheduleAdapter scheduleAdapter;
    private CelebrityAdapter celebrityAdapter;
    private long selectedDate; // 선택한 날짜를 저장할 변수

    boolean isopen= false;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        materialCalendarView = view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        scheduleList = new ArrayList<>();
        celebrityList = new ArrayList<>();

        scheduleAdapter = new ScheduleAdapter(scheduleList);
        celebrityAdapter = new CelebrityAdapter(celebrityList);
//      하나의 Recyclerview 에 여러개의 Adapter 를 설정하는 것은 불가능하다
//      방법 1. adapter를 합쳐서 하나의 adapter에서 관리한다 - 실패
//      방법 2. recyclerview를 원하는 개수만큼 만들어서 관리한다
//                -  실력 부족으로 일단 이렇게 해서 visibility로 관리한다
        RecyclerView recyclerView_schedule = view.findViewById(R.id.recyclerView_schedule);
        recyclerView_schedule.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView recyclerView_celebrity = view.findViewById(R.id.recyclerView_celebrity);
        recyclerView_celebrity.setLayoutManager(new LinearLayoutManager(getContext()));





        MaterialButton btn_open_close = (MaterialButton) view.findViewById(R.id.btn_open_close);
        MaterialButton btn_schedule = (MaterialButton) view.findViewById(R.id.btn_schedule);
        MaterialButton btn_celebrity = (MaterialButton) view.findViewById(R.id.btn_celebrity);

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_schedule.setVisibility(View.VISIBLE);
                recyclerView_celebrity.setVisibility(View.GONE);
            }
        });

        btn_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_schedule.setVisibility(View.GONE);
                recyclerView_celebrity.setVisibility(View.VISIBLE);
            }
        });

//        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) view.findViewById(R.id.toggleButton);
        btn_open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isopen) {
//                    btn_open_close.set
                    Log.d("Tag","open");
                    Drawable icon = getResources().getDrawable(R.drawable.arrow_upward_24px);
                    btn_open_close.setIcon(icon);
                    materialCalendarView.setVisibility(View.VISIBLE);
//                    materialButtonToggleGroup.setVisibility(View.INVISIBLE);
                    btn_schedule.setVisibility(View.INVISIBLE);
                    btn_celebrity.setVisibility(View.INVISIBLE);
                    recyclerView_schedule.setVisibility(View.INVISIBLE);
                    recyclerView_celebrity.setVisibility(View.INVISIBLE);

                }
                else{
                    Log.d("Tag","close");
                    Drawable icon = getResources().getDrawable(R.drawable.arrow_downward_24px);
                    btn_open_close.setIcon(icon);
                    materialCalendarView.setVisibility(View.GONE);
//                    materialButtonToggleGroup.setVisibility(View.VISIBLE);
                    btn_schedule.setVisibility(View.VISIBLE);
                    btn_celebrity.setVisibility(View.VISIBLE);
                }
                isopen = !isopen;
            }
        });

//        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
//            @Override
//            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
//
//                if(checkedId == R.id.btn_schedule){
//                     recyclerView_schedule.setVisibility(View.VISIBLE);
//                     recyclerView_celebrity.setVisibility(View.GONE);
//                }
//                else if(checkedId == R.id.btn_celebrity){
//                    recyclerView_schedule.setVisibility(View.GONE);
//                    recyclerView_celebrity.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });

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

        recyclerView_schedule.setAdapter(scheduleAdapter);
        recyclerView_celebrity.setAdapter(celebrityAdapter);

        return view;
    }
}
