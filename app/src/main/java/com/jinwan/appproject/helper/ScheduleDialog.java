package com.jinwan.appproject.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;
import com.jinwan.appproject.schedule.ScheduleAdapter;
import com.jinwan.appproject.schedule.Schedule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// 달력 클릭 시 나오는 일정 추가 다이얼로그로 해당 코드에서 setPositive 및 db 설정 해야할 것으로 생각
public class ScheduleDialog {

    private final Context context;
    private final List<Schedule> scheduleList;
    private final ScheduleAdapter scheduleAdapter;
    private final long selectedDate;
    String dateString;
    public ScheduleDialog(Context context, List<Schedule> scheduleList, ScheduleAdapter scheduleAdapter, long selectedDate) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.scheduleAdapter = scheduleAdapter;
        this.selectedDate = selectedDate;
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = sdf.format(selectedDate);
        builder.setTitle(dateString);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.schedule_add_dialog, null);
        builder.setView(dialogView);

        // Schedule layout
        LinearLayout layout_schedule = dialogView.findViewById(R.id.layout_add_schedule);
        layout_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddScheduleDialog(context, scheduleList, scheduleAdapter, selectedDate).show();
            }
        });

        // Celebrity layout
        LinearLayout layout_celebrity = dialogView.findViewById(R.id.layout_add_celebrity);
        layout_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCelebrityDialog(context).show();
            }
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
