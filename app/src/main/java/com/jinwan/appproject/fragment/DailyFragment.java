package com.jinwan.appproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.jinwan.appproject.R;
import com.jinwan.appproject.schedule.Schedule2;
import com.jinwan.appproject.schedule.ScheduleAdapter2;

import java.util.ArrayList;
import java.util.List;

public class DailyFragment extends Fragment {

    private List<Schedule2> scheduleList2; // 스케줄 리스트
    private ScheduleAdapter2 scheduleAdapter2; // 어댑터

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_mission, container, false);

        // FloatingActionButton 클릭 리스너 설정
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(v -> show());

        // RecyclerView 초기화
        scheduleList2 = new ArrayList<>();
        scheduleAdapter2 = new ScheduleAdapter2(scheduleList2);

        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(scheduleAdapter2);

        return view;
    }

    public void show() {

        // Dialog 생성
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_schedule2, null);
        builder.setTitle("일정 추가");
        builder.setView(dialogView);

        TextView schedule_memo2 = dialogView.findViewById(R.id.schedule_memo2);
        TextView timefirst2 = dialogView.findViewById(R.id.timefirst2);
        TextView timelast2 = dialogView.findViewById(R.id.timelast2);

        timefirst2.setOnClickListener(v -> showTimePicker(timefirst2));
        timelast2.setOnClickListener(v -> showTimePicker(timelast2));

        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            String str_schedule_memo2 = schedule_memo2.getText().toString();
            String str_timefirst2 = timefirst2.getText().toString();
            String str_timelast2 = timelast2.getText().toString();

            Schedule2 newSchedule2 = new Schedule2(str_schedule_memo2, str_timefirst2, str_timelast2);
            scheduleList2.add(newSchedule2);
            scheduleAdapter2.notifyDataSetChanged(); // RecyclerView 업데이트

        });

        builder.setNegativeButton("취소", null); // Dialog의 Cancel 버튼 추가
        builder.show(); // Dialog 표시
    }

    private void showTimePicker(TextView textView) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("시간 선택")
                .build();

        timePicker.addOnPositiveButtonClickListener(dialog -> {
            String amPm = (timePicker.getHour() >= 12) ? "PM" : "AM";
            int formattedHour = (timePicker.getHour() % 12 == 0) ? 12 : timePicker.getHour() % 12;
            String selectedTime = String.format("%s %02d:%02d", amPm, formattedHour, timePicker.getMinute());
            textView.setText(selectedTime);
        });

        timePicker.show(getParentFragmentManager(), "Time_picker"); // FragmentManager 수정

    }
}
