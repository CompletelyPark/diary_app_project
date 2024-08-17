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
            "시간은 가장 공정한 자원이다. 모든 사람에게 같은 양이 주어진다.",

            "시간이 없다는 것은 변명일 뿐이다.",

            "오늘을 제대로 살면 내일이 자연스럽게 따라온다.",

            "시간을 관리하지 않으면 시간에 의해 관리당하게 된다.",

            "시간은 소중하다. 잃어버린 시간은 되돌릴 수 없다.",

            "좋은 계획은 시간을 절약하는 가장 좋은 방법이다.",

            "시간은 우리의 가장 큰 자산이다. 잘 활용하라.",

            "미래는 현재의 선택에 달려 있다.",

            "시간을 지배하는 사람만이 시간을 지킬 수 있다.",

            "성공은 시간 관리에서 시작된다.",

            "오늘의 일은 오늘 끝내라.",

            "시간을 잘 활용하는 사람은 성공의 문을 열어간다.",

            "시간을 낭비하지 마라. 당신의 미래가 달려 있다.",

            "시간은 돈보다 더 귀하다. 한 번 잃은 시간은 되돌릴 수 없다.",

            "지금 시작하는 것이 가장 좋은 시간이다.",

            "시간을 잘 관리하면 스트레스를 줄일 수 있다.",

            "모든 일이 시작되는 시간은 바로 지금이다.",

            "오늘의 작은 노력들이 내일의 큰 성과로 이어진다.",

            "시간을 계획하면 계획이 성공을 가져온다.",

            "시간을 지키는 것은 신뢰를 얻는 첫 걸음이다.",

            "하루 24시간을 어떻게 쓰느냐가 인생을 결정한다.",

            "시간은 더 많이 사용할수록 더 많이 주어진다.",

            "효율적인 시간 관리는 삶의 질을 높인다.",

            "시간을 절약하는 방법은 시간을 아끼는 것이 아니다.",

            "무엇을 하느냐보다 언제 하느냐가 중요하다.",

            "한 시간의 계획이 하루를 바꾼다.",

            "우리는 매일 24시간을 가진다. 무엇을 하느냐가 중요하다.",

            "시간은 너무 소중해서 허비할 수 없다.",

            "효율적인 시간 관리는 인생을 풍요롭게 만든다.",

            "시간을 잘 관리하면 인생이 더 행복해진다.",

            "시간을 낭비하지 말고, 계획적으로 사용하라.",

            "성공적인 사람은 시간을 소중히 여긴다.",

            "시간을 지혜롭게 사용하는 것이 성공의 비결이다.",

            "매일의 작은 시간 관리를 통해 큰 목표를 달성하라.",

            "지금 하는 일이 미래를 만든다.",

            "시간 관리의 첫 걸음은 목표 설정이다.",

            "하루를 어떻게 보내느냐가 인생을 결정한다.",

            "지금 이 순간이 가장 중요한 시간이다.",

            "시간을 관리하지 않으면 인생을 관리할 수 없다.",

            "시간을 가치 있게 쓰는 것이 삶을 풍요롭게 한다.",

            "좋은 시간을 관리하는 기술이 성공을 이끈다.",

            "시간을 효과적으로 사용하면 모든 일이 쉬워진다.",

            "시간 관리가 잘 될수록 인생의 질이 높아진다.",

            "미래를 준비하는 가장 좋은 방법은 지금부터 시작하는 것이다.",

            "오늘의 노력은 내일의 성공을 보장한다.",

            "시간을 계획적으로 사용하면 성공의 기회를 얻는다.",

            "한 시간의 계획이 한 달을 결정한다.",

            "지금의 시간 관리가 미래를 만든다.",

            "시간은 정직하다. 그걸 어떻게 쓰느냐가 중요하다.",

            "시간은 모든 것의 열쇠다. 효율적으로 사용하라.",
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
                handler.postDelayed(this, 10000);
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
