package com.jinwan.appproject.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jinwan.appproject.helper.ThemeHelper;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
    }
}
