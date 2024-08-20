package com.jinwan.appproject.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;
import com.jinwan.appproject.adapter.CelebrityAdapter;
import com.jinwan.appproject.helper.DateHelper;
import com.jinwan.appproject.list.Celebrity;

import java.util.List;

// 기념일 추가 클릭 시 나오는 다이얼로그 아직 미완성
public class AddCelebrityDialog {

    private final Context context;
    private final CelebrityAdapter celebrityAdapter;
    private final List<Celebrity> celebrityList;

    public AddCelebrityDialog(Context context, CelebrityAdapter celebrityAdapter, List<Celebrity> celebrityList) {
        this.context = context;

        this.celebrityAdapter = celebrityAdapter;
        this.celebrityList = celebrityList;
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_celebrity, null);
        TextView today_time = dialogView.findViewById(R.id.today_time);
        today_time.setText(DateHelper.getCurrentDateFormattedMonthDayYear());
        EditText celebrity_memo = dialogView.findViewById(R.id.celebrity_memo);


        builder.setTitle("기념일 추가");
        builder.setView(dialogView);
        builder.setNeutralButton("기념일 삭제", null);
        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            String str_celebrity_memo = celebrity_memo.getText().toString();

            Celebrity newCelebrity = new Celebrity(str_celebrity_memo);
            celebrityList.add(newCelebrity);
            celebrityAdapter.notifyDataSetChanged();

        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
