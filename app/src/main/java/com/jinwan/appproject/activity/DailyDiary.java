package com.jinwan.appproject.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.jinwan.appproject.R;

public class DailyDiary extends BaseActivity {

    private LinearLayout layout_daily_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_diary);
        layout_daily_diary = (LinearLayout) findViewById(R.id.layout_daily_diary);

    }
}
