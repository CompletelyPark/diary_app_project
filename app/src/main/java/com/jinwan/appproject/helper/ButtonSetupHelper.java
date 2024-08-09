package com.jinwan.appproject.helper;

import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import com.jinwan.appproject.fragment.DailyFragment;
import com.jinwan.appproject.fragment.DiaryFragment;
import com.jinwan.appproject.fragment.CalendarFragment;

public class ButtonSetupHelper {

    public ButtonSetupHelper(FragmentActivity activity, int monthButtonId, int dailyButtonId, int diaryButtonId, FragmentLoader fragmentLoader) {
        Button btnMonth = activity.findViewById(monthButtonId);
        Button btnDaily = activity.findViewById(dailyButtonId);
        Button btnDiary = activity.findViewById(diaryButtonId);

        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoader.loadFragment(new CalendarFragment());
            }
        });

        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoader.loadFragment(new DailyFragment());
            }
        });

        btnDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoader.loadFragment(new DiaryFragment());
            }
        });
    }
}
