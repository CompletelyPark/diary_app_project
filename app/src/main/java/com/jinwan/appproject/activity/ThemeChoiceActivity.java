package com.jinwan.appproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.jinwan.appproject.R;
import com.jinwan.appproject.helper.ThemeHelper;

public class ThemeChoiceActivity extends BaseActivity {

    ImageButton btn_theme1, btn_theme2, btn_theme3, btn_theme4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_choice);
        btn_theme1 = (ImageButton)  findViewById(R.id.button1);
        btn_theme2 = (ImageButton)  findViewById(R.id.button2);
        btn_theme3 = (ImageButton)  findViewById(R.id.button3);
        btn_theme4 = (ImageButton)  findViewById(R.id.button4);
        btn_theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ThemeHelper.setTheme(ThemeChoiceActivity.this, R.style.Base_Theme_AppProject);}
        });
        btn_theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ThemeHelper.setTheme(ThemeChoiceActivity.this, R.style.Theme2FFC2E);}
        });
        btn_theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ThemeHelper.setTheme(ThemeChoiceActivity.this, R.style.ThemeB59DF7);}
        });
        btn_theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ThemeHelper.setTheme(ThemeChoiceActivity.this, R.style.ThemeFFDE3F);}
        });
    }

    public void onclickBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}