package com.jinwan.appproject.diary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import com.jinwan.appproject.R;

public class WeatherIconManager {

    private Context context;
    private int weatherCnt = 0;

    public WeatherIconManager(Context context) {
        this.context = context;
    }

    public void updateWeatherIcon(MenuItem item) {
        int[] weatherIcons = {
                R.drawable.partly_cloudy_day_48px,
                R.drawable.cloud_48px,
                R.drawable.rainy_48px,
                R.drawable.cloudy_snowing_48px,
                R.drawable.sunny_48px
        };

        Drawable drawable = context.getResources().getDrawable(weatherIcons[weatherCnt], null);
        item.setIcon(drawable);
        weatherCnt = (weatherCnt + 1) % weatherIcons.length;
    }
}
