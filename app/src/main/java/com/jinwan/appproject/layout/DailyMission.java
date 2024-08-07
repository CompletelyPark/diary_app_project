package com.jinwan.appproject.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinwan.appproject.helper.DateUtils;
import com.jinwan.appproject.R;

public class DailyMission extends AppCompatActivity {

    private LinearLayout layout_daily_Mission;
    private Button btn_add, btn_date_daily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_mission);

        layout_daily_Mission = (LinearLayout) findViewById(R.id.layout_daily_Mission);
        btn_add = (Button) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTextView("New Text");
            }
        });

        btn_date_daily = (Button) findViewById(R.id.btn_Date_Daily);
        btn_date_daily.setText(DateUtils.getCurrentDateFormatted());
    }


    private void addNewTextView(String text) {
        LinearLayout newLinearLayout = new LinearLayout(this);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMargins(10, 10, 10, 10);
        newLinearLayout.setLayoutParams(layoutParams);

        // 첫 번째 TextView 생성
        TextView timeTextView = new TextView(this);
        timeTextView.setText("09:00 ~ 10:00");
        timeTextView.setTextSize(18);

        newLinearLayout.addView(timeTextView);

        // 두 번째 TextView 생성
        TextView activityTextView = new TextView(this);
        LinearLayout.LayoutParams activityTextLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        activityTextLayoutParams.setMargins(40, 5, 0, 0);
        activityTextView.setLayoutParams(activityTextLayoutParams);
        activityTextView.setText("단어 외우기");
        activityTextView.setTextSize(25);
        newLinearLayout.addView(activityTextView);

        // CheckBox 생성
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams checkBoxLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxLayoutParams.setMargins(230, 0, 0, 0);
        checkBox.setLayoutParams(checkBoxLayoutParams);
        checkBox.setChecked(true);
        newLinearLayout.addView(checkBox);

        // 부모 레이아웃에 새로운 LinearLayout 추가
        layout_daily_Mission.addView(newLinearLayout);
    }
    }
