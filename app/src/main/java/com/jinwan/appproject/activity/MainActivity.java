package com.jinwan.appproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.jinwan.appproject.R;
import com.jinwan.appproject.fragment.CalendarFragment;
import com.jinwan.appproject.fragment.DailyFragment;
import com.jinwan.appproject.fragment.DiaryFragment;
import com.jinwan.appproject.helper.BaseActivity;


public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private Button btn_month, btn_daily, btn_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fragmentManager = getSupportFragmentManager();

        // Load the default fragment

        btn_month = findViewById(R.id.btn_month);
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new CalendarFragment());
            }
        });

        btn_daily = findViewById(R.id.btn_daily);
        btn_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new DailyFragment());
            }
        });

        btn_diary = findViewById(R.id.btn_diary);
        btn_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new DiaryFragment());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_theme) {
            Intent intent = new Intent(this, ThemeChoice.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}