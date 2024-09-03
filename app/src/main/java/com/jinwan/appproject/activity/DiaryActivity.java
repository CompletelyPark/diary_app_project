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
import com.jinwan.appproject.helper.ThemeHelper;
import com.jinwan.appproject.list.DiaryEntry;
import java.util.Date;

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
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);
        title_text = findViewById(R.id.title_text);
        daily_diary_text = findViewById(R.id.daily_diary_text);

//        diaryOutDatabaseHelper = new DiaryOutDatabaseHelper(getApplicationContext());
        diaryDatabaseHelper = new DiaryDatabaseHelper(getApplicationContext());
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        Intent intent = getIntent();
        DiaryEntry diaryEntry1 = (DiaryEntry) intent.getSerializableExtra("diaryEntry");

        if (diaryEntry1!=null){

            title_text.setText(diaryEntry1.getTitle());
            daily_diary_text.setText(diaryEntry1.getContent());

            String str1 = String.valueOf(title_text.getText());
            String str2 = String.valueOf(daily_diary_text.getText());
            Log.d("Tag",str1);
            Log.d("Tag",str2);

            daily_diary_text.setTextSize(diaryEntry1.getFontSize());
            isBold = diaryEntry1.isBold();
            isItalic = diaryEntry1.isItalic();
            imageWeather = diaryEntry1.getWeatherIcon();

            if (isBold) {
                applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.BOLD));
            }
            if (isItalic) {
                applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.ITALIC));
            }

            daily_diary_text.setTag(diaryEntry1.getFont());
            String fontName = diaryEntry1.getFont();
            if (fontName != null) {
                daily_diary_text.setTypeface(ResourcesCompat.getFont(this, getFontResourceId(fontName)));
            }

        }

        Date currentDate = new Date();

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(view -> {
            String fontname = (String) daily_diary_text.getTag();
            int entryId = diaryEntry1 != null ? diaryEntry1.getId() : 0;

            DiaryEntry diaryEntry = new DiaryEntry(
                    entryId,  // 기존 DiaryEntry의 ID 유지
                    title_text.getText().toString(),
                    daily_diary_text.getText().toString(),
                    fontname,
                    (int) daily_diary_text.getTextSize(),
                    isBold,    // BOLD 여부 확인
                    isItalic,  // ITALIC 여부 확인
                    imageWeather,
                    currentDate
            );

            Intent resultIntent = new Intent();
            if (diaryEntry1 != null) {
                // 기존 엔트리 업데이트
                diaryDatabaseHelper.updateDiaryEntry(diaryEntry1.getId(), diaryEntry);
                resultIntent.putExtra("new_diaryEntry", diaryEntry);
            } else {
                // 새로운 엔트리 추가
                diaryDatabaseHelper.addDiaryEntry(diaryEntry);
                resultIntent.putExtra("new_diaryEntry", diaryEntry);
            }

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

    private int getFontResourceId(String fontName) {
        if (fontName == null) {
            // 기본 폰트를 설정하거나 적절한 조치를 취할 수 있습니다.
            return R.font.roboto; // 기본값을 설정
        }

        switch (fontName) {
            case "roboto":
                return R.font.roboto;
            case "bazzi":
                return R.font.bazzi;
            case "dnfforgedblade_light":
                return R.font.dnfforgedblade_light;
            case "mabinogi_classic":
                return R.font.mabinogi_classic;
            case "maplestory_light":
                return R.font.maplestory_light;
            case "nexon_kart_gothic_medium":
                return R.font.nexon_kart_gothic_medium;
            case "nexon_lv2_gothic":
                return R.font.nexon_lv2_gothic;
            default:
                // 기본값을 설정
                return R.font.roboto;
        }
    }


    private static void changeFont(EditText editText, Typeface font,String fontname) {
        Typeface typeface = Typeface.create(font, Typeface.NORMAL);
        editText.setTypeface(typeface);
        editText.setTag(fontname);
    }
}