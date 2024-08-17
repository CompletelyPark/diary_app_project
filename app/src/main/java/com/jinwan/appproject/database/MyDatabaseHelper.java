package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.schedule.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //  db 정보
    private static final String DATABASE_NAME = "mydatabase.db"; // 데이터베이스 이름
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전

    //  테이블 및 칼럼 정보
    private static final String TABLE_NAME = "schedules";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MEMO = "memo";
    private static final String COLUMN_DAY_FIRST = "dayFirst";
    private static final String COLUMN_DAY_LAST = "dayLast";
    private static final String COLUMN_TIME_FIRST = "timeFirst";
    private static final String COLUMN_TIME_LAST = "timeLast";

//  today
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String todayDate = sdf.format(new Date());

    // 테이블 생성 SQL문
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MEMO + " TEXT, " +
            COLUMN_DAY_FIRST + " TEXT, " +
            COLUMN_DAY_LAST + " TEXT, " +
            COLUMN_TIME_FIRST + " TEXT, " +
            COLUMN_TIME_LAST + " TEXT );";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//      db 처음 호출 시 호출
//      테이블 생성
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 필요할 때 호출 (스키마 변경 등)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // 테이블 재생성
    }

    public long addSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
//      데이터베이스에 삽입할 데이터를 담기 위한 객체, 키-값 쌍으로 데이터를 저장
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, schedule.getName());
        values.put(COLUMN_MEMO, schedule.getMemo());
        values.put(COLUMN_DAY_FIRST, schedule.getDayFirst());
        values.put(COLUMN_DAY_LAST, schedule.getDayLast());
        values.put(COLUMN_TIME_FIRST, schedule.getTimeFirst());
        values.put(COLUMN_TIME_LAST, schedule.getTimeLast());

//      데이터 삽입 (insert method 를 사용해서 TABLE_NAME 테이블에 values에서 정의한 데이터 삽입
//      삽입된 레코드의 id 반환
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Schedule> getTodaySchedules(String todayDate) {
        List<Schedule> scheduleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 날짜가 오늘과 일치하는 일정을 가져오기 위한 쿼리
        String selection = COLUMN_DAY_FIRST + " <= ? AND " + COLUMN_DAY_LAST + " >= ?";
        String[] selectionArgs = {todayDate, todayDate};

        Cursor cursor = db.query(
                TABLE_NAME,
                null,  // 모든 열을 선택
                selection,  // WHERE 조건절
                selectionArgs,  // 조건절에 전달할 인수
                null,
                null,
                null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String memo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO));
                String dayFirst = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_FIRST));
                String dayLast = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_LAST));
                String timeFirst = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_FIRST));
                String timeLast = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_LAST));

                Schedule schedule = new Schedule(name, memo, dayFirst, dayLast, timeFirst, timeLast);
                scheduleList.add(schedule);
            }
            cursor.close();
        }
        db.close();
        return scheduleList;
    }


    // 특정 스케줄 삭제
    public int deleteSchedule(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{name});
        db.close();
        return deletedRows;
    }

    // 스케줄 업데이트
    public int updateSchedule(String name, Schedule newSchedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, newSchedule.getMemo());
        values.put(COLUMN_DAY_FIRST, newSchedule.getDayFirst());
        values.put(COLUMN_DAY_LAST, newSchedule.getDayLast());
        values.put(COLUMN_TIME_FIRST, newSchedule.getTimeFirst());
        values.put(COLUMN_TIME_LAST, newSchedule.getTimeLast());

        int updatedRows = db.update(TABLE_NAME, values, COLUMN_NAME + " = ?", new String[]{name});
        db.close();
        return updatedRows;
    }

}
