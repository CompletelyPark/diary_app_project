package com.jinwan.appproject.diary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;

public class FontSelectorDialog {

    private static String[] fontList = {"Roboto", "sans-serif", "serif", "monospace", "sans-serif-light"};

    public static void show(Context context, EditText editText) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.font_dialog, null);

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
