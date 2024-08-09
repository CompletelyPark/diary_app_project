package com.jinwan.appproject.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;

import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

//  달력을 표시할 날짜 리스트
    private List<Calendar> calendarDays;
//  날짜 클릭 시 호출 될 콜백 인터페이스
    private OnDateClickListener onDateClickListener;

//  CalendarAdapter constructor
    public CalendarAdapter(List<Calendar> calendarDays, OnDateClickListener onDateClickListener) {
        this.calendarDays = calendarDays;
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//      calendar_day_item.xml layout을 inflate 해서 ViewHolder 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_item, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
//      형재 위치에 해당하는 날짜 데이터 가져온다
        Calendar day = calendarDays.get(position);
//      ViewHolder 에 날짜 데이터 바인딩
        holder.bind(day);
//      날짜 아이템 클릭 시 callback call
        holder.itemView.setOnClickListener(v -> onDateClickListener.onDateClick(day));
    }

    @Override
//  리스트 요소 개수 반환
    public int getItemCount() {
        return calendarDays.size();
    }

//  ViewHolder class
    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textDay;

        CalendarViewHolder(View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.textDay);
        }

//      날짜 데이터를 ViewHolder 에 바인딩 하는 method
        void bind(Calendar day)
        {
            textDay.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        }
    }

//  날짜 클릭 시 호출 될 인터페이스
    public interface OnDateClickListener {
        void onDateClick(Calendar date);
    }
}
