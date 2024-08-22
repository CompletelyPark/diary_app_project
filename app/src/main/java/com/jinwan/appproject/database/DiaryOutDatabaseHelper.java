
package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.list.Diary_out;

import java.util.ArrayList;
import java.util.List;

public class DiaryOutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary_outs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "diary_out_list";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE_ID = "image_id";

    public DiaryOutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_IMAGE_ID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDiary_out(Diary_out diary_out) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, diary_out.getTitle());
        values.put(COLUMN_IMAGE_ID, diary_out.getImageId());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 다이어리_아웃 수정 메서드
    public int updateDiary_out(int id, Diary_out updatedDiary_out) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, updatedDiary_out.getTitle());
        values.put(COLUMN_IMAGE_ID, updatedDiary_out.getImageId());

        // 해당 ID의 다이어리_아웃 업데이트
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 다이어리_아웃 삭제 메서드
    public void deleteDiary_out(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 특정 날짜의 모든 다이어리_아웃을 불러올 때 ID도 포함
    public List<Diary_out> getAllDiary_outs() {
        List<Diary_out> diary_out_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 테이블의 모든 행을 선택하는 쿼리
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE_ID},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Diary_out diary_out = new Diary_out(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)), // ID 추가
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID))
                );
                diary_out_list.add(diary_out);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return diary_out_list;
    }
}
