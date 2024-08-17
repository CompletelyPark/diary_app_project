package com.jinwan.appproject.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.jinwan.appproject.schedule.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.schedule.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// 일정 추가 클릭 시 나오는 다이얼로그
public class AddScheduleDialog {

    private final Context context;
    private final List<Schedule> scheduleList;
    private final ScheduleAdapter scheduleAdapter;
    private final long selectedDate;

    public AddScheduleDialog(Context context, List<Schedule> scheduleList, ScheduleAdapter scheduleAdapter, long selectedDate) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.scheduleAdapter = scheduleAdapter;
        this.selectedDate = selectedDate;
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.add_schedule, null);

        EditText schedule_name = dialogView.findViewById(R.id.schedule_name);
        EditText schedule_memo = dialogView.findViewById(R.id.schedule_memo);
        TextView dayfirst = dialogView.findViewById(R.id.dayfirst);
        TextView daylast = dialogView.findViewById(R.id.daylast);
        TextView timefirst = dialogView.findViewById(R.id.timefirst);
        TextView timelast = dialogView.findViewById(R.id.timelast);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = sdf.format(selectedDate);
        dayfirst.setText(dateString);
        daylast.setText(dateString);

        dayfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setSelection(selectedDate).build();
                materialDatePicker.show(((FragmentActivity) context).getSupportFragmentManager(), "Date_picker");

                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    String dateString = sdf.format(new Date((Long) selection)); // Long으로 캐스팅
                    dayfirst.setText(dateString);
                });
            }
        });

        daylast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setSelection(selectedDate).build();
                materialDatePicker.show(((FragmentActivity) context).getSupportFragmentManager(), "Date_picker");

                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    String dateString = sdf.format(new Date((Long) selection));
                    daylast.setText(dateString);
                });
            }
        });

        timefirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Time")
                        .build();

                timePicker.addOnPositiveButtonClickListener(dialog -> {
                    String amPm = (timePicker.getHour() >= 12) ? "PM" : "AM";
                    int formattedHour = (timePicker.getHour() % 12 == 0) ? 12 : timePicker.getHour() % 12;
                    String selectedTime = String.format("%s %02d:%02d", amPm, formattedHour, timePicker.getMinute());
                    timefirst.setText(selectedTime);
                });

                timePicker.show(((FragmentActivity) context).getSupportFragmentManager(), "Time_picker");
            }
        });

        timelast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Time")
                        .build();

                timePicker.addOnPositiveButtonClickListener(dialog -> {
                    String amPm = (timePicker.getHour() >= 12) ? "PM" : "AM";
                    int formattedHour = (timePicker.getHour() % 12 == 0) ? 12 : timePicker.getHour() % 12;
                    String selectedTime = String.format("%s %02d:%02d", amPm, formattedHour, timePicker.getMinute());
                    timelast.setText(selectedTime);
                });

                timePicker.show(((FragmentActivity) context).getSupportFragmentManager(), "Time_picker");
            }
        });

        builder.setTitle("일정 추가");
        builder.setView(dialogView);
        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            String str_schedule_name = schedule_name.getText().toString();
            String str_schedule_memo = schedule_memo.getText().toString();
            String str_dayfirst = dayfirst.getText().toString();
            String str_daylast = daylast.getText().toString();
            String str_timefirst = timefirst.getText().toString();
            String str_timelast = timelast.getText().toString();

            Schedule newSchedule = new Schedule(str_schedule_name, str_schedule_memo, str_dayfirst, str_daylast, str_timefirst, str_timelast);
            scheduleList.add(newSchedule);
            scheduleAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
