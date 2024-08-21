package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.list.Celebrity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class CelebrityDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "celebrities.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "celebrities";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MEMO = "memo";
    private static final String COLUMN_SELECTED_DATE = "selected_date";

    private static final String COLUMN_DATE = "date";

    public CelebrityDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MEMO + " TEXT,"
                + COLUMN_SELECTED_DATE + " TEXT,"
                + COLUMN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//  기념일 추가 메서드
    public void addCelebrity(Celebrity celebrity, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, celebrity.getMemo());
        values.put(COLUMN_SELECTED_DATE, celebrity.getDate());
        values.put(COLUMN_DATE, date);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 기념일 수정 메서드
    public int updateCelebrity(int id, Celebrity updatedCelebrity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, updatedCelebrity.getMemo());
        values.put(COLUMN_SELECTED_DATE, updatedCelebrity.getDate());

        // 해당 ID의 기념일 업데이트
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 기념일 삭제 메서드
    public void deleteCelebrity(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 특정 날짜의 모든 기념일을 불러올 때 ID도 포함
    public List<Celebrity> getCelebritiesByDate(String date) {
        List<Celebrity> celebrities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                COLUMN_ID,
                COLUMN_MEMO,
                COLUMN_SELECTED_DATE,
                },
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Celebrity celebrity = new Celebrity(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)), // ID 추가
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SELECTED_DATE))
                );
                celebrities.add(celebrity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return celebrities;
    }

    public List<Celebrity> getCelebritiesForToday() {
        // 현재 날짜 가져오기
        String todayDate = getTodayDate();

        // 오늘 날짜로 기념일 조회
        return getCelebritiesByDate(todayDate);
    }

    // 현재 날짜를 yyyy-MM-dd 형식으로 반환하는 메서드
    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
