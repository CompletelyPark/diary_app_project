package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.list.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "schedules.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "schedules";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MEMO = "memo";
    private static final String COLUMN_DAY_FIRST = "day_first";
    private static final String COLUMN_DAY_LAST = "day_last";
    private static final String COLUMN_TIME_FIRST = "time_first";
    private static final String COLUMN_TIME_LAST = "time_last";
    private static final String COLUMN_DATE = "date";

    public ScheduleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_MEMO + " TEXT,"
                + COLUMN_DAY_FIRST + " TEXT,"
                + COLUMN_DAY_LAST + " TEXT,"
                + COLUMN_TIME_FIRST + " TEXT,"
                + COLUMN_TIME_LAST + " TEXT,"
                + COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSchedule(Schedule schedule, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, schedule.getName());
        values.put(COLUMN_MEMO, schedule.getMemo());
        values.put(COLUMN_DAY_FIRST, schedule.getDayFirst());
        values.put(COLUMN_DAY_LAST, schedule.getDayLast());
        values.put(COLUMN_TIME_FIRST, schedule.getTimeFirst());
        values.put(COLUMN_TIME_LAST, schedule.getTimeLast());
        values.put(COLUMN_DATE, date);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 일정 수정 메서드
    public int updateSchedule(int id, Schedule updatedSchedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, updatedSchedule.getName());
        values.put(COLUMN_MEMO, updatedSchedule.getMemo());
        values.put(COLUMN_DAY_FIRST, updatedSchedule.getDayFirst());
        values.put(COLUMN_DAY_LAST, updatedSchedule.getDayLast());
        values.put(COLUMN_TIME_FIRST, updatedSchedule.getTimeFirst());
        values.put(COLUMN_TIME_LAST, updatedSchedule.getTimeLast());

        // 해당 ID의 일정 업데이트
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 일정 삭제 메서드
    public void deleteSchedule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 특정 날짜의 모든 일정을 불러올 때 ID도 포함
    public List<Schedule> getSchedulesByDate(String date) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        COLUMN_ID,
                        COLUMN_NAME,
                        COLUMN_MEMO,
                        COLUMN_DAY_FIRST,
                        COLUMN_DAY_LAST,
                        COLUMN_TIME_FIRST,
                        COLUMN_TIME_LAST},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)), // ID 추가
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_FIRST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_LAST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_FIRST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_LAST))
                );
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return schedules;
    }

    public List<Schedule> getSchedulesForToday() {
        // 현재 날짜 가져오기
        String todayDate = getTodayDate();

        // 오늘 날짜로 일정 조회
        return getSchedulesByDate(todayDate);
    }

    // 현재 날짜를 yyyy-MM-dd 형식으로 반환하는 메서드
    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
