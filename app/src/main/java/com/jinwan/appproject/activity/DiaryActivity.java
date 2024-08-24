package com.jinwan.appproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;
import com.jinwan.appproject.database.DiaryDatabaseHelper;
import com.jinwan.appproject.database.DiaryOutDatabaseHelper;
import com.jinwan.appproject.list.DiaryEntry;


public class DiaryActivity extends BaseActivity {

    private EditText title_text;
    private EditText daily_diary_text;
    private int align = 0;
    private int textSize = 0;
    private boolean isBold = false;
    private boolean isItalic = false;
    private int weatherCnt = 0;
    private int imageWeather = R.drawable.sunny_48px;
    DiaryDatabaseHelper diaryDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);
        title_text = findViewById(R.id.title_text);
        daily_diary_text = findViewById(R.id.daily_diary_text);

//        diaryOutDatabaseHelper = new DiaryOutDatabaseHelper(getApplicationContext());
        diaryDatabaseHelper = new DiaryDatabaseHelper(getApplicationContext());
        Button button = findViewById(R.id.btn_back);
        button.setOnClickListener(view -> finish());

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(view -> {
            String fontname =  (String) daily_diary_text.getTag();
            DiaryEntry diaryEntry = new DiaryEntry(
                    0,
                    title_text.getText().toString(),
                    daily_diary_text.getText().toString(),
                    fontname,
                    (int) daily_diary_text.getTextSize(),
                    isBold,    // BOLD 여부 확인
                    isItalic,  // ITALIC 여부 확인
                    imageWeather
            );
            diaryDatabaseHelper.addDiaryEntry(diaryEntry);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_diaryEntry", diaryEntry);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {

            if (item.getItemId()==R.id.text_font) {
                show_font_dialog(DiaryActivity.this, daily_diary_text);
                return true;
            }
            else if (item.getItemId()== R.id.font_size) {
                adjustFontSize();
                return true;
            }
            else if (item.getItemId()==R.id.text_bold) {
                if (!isBold) {
                    applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.BOLD));
                    isBold = true;
                }
                else {
                removeStyleFromSelectedText(daily_diary_text, StyleSpan.class);
                isBold = false;
            }
                return true;
            }
            else if (item.getItemId()== R.id.text_italic) {
                if(!isItalic) {
                    applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.ITALIC));
                    isItalic =true;
                }
                else{
                    removeStyleFromSelectedText(daily_diary_text, StyleSpan.class);
                    isItalic = false;
                }
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

    private void applyStyleToSelectedText(EditText editText, Object span) {

            Spannable spannable = editText.getText();
            spannable.setSpan(span, 0, editText.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            editText.setText(spannable);
            editText.setSelection(editText.getText().toString().length()); // 커서를 끝으로 이동

    }

    private void removeStyleFromSelectedText(EditText editText, Class<?> spanClass) {
        Spannable spannable = editText.getText();
        StyleSpan[] spans = spannable.getSpans(0, editText.getText().toString().length(), StyleSpan.class);

        for (StyleSpan span : spans) {
            if (spanClass.isInstance(span)) {
                spannable.removeSpan(span);
            }
        }

        editText.setText(spannable);
        editText.setSelection(editText.getText().toString().length()); // 커서를 끝으로 이동
    }

    public static void show_font_dialog(Context context, EditText editText) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_font, null);

        TextView fontView1 = dialogView.findViewById(R.id.font1);
        TextView fontView2 = dialogView.findViewById(R.id.font2);
        TextView fontView3 = dialogView.findViewById(R.id.font3);
        TextView fontView4 = dialogView.findViewById(R.id.font4);
        TextView fontView5= dialogView.findViewById(R.id.font5);
        TextView fontView6= dialogView.findViewById(R.id.font6);
        TextView fontView7= dialogView.findViewById(R.id.font7);

        Typeface font1 = ResourcesCompat.getFont(context, R.font.roboto);
        Typeface font2 = ResourcesCompat.getFont(context, R.font.bazzi);
        Typeface font3 = ResourcesCompat.getFont(context, R.font.dnfforgedblade_light);
        Typeface font4 = ResourcesCompat.getFont(context, R.font.mabinogi_classic);
        Typeface font5 = ResourcesCompat.getFont(context, R.font.maplestory_light);
        Typeface font6 = ResourcesCompat.getFont(context, R.font.nexon_kart_gothic_medium);
        Typeface font7 = ResourcesCompat.getFont(context, R.font.nexon_lv2_gothic);



        fontView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font1,"roboto");
            }
        });
        fontView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font2,"bazzi");
            }
        });
        fontView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font3,"dnfforgedblade_light");
            }
        });
        fontView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font4,"mabinogi_classic");
            }
        });
        fontView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font5,"maplestory_light");
            }
        });
        fontView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font6,"nexon_kart_gothic_medium");
            }
        });
        fontView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFont(editText,font7,"nexon_lv2_gothic");
            }
        });


        builder.setTitle("글꼴 선택")
                .setView(dialogView)
                .setPositiveButton("확인",  (dialogInterface, i) -> {


                })
                .setNegativeButton("취소", null)
                .show();
    }




    private static void changeFont(EditText editText, Typeface font,String fontname) {
        Typeface typeface = Typeface.create(font, Typeface.NORMAL);
        editText.setTypeface(typeface);
        editText.setTag(fontname);
    }
}