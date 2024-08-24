package com.jinwan.appproject.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jinwan.appproject.R;
import com.jinwan.appproject.activity.MainActivity;
import com.jinwan.appproject.adapter.DiaryOutAdapter;
import com.jinwan.appproject.adapter.RecyclerItemClickListener;
import com.jinwan.appproject.database.DiaryDatabaseHelper;
import com.jinwan.appproject.list.DiaryEntry;
import com.jinwan.appproject.list.Mission;

import java.util.List;

public class DiaryFragment extends Fragment {

    public DiaryOutAdapter diaryOutAdapter;
    private List<DiaryEntry> diaryEntryList;
    private DiaryDatabaseHelper diaryDatabaseHelper;

    private RecyclerView recyclerView_diary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_diary, container, false);

//        diaryOutDatabaseHelper = new DiaryOutDatabaseHelper(getContext());
        diaryDatabaseHelper = new DiaryDatabaseHelper(getContext());

//      recyclerview 선언 및 초기화
        diaryEntryList = diaryDatabaseHelper.getAllDiaryEntries();

        diaryOutAdapter =new DiaryOutAdapter(diaryEntryList);
        recyclerView_diary = view.findViewById(R.id.recyclerView_diary);
        recyclerView_diary.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_diary.setAdapter(diaryOutAdapter);
        recyclerView_diary.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(),
                        recyclerView_diary, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 아이템 클릭 시 수정 다이얼로그 표시
                showEditDialog(diaryEntryList.get(position));
//                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
//                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_diary_content,null);
//                builder.setTitle("내용 확인");
//                builder.setView(dialogView);
//                DiaryEntry diaryEntry1 = diaryDatabaseHelper.getDiaryEntry(position);

//                EditText dialog_title_text = dialogView.findViewById(R.id.dialog_title_text);
//                EditText dialog_content_text = dialogView.findViewById(R.id.dialog_content_text);
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
        EditText dialog_title_text = dialogView.findViewById(R.id.dialog_title_text);
        EditText dialog_content_text = dialogView.findViewById(R.id.dialog_content_text);
        ImageView imageView_weather_icon = dialogView.findViewById(R.id.imageView_weather_icon);
        dialog_title_text.setText(recent_title);
        dialog_content_text.setText(recent_content);
        imageView_weather_icon.setImageResource(recent_weatherIcon);

        Typeface typeface = getTypefaceByFontName(recent_font);

        dialog_content_text.setTextSize(recent_fontSize);

// 가져온 Typeface 객체를 EditText에 적용
        if (typeface != null) {
            applyStyleToSelectedText(dialog_content_text,typeface);
        }


        if(recent_isBold) applyStyleToSelectedText(dialog_content_text,new StyleSpan(Typeface.BOLD));
        if(recent_isItalic) applyStyleToSelectedText(dialog_content_text,new StyleSpan(Typeface.ITALIC));

        builder.setPositiveButton("수정",(dialogInterface, i) -> {
            String updated_title = dialog_title_text.getText().toString();
            String updated_content = dialog_content_text.getText().toString();

            DiaryEntry updatedDiaryEntry = new DiaryEntry(diaryEntry.getId(),
                    updated_title,updated_content,
                    recent_font,recent_fontSize,
                    recent_isBold,recent_isItalic,
                    recent_weatherIcon
                    );

            // 데이터베이스 업데이트
            diaryDatabaseHelper.updateDiaryEntry(diaryEntry.getId(), updatedDiaryEntry);

            // 리스트 업데이트
            diaryEntryList.set(diaryEntryList.indexOf(diaryEntry), updatedDiaryEntry);
            diaryOutAdapter.notifyDataSetChanged();
        });



        builder.show();
    }

    private Typeface getTypefaceByFontName(String fontName) {
        switch (fontName) {
            case "roboto":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.roboto);
            case "bazzi":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.bazzi);
            case "dnfforgedblade_light":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.dnfforgedblade_light);
            case "mabinogi_classic":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.mabinogi_classic);
            case "maplestory_light":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.maplestory_light);
            case "nexon_kart_gothic_medium":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.nexon_kart_gothic_medium);
            case "nexon_lv2_gothic":
                return ResourcesCompat.getFont(getActivity().getApplicationContext(), R.font.nexon_lv2_gothic);
            default:
                return null;
        }
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
            diaryOutAdapter.notifyDataSetChanged();
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
