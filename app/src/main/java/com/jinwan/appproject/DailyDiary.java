package com.jinwan.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyDiary extends AppCompatActivity {
    long mnow;
    Date mdate;
    SimpleDateFormat mformat = new SimpleDateFormat("M월 d일");
    TextView textView;

    private DrawerLayout drawerLayout;
    private View drawerView;

    private SeekBar seekBar;
    private TextView seekBarTxt;

    private Button btn_set_font_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

//      날짜 불러오기
        textView = findViewById(R.id.txt_date3);
        textView.setText(getTime());

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
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarTxt = (TextView) findViewById(R.id.seekBarTxt);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue=0;

            @Override
//          이 때 숫자값 변경
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                drawerView.setVisibility(View.INVISIBLE);
//                seekBar.setVisibility(View.VISIBLE);
                Log.d("Tag","onProgressChanged: "+ progress);
                seekBarTxt.setText(progress+"");
//              처음에 seekBarTxt.setText(progress); 로 적었다가 계속 꺼져서 문제점을 찾아봤다
//              textview의 setText method는 String 객체로 집어 넣어 줘야 한다는 것을 알게 됐다
                setBrightness(progress);
                progressValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Tag","onStartTrackingTouch");
//                setChildViewsEnabled(drawerView,false);
            }

            @Override
//          이 때 결과 값 받아와서 값 수정하기
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Tag","onStopTrackingTouch: " + progressValue);
//                setChildViewsEnabled(drawerView,true);

            }
        });

//      font dialog button
        btn_set_font_dialog = (Button) findViewById(R.id.btn_set_font_dialog);

    }

//  오늘 날짜를 불러오는 method
    private String getTime(){
        mnow = System.currentTimeMillis();
        mdate = new Date(mnow);
        return mformat.format(mdate);
    }

//  화면 밝기 조절
    private void setBrightness(int value){
        if(value<10)value = 10;
        else if(value>100) value=100;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = (float)value/100;
        getWindow().setAttributes(params);


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

    private void setChildViewsEnabled(View view, boolean enabled){
        int x=0;

        if(view instanceof ViewGroup){
            ViewGroup group = (ViewGroup) view;
            for(int i = 0; i<group.getChildCount();i++){
                View child = group.getChildAt(i);
                if (child.getId() == R.id.seekBar&&child.getId() == R.id.seekBarTxt)
                    continue;
                else {
                    child.setEnabled(enabled);
                    if (enabled == false) x = 4;
                    else x = 0;
//              Visible = 0, INVISIBLE = 4, GONE = 8

                    child.setVisibility(x);
                }
            }
        }
    }

    public void onClickDialog(View view){
        final String[] items = {"Option 1", "Option 2", "Option 3"};
        new AlertDialog.Builder(this)
                .setTitle("Select an Option")
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

