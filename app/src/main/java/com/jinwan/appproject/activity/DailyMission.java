package com.jinwan.appproject.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.jinwan.appproject.R;

public class DailyMission extends BaseActivity {

    private LinearLayout layout_daily_Mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_mission);
        layout_daily_Mission = (LinearLayout) findViewById(R.id.layout_daily_Mission);

    }
}

