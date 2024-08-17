package com.jinwan.appproject.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;

// 기념일 추가 클릭 시 나오는 다이얼로그 아직 미완성
public class AddCelebrityDialog {

    private final Context context;

    public AddCelebrityDialog(Context context) {
        this.context = context;
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.add_celebrity, null);

        builder.setTitle("기념일 추가");
        builder.setView(dialogView);
        builder.setNeutralButton("기념일 삭제", null);
        builder.setPositiveButton("확인", null);
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
