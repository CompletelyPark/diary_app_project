package com.jinwan.appproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jinwan.appproject.list.Mission;

import java.util.ArrayList;
import java.util.List;

public class MissionDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "missions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "missions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MEMO = "memo";
    private static final String COLUMN_TIME_FIRST = "time_first";
    private static final String COLUMN_TIME_LAST = "time_last";
    private static final String COLUMN_DATE = "date";

    public MissionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MEMO + " TEXT,"
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

    public void addMission(Mission mission, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, mission.getMemo2());
        values.put(COLUMN_TIME_FIRST, mission.getTimeFirst2());
        values.put(COLUMN_TIME_LAST, mission.getTimeLast2());
        values.put(COLUMN_DATE, date);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 미션 수정 메서드
    public int updateMission(int id, Mission updatedMission) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, updatedMission.getMemo2());
        values.put(COLUMN_TIME_FIRST, updatedMission.getTimeFirst2());
        values.put(COLUMN_TIME_LAST, updatedMission.getTimeLast2());

        // 해당 ID의 미션 업데이트
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 미션 삭제 메서드
    public void deleteMission(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 특정 날짜의 모든 미션을 불러올 때 ID도 포함
    public List<Mission> getMissionsByDate(String date) {
        List<Mission> missions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_MEMO, COLUMN_TIME_FIRST, COLUMN_TIME_LAST},
                COLUMN_DATE + "=?", new String[]{date}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Mission mission = new Mission(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)), // ID 추가
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_FIRST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_LAST))
                );
                missions.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return missions;
    }
}
