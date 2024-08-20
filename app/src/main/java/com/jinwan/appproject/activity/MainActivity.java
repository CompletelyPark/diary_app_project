package com.jinwan.appproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jinwan.appproject.R;
import com.jinwan.appproject.fragment.CalendarFragment;
import com.jinwan.appproject.fragment.MainFragment;
import com.jinwan.appproject.fragment.DailyFragment;
import com.jinwan.appproject.fragment.DiaryFragment;
import com.jinwan.appproject.helper.DateHelper;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private Button btn_month, btn_daily, btn_diary;

    private boolean ishome = true;
    private boolean istheme = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        int colorOnPrimary = getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary);
        toolbar.setTitleTextColor(colorOnPrimary);
        toolbar.setTitle(DateHelper.getCurrentDateFormattedMonthDay());
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        // Load the default fragment
        loadFragment(new MainFragment());

        btn_month = findViewById(R.id.btn_month);
        btn_month.setOnClickListener(v -> loadFragmentWithHomeCheck(new CalendarFragment()));

        btn_daily = findViewById(R.id.btn_daily);
        btn_daily.setOnClickListener(v -> loadFragmentWithHomeCheck(new DailyFragment()));

        btn_diary = findViewById(R.id.btn_diary);
        btn_diary.setOnClickListener(v -> loadFragmentWithHomeCheck(new DiaryFragment()));
    }

    // 툴바 메뉴 활성화 method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // activity 이동 method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_theme && istheme) {
            ishome = true;
            istheme = false;
            Intent intent = new Intent(this, ThemeChoiceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.action_home && ishome){
            ishome = false;
            istheme = true;
            loadFragment(new MainFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    // Fragment 로드 시 홈 체크를 위한 메서드
    private void loadFragmentWithHomeCheck(Fragment fragment) {
        ishome = true;
        istheme = true;
        loadFragment(fragment);
    }

    // Fragment load method
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); // 애니메이션 추가
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // 색상 가져오는 method
    private int getColorFromAttr(int attr) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}
