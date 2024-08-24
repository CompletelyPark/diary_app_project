package com.jinwan.appproject.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.jinwan.appproject.R;
import com.jinwan.appproject.adapter.CelebrityAdapter;
import com.jinwan.appproject.adapter.RecyclerItemClickListener;
import com.jinwan.appproject.adapter.ScheduleAdapter;
import com.jinwan.appproject.database.CelebrityDatabaseHelper;
import com.jinwan.appproject.database.ScheduleDatabaseHelper;
import com.jinwan.appproject.decorator.CustomCalendarDecorator;
import com.jinwan.appproject.decorator.TodayDecorator;
import com.jinwan.appproject.decorator.SaturdayDecorator;
import com.jinwan.appproject.decorator.SundayDecorator;


import com.jinwan.appproject.helper.DateHelper;
import com.jinwan.appproject.list.Celebrity;
import com.jinwan.appproject.list.Schedule;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private MaterialCalendarView materialCalendarView;

    private List<Schedule> scheduleList;
    private List<Celebrity> celebrityList;
    private ScheduleAdapter scheduleAdapter;
    private CelebrityAdapter celebrityAdapter;
    private long selectedDate; // 선택한 날짜를 저장할 변수
    private Calendar calendar_selectedDate; // 선택된 날짜

    private Calendar todayDate;
    private ScheduleDatabaseHelper scheduleDatabaseHelper;
    private CelebrityDatabaseHelper celebrityDatabaseHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String dateString;
    boolean isopen= false;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        materialCalendarView = view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        calendar_selectedDate = Calendar.getInstance();

//      하나의 Recyclerview 에 여러개의 Adapter 를 설정하는 것은 불가능하다
//      방법 1. adapter를 합쳐서 하나의 adapter에서 관리한다 - 실패
//      방법 2. recyclerview를 원하는 개수만큼 만들어서 관리한다
//                -  실력 부족으로 일단 이렇게 해서 visibility로 관리한다

        scheduleDatabaseHelper = new ScheduleDatabaseHelper(getContext());
        scheduleList = scheduleDatabaseHelper.getSchedulesForToday();
        scheduleAdapter = new ScheduleAdapter(scheduleList);

        RecyclerView recyclerView_schedule = view.findViewById(R.id.recyclerView_schedule);
        recyclerView_schedule.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_schedule.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView_schedule, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                  showEditDialogSchedule(scheduleList.get(position));
                  Log.d("Tag","schedule_clicked");
            }

            @Override
            public void onLongItemClick(View view, int position) {
                  showDeleteDialogSchedule(scheduleList.get(position));
            }
        }));

        celebrityDatabaseHelper = new CelebrityDatabaseHelper(getContext());
        celebrityList = celebrityDatabaseHelper.getCelebritiesForTodayAndTomorrow();
        celebrityAdapter = new CelebrityAdapter(celebrityList);

        RecyclerView recyclerView_celebrity = view.findViewById(R.id.recyclerView_celebrity);
        recyclerView_celebrity.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_celebrity.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView_celebrity, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                  showEditDialogCelebrity(celebrityList.get(position));
                Log.d("Tag","celebrity_clicked");

            }

            @Override
            public void onLongItemClick(View view, int position) {
                  showDeleteDialogCelebrity(celebrityList.get(position));
            }
        }));

        MaterialButton btn_schedule = (MaterialButton) view.findViewById(R.id.btn_schedule);
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_schedule.setVisibility(View.VISIBLE);
                recyclerView_celebrity.setVisibility(View.GONE);
            }
        });
        MaterialButton btn_celebrity = (MaterialButton) view.findViewById(R.id.btn_celebrity);
        btn_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView_schedule.setVisibility(View.GONE);
                recyclerView_celebrity.setVisibility(View.VISIBLE);
            }
        });
        MaterialButton btn_open_close = (MaterialButton) view.findViewById(R.id.btn_open_close);
        btn_open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isopen) {
//                    btn_open_close.set
                    Log.d("Tag","open");
                    Drawable icon = getResources().getDrawable(R.drawable.arrow_upward_24px);
                    btn_open_close.setIcon(icon);
                    materialCalendarView.setVisibility(View.VISIBLE);
//                    materialButtonToggleGroup.setVisibility(View.INVISIBLE);
                    btn_schedule.setVisibility(View.INVISIBLE);
                    btn_celebrity.setVisibility(View.INVISIBLE);
                    recyclerView_schedule.setVisibility(View.INVISIBLE);
                    recyclerView_celebrity.setVisibility(View.INVISIBLE);

                }
                else{
                    Log.d("Tag","close");
                    Drawable icon = getResources().getDrawable(R.drawable.arrow_downward_24px);
                    btn_open_close.setIcon(icon);
                    materialCalendarView.setVisibility(View.GONE);
//                    materialButtonToggleGroup.setVisibility(View.VISIBLE);
                    btn_schedule.setVisibility(View.VISIBLE);
                    btn_celebrity.setVisibility(View.VISIBLE);
                }
                isopen = !isopen;
            }
        });


        // 캘린더 뷰 클릭 시
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() - 1;
                int dayOfMonth = date.getDay();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedDate = calendar.getTimeInMillis();

                // 스케줄 다이얼로그 생성
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());

                dateString = sdf.format(selectedDate);
                builder.setTitle(dateString);

//              일정 및 기념일 추가하는 다이얼로그 생성
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_add, null);
                builder.setView(dialogView);

                // 스케줄 다이얼로그
                LinearLayout layout_schedule = dialogView.findViewById(R.id.layout_add_schedule);
                layout_schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_schedule, null);

                        EditText schedule_name = dialogView.findViewById(R.id.schedule_name);
                        EditText schedule_memo = dialogView.findViewById(R.id.schedule_memo);
                        TextView dayfirst = dialogView.findViewById(R.id.dayfirst);
                        TextView daylast = dialogView.findViewById(R.id.daylast);
                        TextView timefirst = dialogView.findViewById(R.id.timefirst);
                        TextView timelast = dialogView.findViewById(R.id.timelast);

//                      시작 날짜 선택시
                        dayfirst.setText(dateString);
                        dayfirst.setOnClickListener(V->showDatePicker(dayfirst));
//                      마지막 날짜 선택시
                        daylast.setText(dateString);
                        daylast.setOnClickListener(V->showDatePicker(daylast));
//                      시작 시간 클릭 시
                        timefirst.setOnClickListener(V->showTimePicker(timefirst));
//                      마지막 시간 클릭 시
                        timelast.setOnClickListener(V->showTimePicker(timelast));


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

                            scheduleDatabaseHelper.addSchedule(newSchedule,getFormattedDate(calendar_selectedDate));
                        });
                        builder.setNegativeButton("취소", null);
                        builder.show();

                    }
                });

                // 기념일 dialog
                LinearLayout layout_celebrity = dialogView.findViewById(R.id.layout_add_celebrity);
                layout_celebrity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_celebrity, null);
                        TextView today_time = dialogView.findViewById(R.id.today_time);
                        today_time.setText(dateString);
                        EditText celebrity_memo = dialogView.findViewById(R.id.celebrity_memo);

                        builder.setTitle("기념일 추가");
                        builder.setView(dialogView);

                        builder.setPositiveButton("확인", (dialogInterface, i) -> {
                            String str_celebrity_memo = celebrity_memo.getText().toString();

                            Celebrity newCelebrity = new Celebrity(str_celebrity_memo, dateString);
                            celebrityList.add(newCelebrity);
                            celebrityAdapter.notifyDataSetChanged();

                            celebrityDatabaseHelper.addCelebrity(newCelebrity,getFormattedDate(calendar_selectedDate));
                        });
                        builder.setNegativeButton("취소", null);
                        builder.show();
                    }
                });

                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });

        SundayDecorator sundayDecorator = new SundayDecorator();
        SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
        TodayDecorator todayDecorator = new TodayDecorator(getContext());

        CustomCalendarDecorator decorator = new CustomCalendarDecorator(getContext());
        materialCalendarView.addDecorator(decorator);

        materialCalendarView.addDecorator(saturdayDecorator);
        materialCalendarView.addDecorator(sundayDecorator);
        materialCalendarView.addDecorator(todayDecorator);

        recyclerView_schedule.setAdapter(scheduleAdapter);
        recyclerView_celebrity.setAdapter(celebrityAdapter);

        return view;
    }

    private void showEditDialogSchedule(Schedule schedule){
//      다이얼로그 뷰 생성
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_schedule, null);
        builder.setTitle("일정 수정");
        builder.setView(dialogView);

//      변수 선언
        EditText schedule_name = dialogView.findViewById(R.id.schedule_name);
        EditText schedule_memo = dialogView.findViewById(R.id.schedule_memo);
        TextView dayfirst = dialogView.findViewById(R.id.dayfirst);
        TextView daylast = dialogView.findViewById(R.id.daylast);
        TextView timefirst = dialogView.findViewById(R.id.timefirst);
        TextView timelast = dialogView.findViewById(R.id.timelast);

//      기존 데이터로 초기화
        schedule_name.setText(schedule.getName());
        schedule_memo.setText(schedule.getMemo());
        dayfirst.setText(schedule.getDayFirst());
        daylast.setText(schedule.getDayLast());
        timefirst.setText(schedule.getTimeFirst());
        timelast.setText(schedule.getTimeLast());

        dayfirst.setOnClickListener(V->showDatePicker(dayfirst));
        daylast.setOnClickListener(V->showDatePicker(daylast));
        timefirst.setOnClickListener(V->showTimePicker(timefirst));
        timelast.setOnClickListener(V->showTimePicker(timelast));

        builder.setPositiveButton("수정",(dialogInterface, i) -> {
            String updated_str_schedule_name = schedule_name.getText().toString();
            String updated_str_schedule_memo = schedule_memo.getText().toString();
            String updated_str_dayfirst = dayfirst.getText().toString();
            String updated_str_daylast = daylast.getText().toString();
            String updated_str_timefirst = timefirst.getText().toString();
            String updated_str_timelast = timelast.getText().toString();

            Schedule updatedSchedule = new Schedule(
                    schedule.getId(),
                    updated_str_schedule_name,
                    updated_str_schedule_memo,
                    updated_str_dayfirst,
                    updated_str_daylast,
                    updated_str_timefirst,
                    updated_str_timelast
                    );

            scheduleDatabaseHelper.updateSchedule(schedule.getId(),updatedSchedule);
            scheduleList.set(scheduleList.indexOf(schedule),updatedSchedule);
            scheduleAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void showDeleteDialogSchedule(Schedule schedule){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("삭제 확인");
        builder.setMessage("해당 기념일을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialogInterface, i) -> {
            // 데이터베이스에서 미션 삭제
            scheduleDatabaseHelper.deleteSchedule(schedule.getId());

            // 리스트에서 제거
            scheduleList.remove(schedule);
            scheduleAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void showEditDialogCelebrity(Celebrity celebrity){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_celebrity, null);

//      today_time = 오늘 날짜
        TextView today_time = dialogView.findViewById(R.id.today_time);
        EditText celebrity_memo = dialogView.findViewById(R.id.celebrity_memo);

        today_time.setText(celebrity.getDate());

        builder.setTitle("기념일 수정");
        builder.setView(dialogView);

        celebrity_memo.setText(celebrity.getMemo());

        builder.setPositiveButton("확인",((dialogInterface, i) -> {
            String updated_str_celebrity_memo = celebrity_memo.getText().toString();
            Celebrity updatedCelebrity = new Celebrity(
                    celebrity.getId(),
                    updated_str_celebrity_memo,
                    getFormattedDate(calendar_selectedDate)
                    );

            celebrityDatabaseHelper.updateCelebrity(celebrity.getId(),updatedCelebrity);
            celebrityList.set(celebrityList.indexOf(celebrity),updatedCelebrity);
            celebrityAdapter.notifyDataSetChanged();

        }));
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void showDeleteDialogCelebrity(Celebrity celebrity){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("삭제 확인");
        builder.setMessage("이 미션을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialogInterface, i) -> {
            // 데이터베이스에서 미션 삭제
            celebrityDatabaseHelper.deleteCelebrity(celebrity.getId());

            // 리스트에서 제거
            celebrityList.remove(celebrity);
            celebrityAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void showTimePicker(TextView textView){
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
            textView.setText(selectedTime);
        });

        timePicker.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "Time_picker");
    }

    private void showDatePicker(TextView textView){
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setSelection(selectedDate).build();
        materialDatePicker.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "Date_picker");
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String dateString = sdf.format(new Date((Long) selection));
            textView.setText(dateString);
        });
    }

    private String getFormattedDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

}
