package com.jinwan.appproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jinwan.appproject.R;
import com.jinwan.appproject.activity.DiaryActivity;
import com.jinwan.appproject.activity.MainActivity;
import com.jinwan.appproject.adapter.DiaryOutAdapter;
import com.jinwan.appproject.adapter.RecyclerItemClickListener;
import com.jinwan.appproject.database.DiaryOutDatabaseHelper;
import com.jinwan.appproject.list.Diary_out;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment {

    public DiaryOutAdapter diaryOutAdapter;
    private List<Diary_out> diary_outList;
    private DiaryOutDatabaseHelper diaryOutDatabaseHelper;
    private RecyclerView recyclerView_diary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_diary, container, false);

        diaryOutDatabaseHelper = new DiaryOutDatabaseHelper(getContext());


//      recyclerview 선언 및 초기화
        diary_outList = diaryOutDatabaseHelper.getAllDiary_outs();
        diaryOutAdapter =new DiaryOutAdapter(diary_outList);
        recyclerView_diary = view.findViewById(R.id.recyclerView_diary);
        recyclerView_diary.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_diary.setAdapter(diaryOutAdapter);
        recyclerView_diary.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView_diary, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 아이템 클릭 시 수정 다이얼로그 표시
//                showEditDialog(diary_outList.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // 아이템 길게 클릭 시 삭제 확인 다이얼로그 표시
                showDeleteDialog(diary_outList.get(position));
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

    private void Edit(){

    }


    private void showDeleteDialog(Diary_out diary_out){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("삭제 확인");
        builder.setMessage("이 미션을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialogInterface, i) -> {
            // 데이터베이스에서 미션 삭제
            diaryOutDatabaseHelper.deleteDiary_out(diary_out.getId());
            // 리스트에서 제거
            diary_outList.remove(diary_out);
            diaryOutAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

}
