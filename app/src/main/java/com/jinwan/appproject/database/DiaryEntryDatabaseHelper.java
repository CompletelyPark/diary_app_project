//package com.jinwan.appproject.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.jinwan.appproject.list.DiaryEntry;
//import com.jinwan.appproject.list.Mission;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DiaryEntryDatabaseHelper extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "diaryentries.db";
//    private static final int DATABASE_VERSION = 1;
//
//    private static final String TABLE_NAME = "diaryentries";
//    // COLUMN_ID 값을 diaryentries db의 기본키로 설정하면서,
//    private static final String COLUMN_ID = "id";
//    private static final String COLUMN_TITLE = "title";
//    private static final String COLUMN_CONTENT = "content";
//    private static final String COLUMN_FONT = "font";
//    private static final String COLUMN_FONT_SIZE = "font_size";
//    private static final String COLUMN_IS_BOLD = "is_bold";
//    private static final String COLUMN_IS_ITALIC = "is_italic";
//    private static final String COLUMN_IS_UNDERLINE = "is_underline";
//    private static final String COLUMN_WEATHER_ICON = "weather_icon";
//
//
//    public DiaryEntryDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_TITLE + " TEXT,"
//                + COLUMN_CONTENT + " TEXT,"
//                + COLUMN_FONT + " TEXT,"
//                + COLUMN_FONT_SIZE + " TEXT,"
//                + COLUMN_IS_BOLD + "TEXT, "
//                + COLUMN_IS_ITALIC + "TEXT, "
//                + COLUMN_IS_UNDERLINE + "TEXT, "
//                + COLUMN_WEATHER_ICON + "TEXT " + ")";
//        db.execSQL(CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public void addDiaryEntry(DiaryEntry diaryEntry) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, diaryEntry.getTitle());
//        values.put(COLUMN_CONTENT, diaryEntry.getContent());
//        values.put(COLUMN_FONT, diaryEntry.getFont());
//        values.put(COLUMN_FONT_SIZE, diaryEntry.getFontSize());
//        values.put(COLUMN_IS_BOLD, diaryEntry.isBold()?1:0);
//        values.put(COLUMN_IS_ITALIC, diaryEntry.isItalic()?1:0);
//        values.put(COLUMN_IS_UNDERLINE, diaryEntry.isUnderline()?1:0);
//        values.put(COLUMN_WEATHER_ICON, diaryEntry.getWeatherIcon());
//
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
//
//    // 미션 수정 메서드
//    public int updateDiaryEntry(int id, DiaryEntry updatedDiaryEntry) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, updatedDiaryEntry.getTitle());
//        values.put(COLUMN_CONTENT, updatedDiaryEntry.getContent());
//        values.put(COLUMN_FONT, updatedDiaryEntry.getFont());
//        values.put(COLUMN_FONT_SIZE, updatedDiaryEntry.getFontSize());
//        values.put(COLUMN_IS_BOLD, updatedDiaryEntry.isBold() ? 1 : 0);
//        values.put(COLUMN_IS_ITALIC, updatedDiaryEntry.isItalic() ? 1 : 0);
//        values.put(COLUMN_IS_UNDERLINE, updatedDiaryEntry.isUnderline() ? 1 : 0);
//        values.put(COLUMN_WEATHER_ICON, updatedDiaryEntry.getWeatherIcon());
//
//
//        // 해당 ID의 미션 업데이트
//        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(updatedDiaryEntry.getId())});
//    }
//
//    // 미션 삭제 메서드
//    public void deleteDiaryEntry(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//        db.close();
//    }
//
//    public DiaryEntry getDiaryEntry(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[]{
//                COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_FONT, COLUMN_FONT_SIZE,
//                COLUMN_IS_BOLD,COLUMN_IS_ITALIC, COLUMN_IS_UNDERLINE, COLUMN_WEATHER_ICON},
//                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        DiaryEntry diaryEntry = new DiaryEntry(
//                cursor.getInt(0),
//                cursor.getString(1),
//                cursor.getString(2),
//                cursor.getString(3),
//                cursor.getInt(4),
//                cursor.getInt(5) == 1,
//                cursor.getInt(6) == 1,
//                cursor.getInt(7) == 1,
//                cursor.getInt(8)
//        );
//
//        cursor.close();
//        db.close();
//
//        return diaryEntry;
//    }
//}
