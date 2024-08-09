package com.jinwan.appproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.recycler.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment {

    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//      Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar,container,false);
        
        recyclerView = view.findViewById(R.id.RecyclerView);
        
//      7열로 구성된 grid layout set (요일별)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));
        
//      현재 월의 날짜 리스트 가져오기
        List<Calendar> daysInMonth = getDaysInMonth(Calendar.getInstance());
        
//      calendarAdapter 초기화
        calendarAdapter = new CalendarAdapter(daysInMonth,this::onDateClick);

//      RecyclerView 에 adapter set
        recyclerView.setAdapter(calendarAdapter);
        
        return view;
    }
    
//  현재 월의 모든 날짜를 리스트로 가져오는 method
    private List<Calendar> getDaysInMonth(Calendar month){
        List<Calendar> days = new ArrayList<>();
        
//      월의 첫 날로 set
        month.set(Calendar.DAY_OF_MONTH,1);
//        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = month.getActualMaximum(Calendar.DAY_OF_MONTH);


//        for (int i = 0; i < firstDayOfWeek; i++) days.add("");
        for(int i = 0;i<daysInMonth;i++){
            Calendar day = (Calendar) month.clone();
            day.add(Calendar.DAY_OF_MONTH,i);
            days.add(day);
        }
        return days;
    }
    
//  날짜 클릭 시 호출 될 method
    private void onDateClick(Calendar date){
//      dialog 를 띄우거나 작업 수행 코드
    }
    
}
