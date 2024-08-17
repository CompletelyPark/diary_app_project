package com.jinwan.appproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jinwan.appproject.R;
import com.jinwan.appproject.helper.BaseActivity;
import com.jinwan.appproject.helper.ThemeUtils;

public class ThemeChoice extends BaseActivity {

    Button btn_theme1, btn_theme2, btn_theme3, btn_theme4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_choice);


        btn_theme1 = (Button) findViewById(R.id.button1);

        btn_theme2 = (Button) findViewById(R.id.button2);
        btn_theme3 = (Button) findViewById(R.id.button3);
        btn_theme4 = (Button) findViewById(R.id.button4);

        btn_theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeUtils.setTheme(ThemeChoice.this, R.style.Base_Theme_AppProject);
            }
        });

        btn_theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeUtils.setTheme(ThemeChoice.this, R.style.Theme2FFC2E);
            }
        });
        btn_theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeUtils.setTheme(ThemeChoice.this, R.style.ThemeB59DF7);
            }
        });
        btn_theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeUtils.setTheme(ThemeChoice.this, R.style.ThemeFFDE3F);
            }
        });
    }

    public void onclickBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}