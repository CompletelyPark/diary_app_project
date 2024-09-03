package com.jinwan.appproject.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jinwan.appproject.R;
import com.jinwan.appproject.activity.DiaryActivity;
import com.jinwan.appproject.activity.MainActivity;
import com.jinwan.appproject.adapter.DiaryAdapter;
import com.jinwan.appproject.adapter.RecyclerItemClickListener;
import com.jinwan.appproject.database.DiaryDatabaseHelper;
import com.jinwan.appproject.list.DiaryEntry;

import java.util.Date;
import java.util.List;

public class DiaryFragment extends Fragment {

    public DiaryAdapter diaryAdapter;
    private List<DiaryEntry> diaryEntryList;
    private DiaryDatabaseHelper diaryDatabaseHelper;
    private RecyclerView recyclerView_diary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_diary, container, false);
        diaryDatabaseHelper = new DiaryDatabaseHelper(getContext());

//      recyclerview 선언 및 초기화
        diaryEntryList = diaryDatabaseHelper.getAllDiaryEntries();

        diaryAdapter =new DiaryAdapter(diaryEntryList);
        recyclerView_diary = view.findViewById(R.id.recyclerView_diary);
        recyclerView_diary.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_diary.setAdapter(diaryAdapter);
        recyclerView_diary.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(),
                        recyclerView_diary, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 아이템 클릭 시 수정 다이얼로그 표시
//                showEditDialog(diaryEntryList.get(position));

                editContent(diaryEntryList.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // 아이템 길게 클릭 시 삭제 확인 다이얼로그 표시
                showDeleteDialog(diaryEntryList.get(position));
            }
        }));

        // FloatingActionButton 클릭 리스너 설정
        FloatingActionButton floatingActionButton2 = view.findViewById(R.id.floating_action_button2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.activityCall();
            }
        });

        return view;
    }



    private void editContent(DiaryEntry diaryEntry){
        Intent intent = new Intent(getContext(), DiaryActivity.class);
        intent.putExtra("diaryEntry",diaryEntry);
        startActivityForResult(intent,300);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            // Intent에서 전달된 새로운 diaryEntry 데이터를 받아옴
            DiaryEntry updatedDiaryEntry = (DiaryEntry) data.getSerializableExtra("new_diaryEntry");

            // 기존 리스트에서 해당 entry의 위치를 찾고 업데이트
            int position = diaryEntryList.indexOf(updatedDiaryEntry);
            if (position != -1) {
                diaryEntryList.set(position, updatedDiaryEntry);
                diaryAdapter.notifyItemChanged(position);  // 어댑터에 변경 알림
            }
        }
    }




    private void showEditDialog(DiaryEntry diaryEntry){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_diary_content,null);

        builder.setView(dialogView);
        String recent_title = diaryEntry.getTitle();
        String recent_content = diaryEntry.getContent();
        String recent_font = diaryEntry.getFont();
        int recent_fontSize = diaryEntry.getFontSize();
        boolean recent_isBold = diaryEntry.isBold();
        boolean recent_isItalic = diaryEntry.isItalic();
        int recent_weatherIcon = diaryEntry.getWeatherIcon();
        Date recent_date = diaryEntry.getDate();
        EditText dialog_title_text = dialogView.findViewById(R.id.dialog_title_text);
        EditText dialog_content_text = dialogView.findViewById(R.id.dialog_content_text);
        ImageView imageView_weather_icon = dialogView.findViewById(R.id.imageView_weather_icon);
        dialog_title_text.setText(recent_title);
        dialog_content_text.setText(recent_content);
        imageView_weather_icon.setImageResource(recent_weatherIcon);
        dialog_content_text.setTextSize(recent_fontSize);

        if(recent_isBold) applyStyleToSelectedText(dialog_content_text,new StyleSpan(Typeface.BOLD));
        if(recent_isItalic) applyStyleToSelectedText(dialog_content_text,new StyleSpan(Typeface.ITALIC));

        builder.setPositiveButton("수정",(dialogInterface, i) -> {
            String updated_title = dialog_title_text.getText().toString();
            String updated_content = dialog_content_text.getText().toString();

            DiaryEntry updatedDiaryEntry = new DiaryEntry(diaryEntry.getId(),
                    updated_title,updated_content,
                    recent_font,recent_fontSize,
                    recent_isBold,recent_isItalic,
                    recent_weatherIcon,recent_date
                    );

            // 데이터베이스 업데이트
            diaryDatabaseHelper.updateDiaryEntry(diaryEntry.getId(), updatedDiaryEntry);

            // 리스트 업데이트
            diaryEntryList.set(diaryEntryList.indexOf(diaryEntry), updatedDiaryEntry);
            diaryAdapter.notifyDataSetChanged();
        });


        builder.show();
    }

    private void showDeleteDialog(DiaryEntry diaryEntry){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("삭제 확인");
        builder.setMessage("이 일기를 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialogInterface, i) -> {
            // 데이터베이스에서 미션 삭제
            diaryDatabaseHelper.deleteDiaryEntry(diaryEntry);
            // 리스트에서 제거
            diaryEntryList.remove(diaryEntry);
            diaryAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
    private void applyStyleToSelectedText(EditText editText, Object span) {

        Spannable spannable = editText.getText();
        spannable.setSpan(span, 0, editText.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        editText.setText(spannable);
        editText.setSelection(editText.getText().toString().length()); // 커서를 끝으로 이동

    }
}
