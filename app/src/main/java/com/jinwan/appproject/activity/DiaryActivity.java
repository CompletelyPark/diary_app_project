package com.jinwan.appproject.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.jinwan.appproject.R;
import com.jinwan.appproject.dialog.FontSelectorDialog;
import com.jinwan.appproject.helper.TextFormattingHelper;
import com.jinwan.appproject.helper.WeatherIconHelper;

public class DiaryActivity extends BaseActivity {

    private Button button;
    private Button btn_save;
    private BottomAppBar bottomAppBar;

    private EditText title_text;
    private EditText daily_diary_text;

    private int align = 0;
    private int textSize = 0;
    private WeatherIconHelper weatherIconHelper;
    private TextFormattingHelper textFormattingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

        button = findViewById(R.id.btn_back);
        button.setOnClickListener(view -> finish());

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title_text = findViewById(R.id.title_text);
        daily_diary_text = findViewById(R.id.daily_diary_text);

        weatherIconHelper = new WeatherIconHelper(this);
        textFormattingHelper = new TextFormattingHelper(daily_diary_text);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {

            if (item.getItemId()==R.id.text_font) {
                FontSelectorDialog.show(DiaryActivity.this, daily_diary_text);
                return true;
            }
            else if (item.getItemId()== R.id.font_size) {
                adjustFontSize();
                return true;
            }
            else if (item.getItemId()==R.id.text_bold) {
                textFormattingHelper.applyStyle(new StyleSpan(Typeface.BOLD));
                return true;
            }
            else if (item.getItemId()== R.id.text_italic) {
                textFormattingHelper.applyStyle(new StyleSpan(Typeface.ITALIC));
                return true;
            }
            else if (item.getItemId()==R.id.text_underline) {
                textFormattingHelper.applyStyle(new UnderlineSpan());
                return true;
            }
            else if (item.getItemId()==R.id.text_align) {
                adjustTextAlignment(item);
                return true;
            }
             else if (item.getItemId()== R.id.weather){
                weatherIconHelper.updateWeatherIcon(item);
                return true;
            }
            return false;
        });

    }


    private void adjustFontSize() {
        float[] textSizes = {22, 24, 26, 28, 30, 10, 12, 14, 16, 18, 20};
        daily_diary_text.setTextSize(2, textSizes[textSize]);
        textSize = (textSize + 1) % textSizes.length;
    }

    private void adjustTextAlignment(MenuItem item) {
        Drawable drawable;
        switch (align) {
            case 0:
                drawable = getResources().getDrawable(R.drawable.format_align_center_48px, null);
                daily_diary_text.setGravity(Gravity.CENTER_HORIZONTAL);
                align = 1;
                break;
            case 1:
                drawable = getResources().getDrawable(R.drawable.format_align_right_48px, null);
                daily_diary_text.setGravity(Gravity.RIGHT);
                align = 2;
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.format_align_left_48px, null);
                daily_diary_text.setGravity(Gravity.LEFT);
                align = 0;
                break;
        }
        item.setIcon(drawable);
    }


}
