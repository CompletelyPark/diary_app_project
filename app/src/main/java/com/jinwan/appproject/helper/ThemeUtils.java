package com.jinwan.appproject.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.jinwan.appproject.R;

public class ThemeUtils {
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "current_theme";

    public static void applyTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int theme = prefs.getInt(KEY_THEME, R.style.Base_Theme_AppProject); // 기본 테마
        activity.setTheme(theme);
    }

    public static void setTheme(Activity activity, int theme) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_THEME, theme);
        editor.apply();
        activity.recreate();
    }
}
