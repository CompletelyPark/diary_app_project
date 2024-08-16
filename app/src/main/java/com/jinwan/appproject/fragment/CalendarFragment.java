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
import com.jinwan.appproject.Api.HolidayApiService;
import com.jinwan.appproject.Api.HolidayDecorator;
import com.jinwan.appproject.Api.HolidayResponse;
import com.jinwan.appproject.Data.MyDatabaseHelper;
import com.jinwan.appproject.Data.Schedule;
import com.jinwan.appproject.R;
import com.jinwan.appproject.recycler.ScheduleAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
    private int currentYear;

    EditText schedule_name, schedule_memo;
    TextView dayfirst, daylast;
    TextView timelast, timefirst;

    String str_schedule_name, str_schedule_memo;
    String str_dayfirst, str_daylast;
    String str_timefirst, str_timelast;

    private MyDatabaseHelper dbhelper;

    private MaterialCalendarView materialCalendarView;


    private static final String BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"; // 실제 API URL로 변경
    private static final String API_KEY = "W6UiEtxLW0%2F127mfcW%2BRR3657%2FRQpw8zKs5XC1HaX2GGzosUO%2BuKuquHwnHYcyWfSokrvyPfHWmjBO1Ifh0YvA%3D%3D"; // 발급받은 API 키

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
//        calendarView = view.findViewById(R.id.calendarView);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        materialCalendarView = view.findViewById(R.id.calendar_view);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        dbhelper = new MyDatabaseHelper(getContext());

        // Initialize schedule list
        scheduleList = new ArrayList<>();

        // Initialize adapter and set it to RecyclerView
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scheduleAdapter);


//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        String todayDate = sdf.format(new Date());
//
//        // 오늘 날짜에 해당하는 일정 가져오기
//        scheduleList = dbhelper.getTodaySchedules(todayDate);
//          tltltltltltltltlqkfkfkfkfkfkfltqlkfltqklfkqtsksdktwlsWk dlrp dho dksehosmsrjsep wlsWKfh tlqkfkdkfadsf
//        // RecyclerView에 일정 표시
//        scheduleAdapter = new ScheduleAdapter(scheduleList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(scheduleAdapter);

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
                    Log.d("jtag", "open");
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_upward_24px));
                }
                else
                {
                    calendarView.setVisibility(View.GONE);
                    Log.d("jtag", "close");
                    istf = true;
                    toggleCalendar.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_downward_24px));
                }
            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date, boolean selected) {

            // 일정 추가 다이얼로그 띄우기
            int year = date.getYear();
            int month = date.getMonth() - 1; // Calendar 클래스는 0부터 시작하므로 -1 해줍니다.
            int dayOfMonth = date.getDay();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            selectedDate = calendar.getTimeInMillis();
            showDialog();
        }
    });


        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);


        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        fetchHolidays(currentYear);
        return view;
    }

    private void fetchHolidays(int year) {
        // Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 기본 URL 설정
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 추가
                .build();

        HolidayApiService apiService = retrofit.create(HolidayApiService.class); // API 서비스 생성
        Call<HolidayResponse> call = apiService.getHolidays(year, API_KEY); // API 호출

        // 비동기 호출
        call.enqueue(new Callback<HolidayResponse>() {
            @Override
            public void onResponse(Call<HolidayResponse> call, Response<HolidayResponse> response) {
                // API 호출이 성공적이고 응답이 있을 경우
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("jtag", "API 호출 성공: " + response.body()); // 성공 로그 출력
                    Set<CalendarDay> holidayDays = new HashSet<>(); // 공휴일 날짜를 저장할 Set

                    // 응답에서 공휴일 날짜를 가져옴
                    for (HolidayResponse.Holiday holiday : response.body().getResponse()) {
                        String[] dateParts = holiday.getDate().split("-"); // 날짜를 분리
                        int month = Integer.parseInt(dateParts[1]) - 1; // 월은 0부터 시작하므로 1을 빼줌
                        int day = Integer.parseInt(dateParts[2]); // 일 추출
                        holidayDays.add(CalendarDay.from(year, month, day)); // CalendarDay 객체 추가
                    }

                    // HolidayDecorator 적용
                    HolidayDecorator holidayDecorator = new HolidayDecorator(holidayDays);
                    materialCalendarView.addDecorators(holidayDecorator); // CalendarView에 데코레이터 추가
                } else {
                    Log.e("jtag", "API 호출 실패: 응답이 null이거나 실패했습니다."); // 실패 로그 출력
                }
            }

            @Override
            public void onFailure(Call<HolidayResponse> call, Throwable t) {
                // API 호출 실패 시 에러 처리
                Log.e("jtag", "API 호출 실패: " + t.getMessage()); // 에러 메시지 로그 출력
                t.printStackTrace(); // 에러 로그 출력
            }
        });
    }


    private void showDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(materialCalendarView.getContext());

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
                Log.d("jtag", "layout_schedule_clicked");
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
                        timePicker.show(getActivity().getSupportFragmentManager(), "jtag");
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
//                            Log.d("jjtag",str_timelast);
                            timelast.setText(selectedTime);
                        });

                        timePicker.show(getActivity().getSupportFragmentManager(), "jtag");
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

                        Log.d("jjtag","schedule name:"+ str_schedule_name +
                                " schedule memo:"+ str_schedule_memo + "\n" +
                                "day first:"+ str_dayfirst +
                                " day last:"+ str_daylast + "\n" +
                                "time first:"+ str_timefirst +
                                " time last:"+ str_timelast );

                        Schedule newSchedule = new Schedule(str_schedule_name, str_schedule_memo, str_dayfirst, str_daylast, str_timefirst, str_timelast);
//                        dbhelper.addSchedule(newSchedule);
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
                Log.d("jtag", "layout_celebrity_clicked");
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