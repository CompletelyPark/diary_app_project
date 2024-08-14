package com.jinwan.appproject.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.jinwan.appproject.Data.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.recycler.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment
{
    private CalendarView calendarView;
    private Button toggleCalendar;
    private ScheduleAdapter scheduleAdapter;
    private List<Schedule> scheduleList;

    private boolean istf = true;
    private long selectedDate; // 선택한 날짜를 저장할 변수
    String selectedTime;
    String dateString;

    EditText schedule_name, schedule_memo;
    TextView dayfirst, daylast;
    TextView timelast, timefirst;

    String str_schedule_name, str_schedule_memo;
    String str_dayfirst, str_daylast;
    String str_timefirst, str_timelast;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize schedule list
        scheduleList = new ArrayList<>();

        // Initialize adapter and set it to RecyclerView
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scheduleAdapter);

//      calendar toggle button (open, close)
        toggleCalendar = view.findViewById(R.id.toggleCalendar);
        toggleCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (istf)
                {
                    calendarView.setVisibility(View.VISIBLE);
                    istf = false;
                    Log.d("Tag", "open");
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_upward_24px));
                }
                else
                {
                    calendarView.setVisibility(View.GONE);
                    Log.d("Tag", "close");
                    istf = true;
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_downward_24px));
                }
            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 일정 추가 다이얼로그 띄우기
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                selectedDate = calendar.getTimeInMillis();
                showDialog();
            }
        });

        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);

        return view;
    }


    private void showDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(calendarView.getContext());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = sdf.format(selectedDate);
        builder.setTitle(dateString);

        View dialogView = getLayoutInflater().inflate(R.layout.schedule_add_dialog, null);
        builder.setView(dialogView);

//      일정 추가 dialog
        LinearLayout layout_schedule = dialogView.findViewById(R.id.layout_add_schedule);
        layout_schedule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Tag", "layout_schedule_clicked");
                View dialogView1 = getLayoutInflater().inflate(R.layout.add_schedule, null);
                dateString = sdf.format(selectedDate);

                schedule_name = dialogView1.findViewById(R.id.schedule_name);
                schedule_memo = dialogView1.findViewById(R.id.schedule_memo);

//              dayfirst
                dayfirst = dialogView1.findViewById(R.id.dayfirst);
                dayfirst.setText(dateString);
                dayfirst.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(selectedDate).build();
                        materialDatePicker.show(getActivity().getSupportFragmentManager(), "Date_picker");

                        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>()
                        {
                            @Override
                            public void onPositiveButtonClick(Long selection)
                            {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                date.setTime(selection);
                                dateString = simpleDateFormat.format(date);
                                dayfirst.setText(dateString);
                            }
                        });
                    }
                });
                str_dayfirst = dateString;
//              daylast
                daylast = dialogView1.findViewById(R.id.daylast);
                daylast.setText(dateString);
                daylast.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(selectedDate).build();
                        materialDatePicker.show(getActivity().getSupportFragmentManager(), "Date_picker");

                        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>()
                        {
                            @Override
                            public void onPositiveButtonClick(Long selection) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                date.setTime(selection);
                                dateString = simpleDateFormat.format(date);
                                daylast.setText(dateString);
                            }
                        });
                    }
                });
                str_daylast = dateString;

//              timefirst
                timefirst = dialogView1.findViewById(R.id.timefirst);
                timefirst.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_12H) // 12시간 형식 (AM/PM)
                                .setHour(12) // 초기 시간 설정 (예: 12시)
                                .setMinute(0) // 초기 분 설정 (예: 0분)
                                .setTitleText("Select Time") // 타이틀 설정
                                .build();

                        // 확인 버튼 클릭 시 호출될 리스너 설정
                        timePicker.addOnPositiveButtonClickListener(dialog ->
                        {
                            // 선택된 시간 가져오기
                            int selectedHour = timePicker.getHour();
                            int selectedMinute = timePicker.getMinute();
                            // AM/PM 값 확인
                            String amPm = (selectedHour >= 12) ? "PM" : "AM";
                            // 12시간 형식으로 시간 조정
                            int formattedHour = (selectedHour % 12 == 0) ? 12 : selectedHour % 12;
                            // 선택된 시간과 AM/PM 출력
                            selectedTime = String.format("%s %02d:%02d",amPm, formattedHour, selectedMinute);
                            timefirst.setText(selectedTime);
                        });
                        // 타임피커 표시
                        timePicker.show(getActivity().getSupportFragmentManager(), "tag");
                    }
                });
                str_timefirst = (String) timefirst.getText();

//              timelast
                timelast = dialogView1.findViewById(R.id.timelast);
                timelast.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_12H)
                                .setHour(12)
                                .setMinute(0)
                                .setTitleText("Select Time")
                                .build();

                        timePicker.addOnPositiveButtonClickListener(dialog ->
                        {
                            int selectedHour = timePicker.getHour();
                            int selectedMinute = timePicker.getMinute();
                            String amPm = (selectedHour >= 12) ? "PM" : "AM";
                            int formattedHour = (selectedHour % 12 == 0) ? 12 : selectedHour % 12;
                            selectedTime = String.format("%s %02d:%02d",amPm, formattedHour, selectedMinute);
//                            Log.d("jtag",str_timelast);
                            timelast.setText(selectedTime);
                        });

                        timePicker.show(getActivity().getSupportFragmentManager(), "tag");
                    }
                });
                str_timelast = (String) timelast.getText();


                builder.setTitle("일정 추가");
                builder.setView(dialogView1);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        str_schedule_name = String.valueOf(schedule_name.getText());
                        str_schedule_memo = String.valueOf(schedule_memo.getText());

                        Log.d("jtag","schedule name:"+ str_schedule_name +
                                " schedule memo:"+ str_schedule_memo + "\n" +
                                "day first:"+ str_dayfirst +
                                " day last:"+ str_daylast + "\n" +
                                "time first:"+ str_timefirst +
                                " time last:"+ str_timelast );

                        Schedule newSchedule = new Schedule(str_schedule_name, str_schedule_memo, str_dayfirst, str_daylast, str_timefirst, str_timelast);

                        scheduleList.add(newSchedule);
                        scheduleAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

//      기념일 추가 dialog
        LinearLayout layout_celebrity = dialogView.findViewById(R.id.layout_add_celebrity);
        layout_celebrity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Tag", "layout_celebrity_clicked");
                View dialogView2 = getLayoutInflater().inflate(R.layout.add_celebrity, null);
                builder.setTitle("기념일 추가");
                builder.setView(dialogView2);
                builder.setNeutralButton("기념일 삭제",null);
                builder.setPositiveButton("확인",null);
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

}