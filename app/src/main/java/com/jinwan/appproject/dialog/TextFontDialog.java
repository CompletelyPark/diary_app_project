package com.jinwan.appproject.dialog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;

public class TextFontDialog {

    private final Context context;

    public TextFontDialog(Context context){
        this.context = context;
    }

    public void show(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.font_dialog,null);

        builder.setTitle("글꼴 선택");
        builder.setView(dialogView);
        builder.setPositiveButton("확인",(dialogInterface, i) -> {
            TextView font1;
            font1 = dialogView.findViewById(R.id.font1);
            font1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TTTT","click1");
                }
            });
            TextView font2;
            font2 = dialogView.findViewById(R.id.font2);
            font2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TTTT", "click2");
                }
            });

            TextView font3;
            font3 = dialogView.findViewById(R.id.font3);
            font3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TTTT", "click3");
                }
            });

            TextView font4;
            font4 = dialogView.findViewById(R.id.font4);
            font4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TTTT", "click4");
                }
            });
            TextView font5;
            font5 = dialogView.findViewById(R.id.font5);
            font5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TTTT", "click5");
                }
            });


        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
