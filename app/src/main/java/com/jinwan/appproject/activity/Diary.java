package com.jinwan.appproject.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.jinwan.appproject.R;

public class Diary extends BaseActivity {

    private Button button;
    private BottomAppBar bottomAppBar;
    private EditText daily_diary_text;

    private boolean isbold = false;
    private boolean isitalic = false;

    private int align = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_diary);

        button = findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        daily_diary_text = findViewById(R.id.daily_diary_text);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String text = daily_diary_text.getText().toString();
                SpannableString spannableString = new SpannableString(text);
//              text font
                if(item.getItemId()==R.id.text_font){
                    showDialog();
                    return true;
                }

//              font size
                else if(item.getItemId()==R.id.font_size){
                    return true;
                }

//              isbold
                else if (item.getItemId() == R.id.text_bold) {
                    return true;
                }
                else if (item.getItemId() == R.id.text_italic) {
                    return true;
                }

//              underline
                else if(item.getItemId()==R.id.text_underline){
                    return true;
                }

//              text align
                else if(item.getItemId()==R.id.text_align){
                    Drawable drawable;
                    if (align == 0) {
                        drawable = getResources().getDrawable(R.drawable.format_align_center_48px, null);
                        daily_diary_text.setGravity(Gravity.CENTER_HORIZONTAL);
                        item.setIcon(drawable);
                        align = 1;
                    } else if (align == 1) {
                        drawable = getResources().getDrawable(R.drawable.format_align_right_48px, null);
                        daily_diary_text.setGravity(Gravity.RIGHT);

                        item.setIcon(drawable);
                        align = 2;
                    } else if (align ==2){
                        drawable = getResources().getDrawable(R.drawable.format_align_left_48px, null);
                        daily_diary_text.setGravity(Gravity.LEFT);

                        item.setIcon(drawable);
                        align = 0;
                    }
                    return true;
                }
//              저장 버튼
                else if(item.getItemId()==R.id.btn_check){
                    return true;
                }
                return false;
            }
        });
    }


}
