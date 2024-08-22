package com.jinwan.appproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jinwan.appproject.R;
import com.jinwan.appproject.activity.DiaryActivity;
import com.jinwan.appproject.activity.MainActivity;
import com.jinwan.appproject.adapter.DiaryOutAdapter;
import com.jinwan.appproject.list.Diary_out;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment {

    public DiaryOutAdapter diaryOutAdapter;
    private List<Diary_out> diary_outList;
    private RecyclerView recyclerView_diary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_diary, container, false);

//      recyclerview 선언 및 초기화
        diary_outList = new ArrayList<>();
        diaryOutAdapter =new DiaryOutAdapter(diary_outList);
        recyclerView_diary = view.findViewById(R.id.recyclerView_diary);
        recyclerView_diary.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_diary.setAdapter(diaryOutAdapter);


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


}
