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

    public List<Celebrity> getCelebritiesForTodayAndTomorrow() {
        List<Celebrity> celebrities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String todayDate = getTodayDate();
        String tomorrowDate = getTomorrowDate();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SELECTED_DATE + " = ? OR " + COLUMN_SELECTED_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{todayDate, tomorrowDate});
        if (cursor.moveToFirst()) {
            do {
                Celebrity celebrity = new Celebrity(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
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

    // 현재 날짜를 yyyy-MM-dd 형식으로 반환하는 메서드
    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    // 내일 날짜를 yyyy-MM-dd 형식으로 반환하는 메서드
    private String getTomorrowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // 현재 날짜에 1일 추가하여 내일 날짜를 얻음
        return dateFormat.format(calendar.getTime());
    }


}
