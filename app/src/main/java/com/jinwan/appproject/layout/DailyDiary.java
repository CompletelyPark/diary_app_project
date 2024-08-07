package com.jinwan.appproject.layout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jinwan.appproject.helper.CustomSeekBarChangeListener;
import com.jinwan.appproject.helper.DateUtils;
import com.jinwan.appproject.R;
import com.jinwan.appproject.manager.BrightnessManager;
import com.jinwan.appproject.manager.PermissionManager;

public class DailyDiary extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private SeekBar seekBarBrightness, seekBar_textSize, seekBar_line_space_Extra, seekBar_UpDown_Padding, seekBar_LeftRight_Padding;
    private TextView timeText, textBright, textSize, text_line_space, UpDownPadding, LeftRight_Padding;
    private Button btn_set_font_dialog, btn_theme_dark, btn_auto_bright;
    private BrightnessManager brightnessManager;

    private boolean ischangebtn = true;
    private boolean isautobtn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionManager.checkWriteSettingsPermission(this))
            brightnessManager.initBrightness(seekBarBrightness, textBright);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_diary);

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

        seekBar_textSize = (SeekBar) findViewById(R.id.seekBar_textSize);
        textSize = (TextView) findViewById(R.id.textSize);
        seekBar_textSize.setOnSeekBarChangeListener(new SeekBarTextSize(textSize));

        seekBar_line_space_Extra = (SeekBar) findViewById(R.id.seekBar_line_space_Extra);
        text_line_space = (TextView) findViewById(R.id.text_line_space);
        seekBar_line_space_Extra.setOnSeekBarChangeListener(new SeekBarTextSize(text_line_space));

        seekBar_UpDown_Padding = (SeekBar) findViewById(R.id.seekBar_UpDown_Padding);
        UpDownPadding = (TextView) findViewById(R.id.UpDownPadding);
        seekBar_UpDown_Padding.setOnSeekBarChangeListener(new SeekBarTextSize(UpDownPadding));

        seekBar_LeftRight_Padding = (SeekBar) findViewById(R.id.seekBar_LeftRight_Padding);
        LeftRight_Padding = (TextView) findViewById(R.id.LeftRight_Padding);
        seekBar_LeftRight_Padding.setOnSeekBarChangeListener(new SeekBarTextSize(LeftRight_Padding));

//      font dialog button
        btn_set_font_dialog = (Button) findViewById(R.id.btn_set_font_dialog);

//      theme dark button set
        btn_theme_dark = (Button) findViewById(R.id.btn_theme_dark);
        btn_theme_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTheme();
            }
        });

//      bright auto setting button
        btn_auto_bright = (Button) findViewById(R.id.btn_auto_bright);
        btn_auto_bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoBright();
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

//  Brightness Seekbar
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

//  TextSize Seekbar
    private class SeekBarTextSize extends CustomSeekBarChangeListener {
        public SeekBarTextSize(TextView seekBarTxt) {
            super(seekBarTxt);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

        }
    }

//  LineSpaceExtra Seekbar
    private class seekBar_line_space_Extra extends CustomSeekBarChangeListener {
        public seekBar_line_space_Extra(TextView seekBarTxt) {
            super(seekBarTxt);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

        }
    }

//  UpDownPadding Seekbar
    private class seekBar_UpDown_Padding extends CustomSeekBarChangeListener {
        public seekBar_UpDown_Padding(TextView seekBarTxt) {
            super(seekBarTxt);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

        }
    }

//  LeftRightPadding Seekbar
    private class seekBar_LeftRight_Padding	extends CustomSeekBarChangeListener {
        public seekBar_LeftRight_Padding(TextView seekBarTxt) {
            super(seekBarTxt);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

        }
    }

    //  navigation drawer layout setting
    DrawerLayout.DrawerListener listener = new androidx.drawerlayout.widget.DrawerLayout.DrawerListener() {
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
    private void changeTheme() {
        if (ischangebtn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            ischangebtn = false;
            Log.d("Tag","darkmode");
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            ischangebtn = true;
            Log.d("Tag","lightmode");

        }
    }

    //  auto brightness
    private void autoBright() {
        //  auto brightness boolean
        ContentResolver contentResolver = getContentResolver();

        if (isautobtn){
            Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            isautobtn = false;
            Log.d("Tag","Automatic");
        }
        else{
            Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            isautobtn = true;
            Log.d("Tag","Manual");


        }


    }

}
