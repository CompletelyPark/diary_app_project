package com.jinwan.appproject.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinwan.appproject.helper.BaseActivity;
import com.jinwan.appproject.helper.DateUtils;
import com.jinwan.appproject.R;
import com.jinwan.appproject.helper.ThemeUtils;

public class DailyMission extends BaseActivity {

    private LinearLayout layout_daily_Mission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_mission);
        layout_daily_Mission = (LinearLayout) findViewById(R.id.layout_daily_Mission);

    }
}

