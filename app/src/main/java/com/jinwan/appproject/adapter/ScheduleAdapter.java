package com.jinwan.appproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.list.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<Schedule> scheduleList;

    public ScheduleAdapter(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.name.setText(schedule.getName());
        holder.memo.setText(schedule.getMemo());
        holder.dayFirst.setText(schedule.getDayFirst());
        holder.dayLast.setText(schedule.getDayLast());
        holder.timeFirst.setText(schedule.getTimeFirst());
        holder.timeLast.setText(schedule.getTimeLast());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView name, memo, dayFirst, dayLast, timeFirst, timeLast;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.schedule_name);
            memo = itemView.findViewById(R.id.schedule_memo);
            dayFirst = itemView.findViewById(R.id.dayfirst);
            dayLast = itemView.findViewById(R.id.daylast);
            timeFirst = itemView.findViewById(R.id.timefirst);
            timeLast = itemView.findViewById(R.id.timelast);
        }
    }
}
