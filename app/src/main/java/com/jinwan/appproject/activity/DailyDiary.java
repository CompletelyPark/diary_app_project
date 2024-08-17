package com.jinwan.appproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jinwan.appproject.helper.BaseActivity;
import com.jinwan.appproject.helper.CustomSeekBarChangeListener;
import com.jinwan.appproject.helper.DateUtils;
import com.jinwan.appproject.R;
import com.jinwan.appproject.helper.ThemeUtils;
import com.jinwan.appproject.manager.BrightnessManager;
import com.jinwan.appproject.manager.PermissionManager;

public class DailyDiary extends BaseActivity {

    private LinearLayout layout_daily_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_diary);
        layout_daily_diary = (LinearLayout) findViewById(R.id.layout_daily_diary);

    }
}
