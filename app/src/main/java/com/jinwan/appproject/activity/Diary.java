package com.jinwan.appproject.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.jinwan.appproject.R;
import com.jinwan.appproject.dialog.TextFontDialog;


public class Diary extends BaseActivity {

    private Button button;
    private BottomAppBar bottomAppBar;
    private EditText daily_diary_text;

    private int align = 0;
    private int textSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_diary);

        button = findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        daily_diary_text = findViewById(R.id.daily_diary_text);


        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {

            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

//              text font
                if(item.getItemId()==R.id.text_font)
                {

                    new TextFontDialog(Diary.this).show();
                    return true;
                }

//              font size
                else if(item.getItemId()==R.id.font_size)
                {
                    // 현재 textSize 값에 따라 설정할 크기를 배열로 정의
                    float[] textSizes = {22, 24, 26, 28, 30, 10, 12, 14, 16, 18, 20};

                    if (textSize == 0) {
                        daily_diary_text.setTextSize(2, textSizes[textSize]);
                        textSize = (textSize + 1) % 11;
                    } else {

                        daily_diary_text.setTextSize(2, textSizes[textSize]);
                        textSize = (textSize + 1) % 11;
                    }
                    Log.d("TTag",textSize+"");
                    return true;
                }

//              bold
                else if (item.getItemId() == R.id.text_bold)
                {
                    applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.BOLD));

                    return true;
                }

//              italic
                else if (item.getItemId() == R.id.text_italic)
                {
                    applyStyleToSelectedText(daily_diary_text, new StyleSpan(Typeface.ITALIC));
                    return true;
                }

//              underline
                else if(item.getItemId()==R.id.text_underline)
                {

                    applyStyleToSelectedText(daily_diary_text, new UnderlineSpan());
                    return true;
                }

//              text align
                else if(item.getItemId()==R.id.text_align)
                {
                    Drawable drawable;
                    if (align == 0)
                    {
                        drawable = getResources().getDrawable(R.drawable.format_align_center_48px, null);
                        daily_diary_text.setGravity(Gravity.CENTER_HORIZONTAL);
                        item.setIcon(drawable);
                        align = 1;
                    }

                    else if (align == 1)
                    {
                        drawable = getResources().getDrawable(R.drawable.format_align_right_48px, null);
                        daily_diary_text.setGravity(Gravity.RIGHT);

                        item.setIcon(drawable);
                        align = 2;
                    }

                    else if (align ==2)
                    {
                        drawable = getResources().getDrawable(R.drawable.format_align_left_48px, null);
                        daily_diary_text.setGravity(Gravity.LEFT);

                        item.setIcon(drawable);
                        align = 0;
                    }
                    return true;
                }

//              save
                else if(item.getItemId()==R.id.btn_check)
                {
                    return true;
                }
                return false;

            }

        });

    }
    // 선택된 텍스트에 스타일 적용하는 메서드
    private void applyStyleToSelectedText(EditText editText, Object span) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if (start < end) {
            Spannable spannable = editText.getText();
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setText(spannable);
            editText.setSelection(end); // 커서를 끝으로 이동
        }
    }
}
