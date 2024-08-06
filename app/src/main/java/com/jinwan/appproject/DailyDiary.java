package com.jinwan.appproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DailyDiary extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private SeekBar seekBarBrightness;
    private TextView timeText,textBright;
    private Button btn_set_font_dialog, btn_theme_dark;
    private BrightnessManager brightnessManager;


    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionManager.checkWriteSettingsPermission(this))
            brightnessManager.initBrightness(seekBarBrightness, textBright);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

//      날짜 불러오기
        timeText = findViewById(R.id.txt_date3);
        timeText.setText(DateUtils.getCurrentDateFormatted());

//      navigation menu open
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.navigation_menu);
        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

//      seekbar set
        seekBarBrightness = (SeekBar) findViewById(R.id.seekBarBright);
        textBright = (TextView) findViewById(R.id.textBright);
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBarBrightness(textBright));

//      font dialog button
        btn_set_font_dialog = (Button) findViewById(R.id.btn_set_font_dialog);

//      theme dark button set
        btn_theme_dark = (Button) findViewById(R.id.btn_theme_dark);
        btn_theme_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTheme(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

//      화면 밝기에 대한 권한 설정
        brightnessManager = new BrightnessManager(getContentResolver(), getWindow());
        if (PermissionManager.checkWriteSettingsPermission(this)) {
            brightnessManager.initBrightness(seekBarBrightness, textBright);
        } else {
            PermissionManager.requestWriteSettingsPermission(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
//  화면 밝기 권한 확인
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean permission;
        if (requestCode == PermissionManager.WRITE_SETTINGS_PERMISSION_REQUEST_CODE) {
            if (PermissionManager.checkWriteSettingsPermission(this)) {
                brightnessManager.initBrightness(seekBarBrightness, textBright);
            } else {
                Toast.makeText(this, "시스템 설정 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

//  abstract seekbar change method
    private class SeekBarBrightness extends CustomSeekBarChangeListener {
        public SeekBarBrightness(TextView seekBarTxt) {
            super(seekBarTxt);

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);
            brightnessManager.setBrightness(progress);
        }
    }


//  navigation drawer layout setting
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
//      slide
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
//      open
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
//      slide close
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
//      state change
        public void onDrawerStateChanged(int newState) {
        }
    };

    //  글꼴 선택할 수 있는 dialog button
    public void onClickDialog(View view) {
        final String[] items = {"Option 1", "Option 2", "Option 3", "Option 4"};
        new AlertDialog.Builder(this).setTitle("글꼴 설정").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 사용자가 항목을 클릭하면 호출됩니다.
                        // 'which'는 사용자가 선택한 항목의 인덱스입니다.
                        // 예: items[which]로 선택된 항목에 접근 가능
                    }
                }).setNegativeButton("Cancel", null) // 취소 버튼
                .show();
    }

//  theme change dark mode
    private void changeTheme(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}




/*
  앱에서 설정에 접근하기 위해서 사용하는 방법은 SettingsProvider의 설정 항목을 쿼리해서 참조 할 수 있도록 만든 Settings 클래스를 이용하는 것
  접근 단순화 해주는 class
  public final class Settings extend Object
  global - MultiUser 를 위한 공용 설정 데이터 접근 용도
  Secure - 보안 관련한 설정 데이터 접근 용도
  System - 시스템 과 관련한 설정 데이터 접근 용도
  
  밝기 조절 모드
  int brightnessMode = Settings.System.getInt(getContentResolver(),
                                                Settings.System.Screen_BRIGTNESS_MODE);

*/
