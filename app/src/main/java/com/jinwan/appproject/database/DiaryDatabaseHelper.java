package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.list.DiaryEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;

    // 테이블 및 컬럼 이름 정의
    private static final String TABLE_DIARY = "diary_entries";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_FONT = "font";
    private static final String COLUMN_FONT_SIZE = "font_size";
    private static final String COLUMN_IS_BOLD = "is_bold";
    private static final String COLUMN_IS_ITALIC = "is_italic";
    private static final String COLUMN_WEATHER_ICON = "weather_icon";
    private static final String COLUMN_DATE = "date";  // 날짜 컬럼 추가

    public DiaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIARY_TABLE = "CREATE TABLE " + TABLE_DIARY + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_FONT + " TEXT, "
                + COLUMN_FONT_SIZE + " INTEGER, "
                + COLUMN_IS_BOLD + " INTEGER, "
                + COLUMN_IS_ITALIC + " INTEGER, "
                + COLUMN_WEATHER_ICON + " INTEGER,"
                + COLUMN_DATE + " INTEGER)";  // 날짜 컬럼 추가
        db.execSQL(CREATE_DIARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        onCreate(db);
    }

    // 다이어리 항목 추가
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, diaryEntry.getTitle());
        values.put(COLUMN_CONTENT, diaryEntry.getContent());
        values.put(COLUMN_FONT, diaryEntry.getFont());
        values.put(COLUMN_FONT_SIZE, diaryEntry.getFontSize());
        values.put(COLUMN_IS_BOLD, diaryEntry.isBold() ? 1 : 0);
        values.put(COLUMN_IS_ITALIC, diaryEntry.isItalic() ? 1 : 0);
        values.put(COLUMN_WEATHER_ICON, diaryEntry.getWeatherIcon());
        values.put(COLUMN_DATE, diaryEntry.getDate().getTime());


        db.insert(TABLE_DIARY, null, values);
        db.close();
    }

    // 다이어리 항목 조회
    public DiaryEntry getDiaryEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DIARY, new String[] {
                        COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_FONT,
                        COLUMN_FONT_SIZE, COLUMN_IS_BOLD, COLUMN_IS_ITALIC,
                        COLUMN_WEATHER_ICON },
                COLUMN_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        DiaryEntry diaryEntry = new DiaryEntry(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5) == 1,
                cursor.getInt(6) == 1,
                cursor.getInt(7),
                new Date(cursor.getLong(8))
        );

        cursor.close();
        db.close();

        return diaryEntry;
    }

    // 다이어리 항목 업데이트
    public int updateDiaryEntry(int id, DiaryEntry diaryEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, diaryEntry.getTitle());
        values.put(COLUMN_CONTENT, diaryEntry.getContent());
        values.put(COLUMN_FONT, diaryEntry.getFont());
        values.put(COLUMN_FONT_SIZE, diaryEntry.getFontSize());
        values.put(COLUMN_IS_BOLD, diaryEntry.isBold() ? 1 : 0);
        values.put(COLUMN_IS_ITALIC, diaryEntry.isItalic() ? 1 : 0);
        values.put(COLUMN_WEATHER_ICON, diaryEntry.getWeatherIcon());
        values.put(COLUMN_DATE, diaryEntry.getDate().getTime());
        return db.update(TABLE_DIARY, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // 다이어리 항목 삭제
    public void deleteDiaryEntry(DiaryEntry diaryEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DIARY, COLUMN_ID + " = ?",
                new String[] { String.valueOf(diaryEntry.getId()) });
        db.close();
    }

    // 모든 다이어리 항목 조회
    public List<DiaryEntry> getAllDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_DIARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DiaryEntry diaryEntry = new DiaryEntry(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5) == 1,
                        cursor.getInt(6) == 1,
                        cursor.getInt(7),
                        new Date(cursor.getLong(8))
                );
                diaryEntries.add(diaryEntry);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return diaryEntries;
    }
}
