package com.jinwan.appproject.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jinwan.appproject.helper.*;

public class CalendarFragment extends Fragment {

    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//      Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

//      RecyclerView를 찾고 layout manger set
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));

//      날짜 데이터 준비
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

//      빈 셀 추가 - 달력 시작 요일 맞추기
        for(int i =0;i<firstDayOfWeek;i++) days.add("");

//      날짜 추가
        for(int i =1;i<=maxDay;i++) days.add(String.valueOf(i));

        calendarAdapter = new CalendarAdapter(days);
        calendarRecyclerView.setAdapter(calendarAdapter);

        return view;
    }
}
