package com.jinwan.appproject.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.jinwan.appproject.adapter.RecyclerItemClickListener;
import com.jinwan.appproject.database.MissionDatabaseHelper;
import com.jinwan.appproject.list.Mission;
import com.jinwan.appproject.adapter.MissionAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DailyFragment extends Fragment {


        private List<Mission> missionlist; // 스케줄 리스트
        private MissionAdapter missionAdapter; // 어댑터
        private MissionDatabaseHelper dbHelper; // 데이터베이스 헬퍼
        private Calendar selectedDate; // 선택된 날짜

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_daily_mission, container, false);

            dbHelper = new MissionDatabaseHelper(getContext()); // 데이터베이스 헬퍼 초기화
            selectedDate = Calendar.getInstance(); // 현재 날짜로 초기화

            FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
            floatingActionButton.setOnClickListener(v -> show());

            // DatePicker 버튼 설정
            Button datePickerButton = view.findViewById(R.id.date_picker_button);
            datePickerButton.setOnClickListener(v -> showDatePicker());

            // RecyclerView 초기화
            missionlist = dbHelper.getMissionsByDate(getFormattedDate(selectedDate));
            missionAdapter = new MissionAdapter(missionlist);

            RecyclerView recyclerView2 = view.findViewById(R.id.recyclerView2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView2.setAdapter(missionAdapter);

            recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 아이템 클릭 시 수정 다이얼로그 표시
                    showEditDialog(missionlist.get(position));
                }

                @Override
                public void onLongItemClick(View view, int position) {
                    // 아이템 길게 클릭 시 삭제 확인 다이얼로그 표시
                    showDeleteDialog(missionlist.get(position));
                }
            }));

            return view;
        }
    public void show() {

        // Dialog 생성
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_daily_mission, null);
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

            Mission mission = new Mission( str_schedule_memo2, str_timefirst2, str_timelast2);
            missionlist.add(mission);
            missionAdapter.notifyDataSetChanged(); // RecyclerView 업데이트

            // 현재 선택된 날짜에 대해 새로운 미션 추가
            dbHelper.addMission(mission, getFormattedDate(selectedDate));
        });

        builder.setNegativeButton("취소", null); // Dialog의 Cancel 버튼 추가
        builder.show(); // Dialog 표시
    }

        private void showEditDialog(Mission mission) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_daily_mission, null);
            builder.setTitle("일정 수정");
            builder.setView(dialogView);

            TextView schedule_memo2 = dialogView.findViewById(R.id.schedule_memo2);
            TextView timefirst2 = dialogView.findViewById(R.id.timefirst2);
            TextView timelast2 = dialogView.findViewById(R.id.timelast2);

            // 기존 데이터로 다이얼로그 초기화
            schedule_memo2.setText(mission.getMemo2());
            timefirst2.setText(mission.getTimeFirst2());
            timelast2.setText(mission.getTimeLast2());

            timefirst2.setOnClickListener(v -> showTimePicker(timefirst2));
            timelast2.setOnClickListener(v -> showTimePicker(timelast2));

            builder.setPositiveButton("수정", (dialogInterface, i) -> {
                String updatedMemo = schedule_memo2.getText().toString();
                String updatedTimeFirst = timefirst2.getText().toString();
                String updatedTimeLast = timelast2.getText().toString();

                Mission updatedMission = new Mission(mission.getId(), updatedMemo, updatedTimeFirst, updatedTimeLast);

                // 데이터베이스 업데이트
                dbHelper.updateMission(mission.getId(), updatedMission);

                // 리스트 업데이트
                missionlist.set(missionlist.indexOf(mission), updatedMission);
                missionAdapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("취소", null);
            builder.show();
        }

        private void showDeleteDialog(Mission mission) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            builder.setTitle("삭제 확인");
            builder.setMessage("이 미션을 삭제하시겠습니까?");
            builder.setPositiveButton("삭제", (dialogInterface, i) -> {
                // 데이터베이스에서 미션 삭제
                dbHelper.deleteMission(mission.getId());

                // 리스트에서 제거
                missionlist.remove(mission);
                missionAdapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("취소", null);
            builder.show();
        }

        private void showDatePicker() {
            int year = selectedDate.get(Calendar.YEAR);
            int month = selectedDate.get(Calendar.MONTH);
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        selectedDate.set(Calendar.YEAR, selectedYear);
                        selectedDate.set(Calendar.MONTH, selectedMonth);
                        selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                        // 선택된 날짜에 해당하는 미션을 불러오고 RecyclerView를 업데이트
                        missionlist.clear();
                        missionlist.addAll(dbHelper.getMissionsByDate(getFormattedDate(selectedDate)));
                        missionAdapter.notifyDataSetChanged();
                    },
                    year, month, day);

            datePickerDialog.show();
        }

        private String getFormattedDate(Calendar calendar) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(calendar.getTime());
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

            timePicker.show(getParentFragmentManager(), "Time_picker");
        }
    }


