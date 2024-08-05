package com.jinwan.appproject;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;


//  seekbar class create and abstract class set
//  해당 함수는 여러 seekbar에서 사용할 수 있게 모듈화를 진행하고 추가로 추상 method 로 설정해
//  원하는 기능을 구현해서 사용할 수 있도록 작성

public abstract class CustomSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    private TextView seekBarTxt;

    public CustomSeekBarChangeListener(TextView seekBarTxt) {
        this.seekBarTxt = seekBarTxt;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        Log.d("Tag", "onProgressChanged: " + progress);
        seekBarTxt.setText(String.valueOf(progress));
//      처음에 seekBarTxt.setText(progress); 로 적었다가 계속 에러가 나서 문제점을 찾아봤다
//      textview의 setText method는 String 객체로 집어 넣어 줘야 한다는 것을 알게 됐다
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d("Tag", "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("Tag", "onStopTrackingTouch: " + seekBar.getProgress());
    }
}
