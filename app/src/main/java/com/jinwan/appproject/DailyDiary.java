package com.jinwan.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyDiary extends AppCompatActivity {
    long mnow;
    Date mdate;
    SimpleDateFormat mformat = new SimpleDateFormat("M월 d일");
    TextView textView;

    private DrawerLayout drawerLayout;
    private View drawerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

//      날짜 불러오기
        textView = findViewById(R.id.txt_date3);
        textView.setText(getTime());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        drawerLayout.setDrawerListener(listener);

        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        
    }

    private String getTime(){
        mnow = System.currentTimeMillis();
        mdate = new Date(mnow);
        return mformat.format(mdate);
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

}