package com.jinwan.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DailyMission extends AppCompatActivity {

    private LinearLayout layout_daily_Mission;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_mission);

        layout_daily_Mission = (LinearLayout) findViewById(R.id.layout_daily_Mission);
        btn_add = (Button) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTextView("New Text");
            }
        });

    }


    private void addNewTextView(String text) {
        // 새로운 LinearLayout 생성
        LinearLayout newLinearLayout = new LinearLayout(this);
        newLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // 새로운 TextView 생성
        TextView newTextView = new TextView(this);
        newTextView.setText(text);
        newTextView.setTextSize(18);

        // 새로운 TextView를 새로운 LinearLayout에 추가
        newLinearLayout.addView(newTextView);

        // 새로운 LinearLayout을 기존의 LinearLayout 컨테이너에 추가
        layout_daily_Mission.addView(newLinearLayout);
    }
}