package com.jinwan.appproject;

import android.content.ContentResolver;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class BrightnessManager {
    private ContentResolver contentResolver;
    private Window window;

    public BrightnessManager(ContentResolver contentResolver, Window window) {
        this.contentResolver = contentResolver;
        this.window = window;
    }

    public void initBrightness(SeekBar seekBar, TextView textView) {
        try {
            int brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            seekBar.setProgress(brightness);
            textView.setText(String.valueOf(brightness));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setBrightness(int brightness) {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightness / 255f;
        window.setAttributes(layoutParams);
    }
}


/*
  앱에서 설정에 접근하기 위해서 사용하는 방법은 SettingsProvider의 설정 항목을 쿼리해서 참조 할 수 있도록 만든 Settings 클래스를 이용하는 것
  접근 단순화 해주는 class
  public final class Settings extend Object
  global - MultiUser 를 위한 공용 설정 데이터 접근 용도
  Secure - 보안 관련한 설정 데이터 접근 용도
  System - 시스템 과 관련한 설정 데이터 접근 용도

  밝기 조절 모드
  int brightnessMode = Settings.System.getInt(getContentResolver(),
                                                Settings.System.Screen_BRIGTNESS_MODE);

*/
