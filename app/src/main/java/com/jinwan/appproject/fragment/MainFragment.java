package com.jinwan.appproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.jinwan.appproject.R;
import java.util.Random;

public class MainFragment extends Fragment {

    private Button btn_text;
    private Handler handler = new Handler();
    private Runnable runnable;

    private String[] quotes = {
            "1번째",
            "2번째",
            "3번째",
            "4번째",
            "5번째",
    };

    private static final String PREFS_NAME = "text_prefs";
    private static final String KEY_TEXT = "current_text";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        btn_text = view.findViewById(R.id.btn_text);

        // 저장된 텍스트가 있다면 버튼에 설정
        String savedText = prefs.getString(KEY_TEXT, "기본 텍스트");
        btn_text.setText(savedText);

        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int index = random.nextInt(quotes.length);
                String randomQ = quotes[index];

                // 새로운 텍스트를 SharedPreferences에 저장
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_TEXT, randomQ);
                editor.apply();

                // 버튼 텍스트 업데이트
                btn_text.setText(randomQ);
            }
        });

        // 주기적으로 텍스트 변경
        startAutoChangeText();

        return view;
    }

    private void startAutoChangeText() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int index = random.nextInt(quotes.length);
                String randomQ = quotes[index];

                // 버튼 텍스트 업데이트
                btn_text.setText(randomQ);

                // 다음 실행 예약 5초
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(runnable); // 초기 실행
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable); // Fragment가 파괴될 때 Runnable 제거
    }
}
