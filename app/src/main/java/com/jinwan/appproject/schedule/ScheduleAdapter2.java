package com.jinwan.appproject.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;

import java.util.List;

public class ScheduleAdapter2 extends RecyclerView.Adapter<ScheduleAdapter2.ScheduleViewHolder> {
    private List<Schedule2> scheduleList2;

    public ScheduleAdapter2(List<Schedule2> scheduleList) {
        this.scheduleList2 = scheduleList;
    }

    @NonNull @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule2, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule2 schedule = scheduleList2.get(position);
        holder.memo2.setText(schedule.getMemo2());
        holder.timeFirst2.setText(schedule.getTimeFirst2());
        holder.timeLast2.setText(schedule.getTimeLast2());
    }

    @Override
    public int getItemCount() {
        return scheduleList2.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView memo2, timeFirst2, timeLast2;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            memo2 = itemView.findViewById(R.id.schedule_memo2);
            timeFirst2 = itemView.findViewById(R.id.timefirst2);
            timeLast2 = itemView.findViewById(R.id.timelast2);
        }
    }
}
