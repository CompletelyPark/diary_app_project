package com.jinwan.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class DailyDiary extends AppCompatActivity {

    TextView timeText;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private SeekBar seekBarBrightness;
    private SeekBar seekBarBrightness111;
    private TextView textBright111;

    private TextView textBright;
    private Button btn_set_font_dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

//      날짜 불러오기
        timeText = findViewById(R.id.txt_date3);
        timeText.setText(DateUtils.getCurrentDateFormatted());

//      navigation menu open
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.navigation_menu);
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

        seekBarBrightness111 = (SeekBar) findViewById(R.id.seekBarBright111);
        textBright111 = (TextView) findViewById(R.id.textBright111);
        seekBarBrightness111.setOnSeekBarChangeListener(new SeekBarBrightness(textBright111));


//      font dialog button
        btn_set_font_dialog = (Button) findViewById(R.id.btn_set_font_dialog);



    }



    private class SeekBarBrightness extends CustomSeekBarChangeListener{
           public SeekBarBrightness(TextView seekBarTxt){
               super(seekBarTxt);
               
           }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

        }
    }



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
    public void onClickDialog(View view){
        final String[] items = {"Option 1", "Option 2", "Option 3", "Option 4"};
        new AlertDialog.Builder(this)
                .setTitle("글꼴 설정")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 사용자가 항목을 클릭하면 호출됩니다.
                        // 'which'는 사용자가 선택한 항목의 인덱스입니다.
                        // 예: items[which]로 선택된 항목에 접근 가능
                    }
                })
                .setNegativeButton("Cancel", null) // 취소 버튼
                .show();
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
