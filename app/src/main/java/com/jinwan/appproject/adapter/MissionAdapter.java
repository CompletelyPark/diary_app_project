package com.jinwan.appproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.list.Mission;

import java.util.List;

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.ScheduleViewHolder> {
    private List<Mission> mission;

    public MissionAdapter(List<Mission> mission) {
        this.mission = mission;
    }

    @NonNull @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_mission, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Mission schedule = mission.get(position);
        holder.memo2.setText(schedule.getMemo2());
        holder.timeFirst2.setText(schedule.getTimeFirst2());
        holder.timeLast2.setText(schedule.getTimeLast2());
    }

    @Override
    public int getItemCount() {
        return mission.size();
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
