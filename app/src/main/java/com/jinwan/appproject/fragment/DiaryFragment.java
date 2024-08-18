package com.jinwan.appproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jinwan.appproject.R;
import com.jinwan.appproject.activity.Diary;
import com.jinwan.appproject.activity.ThemeChoice;

public class DiaryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_diary, container, false);

        // FloatingActionButton 클릭 리스너 설정
        FloatingActionButton floatingActionButton2 = view.findViewById(R.id.floating_action_button2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Diary diary = new Diary();

//              현재 fragment가 속한 activity의 fragmentmanerger 를 가져온다
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container,diary);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }


}
