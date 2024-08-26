package com.jinwan.appproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.Nullable;
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
import com.jinwan.appproject.list.DiaryEntry;

public class MainActivity extends BaseActivity {
    private FragmentManager fragmentManager;

    private boolean ishome = true;
    private boolean istheme = true;

    MainFragment mainFragment;
    CalendarFragment calendarFragment;
    DiaryFragment diaryFragment;
    DailyFragment dailyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent lockIntent = new Intent(this, LockActivity.class);
        startActivity(lockIntent);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        int colorOnPrimary = getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary);
        toolbar.setTitleTextColor(colorOnPrimary);
        toolbar.setTitle(DateHelper.getCurrentDateFormattedMonthDay());
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        mainFragment = new MainFragment();
        calendarFragment = new CalendarFragment();
        diaryFragment = new DiaryFragment();
        dailyFragment = new DailyFragment();

//      기본 프래그먼트
        loadFragment(mainFragment);

        Button btn_month = findViewById(R.id.btn_month);
        btn_month.setOnClickListener(v ->{
            loadFragmentWithHomeCheck(calendarFragment);
        });

        Button btn_daily = findViewById(R.id.btn_daily);
        btn_daily.setOnClickListener(v -> loadFragmentWithHomeCheck(dailyFragment));

        Button btn_diary = findViewById(R.id.btn_diary);
        btn_diary.setOnClickListener(v -> {
            loadFragmentWithHomeCheck(diaryFragment);
        });




    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // 앱이 포그라운드로 돌아올 때 잠금 화면 표시
//        Intent lockIntent = new Intent(this, LockActivity.class);
//        startActivity(lockIntent);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // 다른 설정이 필요한 경우 작성
//    }


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
            startActivity(intent);
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.action_home && ishome){
            ishome = false;
            istheme = true;
            loadFragment(new MainFragment());
        }
        else if(item.getItemId() == R.id.action_lock && ishome){
            ishome = false;
            Intent lockIntent = new Intent(this, LockActivity.class);
            startActivity(lockIntent);
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


    //현재액티비티에서 다른 액티비티를 호출하였을때 호출된 액티비티가 정상 종료하면 아래의 메서드가 실행된다.
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == 100){
            if( resultCode == RESULT_OK){
                //Fragment가 null이 아닌것을 확인하고 필요한 정보를 설정해야 한다.\
                if( data != null) {
                    DiaryEntry new_diaryEntry = (DiaryEntry) data.getSerializableExtra("new_diaryEntry");
//                    Diary_out new_diary_out = (Diary_out) data.getSerializableExtra("new_diary_out");
                    if( diaryFragment != null) {
                        diaryFragment.diaryAdapter.addItem(new_diaryEntry);
                        diaryFragment.diaryAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public void activityCall(){
        Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);

        startActivityForResult(intent, 100);
    }


    /*
        fragment 생명주기 다시 한번 배우자
        oncreateview에서 새로운 activity로 넘어가면 기존 액티비티랑 프래그먼트는 새로운 액티비티 밑에서 동작하게 된다
        그러면 새로운 액티비티에서 기존 액티비티로 돌아올때 값을 넘기고 싶다면
        putExtra를 통해 (키값, 자료형)값을 넘기는데 (자료형에서 serializable을 통해서 복합 자료형을 이용할 수 있고) 
        Activity.setResult 를 통해 result code와 intent data를 넘긴다
        이 후 finish() 를 통해 원래 activity로 돌아온다면 
        이전 fragment에서 보내준 requestcode와 사용하던 activity에서 보내준 resultcode를 확인해서 
        intent data에서 자료형에 맞는 값을 받아오고 
        recyclerview에 아이템을 추가해서
        값이 변경된 것을 알고 화면을 바꿔준다
        
        추가 1 fragment에서는 activityCall을 사용하지말고 fragment에 해당하는 액티비티에서 activitycall method를 정의해서 
        원하는 activity로 넘어가자
        
        추가 2 fragment를 버튼을 누를 때마다 new를 통해서 새롭게 만드는 것이 아닌 미리 변수 선언과 초기화를 해놓고
        원할때 fragment로 갔다가 다시 돌아올때 정보를 갖고 있게 만들자
     */

}
