package com.jinwan.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity1 extends AppCompatActivity {
    long mnow;
    Date mdate;
    SimpleDateFormat mformat = new SimpleDateFormat("yy-MM-dd");

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        textView = findViewById(R.id.txt_date);
        textView.setText(getTime());
    }

    private String getTime(){
        mnow = System.currentTimeMillis();
        mdate = new Date(mnow);
        return mformat.format(mdate);
    }
}