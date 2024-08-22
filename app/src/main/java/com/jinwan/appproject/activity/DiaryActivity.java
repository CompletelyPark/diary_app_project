package com.jinwan.appproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;
import com.jinwan.appproject.database.DiaryOutDatabaseHelper;
import com.jinwan.appproject.dialog.FontSelectorDialog;
import com.jinwan.appproject.helper.TextFormattingHelper;
import com.jinwan.appproject.list.Diary_out;


public class DiaryActivity extends BaseActivity {

    private EditText title_text;
    private EditText daily_diary_text;

    private int align = 0;
    private int textSize = 0;
    private TextFormattingHelper textFormattingHelper;

    private int weatherCnt = 0;

    private int imageWeather = R.drawable.sunny_48px;
    DiaryOutDatabaseHelper diaryOutDatabaseHelper;

    private static String[] fontList = {"Roboto", "sans-serif", "serif", "monospace", "sans-serif-light"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

        title_text = findViewById(R.id.title_text);
        daily_diary_text = findViewById(R.id.daily_diary_text);

        textFormattingHelper = new TextFormattingHelper(daily_diary_text);
        diaryOutDatabaseHelper = new DiaryOutDatabaseHelper(getApplicationContext());

        Button button = findViewById(R.id.btn_back);
        button.setOnClickListener(view -> finish());

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(view -> {
            String str_title = title_text.getText().toString();
            int weather_icon = imageWeather;
            Diary_out new_diary_out = new Diary_out(str_title,weather_icon);
            diaryOutDatabaseHelper.addDiary_out(new_diary_out);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_diary_out", new_diary_out);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {

            if (item.getItemId()==R.id.text_font) {
                show(DiaryActivity.this, daily_diary_text);
                return true;
            }
            else if (item.getItemId()== R.id.font_size) {
                adjustFontSize();
                return true;
            }
            else if (item.getItemId()==R.id.text_bold) {
                applyStyle(daily_diary_text, new StyleSpan(Typeface.BOLD));
                return true;
            }
            else if (item.getItemId()== R.id.text_italic) {
                applyStyle(daily_diary_text, new StyleSpan(Typeface.ITALIC));
                return true;
            }
            else if (item.getItemId()==R.id.text_underline) {
                applyStyle(daily_diary_text, new UnderlineSpan());
                return true;
            }
            else if (item.getItemId()==R.id.text_align) {
                adjustTextAlignment(item);
                return true;
            }

//          weathericon 클릭 시 이미지 변경과 image의  값 설정하는 코드
            else if (item.getItemId()== R.id.weather){
                Drawable drawable;
                if(weatherCnt==0){
                    drawable = getResources().getDrawable(R.drawable.partly_cloudy_day_48px, null);
                    imageWeather = R.drawable.partly_cloudy_day_48px;
                    item.setIcon(drawable);
                    weatherCnt = (weatherCnt+1)%5;
                }

                else if(weatherCnt==1){
                    drawable = getResources().getDrawable(R.drawable.cloud_48px, null);
                    imageWeather = R.drawable.cloud_48px;
                    item.setIcon(drawable);
                    weatherCnt = (weatherCnt+1)%5;
                }
                else if(weatherCnt==2){
                    drawable = getResources().getDrawable(R.drawable.rainy_48px, null);
                    imageWeather = R.drawable.rainy_48px;
                    item.setIcon(drawable);
                    weatherCnt = (weatherCnt+1)%5;
                }
                else if(weatherCnt==3){
                    drawable = getResources().getDrawable(R.drawable.cloudy_snowing_48px, null);
                    imageWeather = R.drawable.cloudy_snowing_48px;
                    item.setIcon(drawable);
                    weatherCnt = (weatherCnt+1)%5;
                }
                else if(weatherCnt==4){
                    drawable = getResources().getDrawable(R.drawable.sunny_48px, null);
                    imageWeather = R.drawable.sunny_48px;
                    item.setIcon(drawable);
                    weatherCnt = (weatherCnt+1)%5;
                }
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
                daily_diary_text.setGravity(Gravity.END);
                align = 2;
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.format_align_left_48px, null);
                daily_diary_text.setGravity(Gravity.START);
                align = 0;
                break;
        }
        item.setIcon(drawable);
    }
    private void applyStyle(EditText editText, Object span) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if (start < end) {
            Spannable spannable = editText.getText();
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setText(spannable);
            editText.setSelection(end);
        }
    }

    public static void show(Context context, EditText editText) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_font, null);

        TextView[] fontViews = {
                dialogView.findViewById(R.id.font1),
                dialogView.findViewById(R.id.font2),
                dialogView.findViewById(R.id.font3),
                dialogView.findViewById(R.id.font4),
                dialogView.findViewById(R.id.font5)
        };

        for (int i = 0; i < fontViews.length; i++) {
            final int index = i;
            fontViews[i].setOnClickListener(view -> changeFont(editText, fontList[index]));
        }

        builder.setTitle("글꼴 선택")
                .setView(dialogView)
                .setPositiveButton("확인", (dialogInterface, i) -> {})
                .setNegativeButton("취소", null)
                .show();
    }

    private static void changeFont(EditText editText, String fontName) {
        Typeface typeface = Typeface.create(fontName, Typeface.NORMAL);
        editText.setTypeface(typeface);
    }
}