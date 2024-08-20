package com.jinwan.appproject.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;
import com.jinwan.appproject.adapter.CelebrityAdapter;
import com.jinwan.appproject.adapter.ScheduleAdapter;
import com.jinwan.appproject.list.Celebrity;
import com.jinwan.appproject.list.Schedule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// 달력 클릭 시 나오는 일정 추가 다이얼로그로 해당 코드에서 setPositive 및 db 설정 해야할 것으로 생각
public class ScheduleDialog {

    private final Context context;
    private final ScheduleAdapter scheduleAdapter;
    private final List<Schedule> scheduleList;
    private final CelebrityAdapter celebrityAdapter;
    private final List<Celebrity> celebrityList;
    private final long selectedDate;
    String dateString;

    public ScheduleDialog(Context context, ScheduleAdapter scheduleAdapter, List<Schedule> scheduleList, CelebrityAdapter celebrityAdapter, List<Celebrity> celebrityList, long selectedDate) {
        this.context = context;
        this.scheduleAdapter = scheduleAdapter;
        this.scheduleList = scheduleList;
        this.celebrityAdapter = celebrityAdapter;
        this.celebrityList = celebrityList;
        this.selectedDate = selectedDate;
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = sdf.format(selectedDate);
        builder.setTitle(dateString);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_add, null);
        builder.setView(dialogView);

        // Schedule layout
        LinearLayout layout_schedule = dialogView.findViewById(R.id.layout_add_schedule);
        layout_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddScheduleDialog(context,scheduleAdapter,scheduleList,selectedDate).show();

            }
        });

        // Celebrity layout
        LinearLayout layout_celebrity = dialogView.findViewById(R.id.layout_add_celebrity);
        layout_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCelebrityDialog(context,celebrityAdapter,celebrityList).show();
            }
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
