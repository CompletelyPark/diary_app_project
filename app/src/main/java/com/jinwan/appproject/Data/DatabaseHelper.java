//package com.jinwan.appproject.Data;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "schedules.db";
//    private static final int DATABASE_VERSION = 1;
//
//    public static final String TABLE_SCHEDULES = "schedules";
//    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_TITLE = "title";
//    public static final String COLUMN_DATE = "date";
//    public static final String COLUMN_TIME = "time";
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_SCHEDULES_TABLE = "CREATE TABLE " + TABLE_SCHEDULES + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_TITLE + " TEXT,"
//                + COLUMN_DATE + " TEXT,"
//                + COLUMN_TIME + " TEXT" + ")";
//        db.execSQL(CREATE_SCHEDULES_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
//        onCreate(db);
//    }
//
//    public void addSchedule(Schedule schedule) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, schedule.getTitle());
//        values.put(COLUMN_DATE, schedule.getDate());
//        values.put(COLUMN_TIME, schedule.getTime());
//        db.insert(TABLE_SCHEDULES, null, values);
//        db.close();
//    }
//
//    public List<Schedule> getAllSchedules() {
//        List<Schedule> scheduleList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCHEDULES, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Schedule schedule = new Schedule(
//                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
//                );
//                scheduleList.add(schedule);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return scheduleList;
//    }
//
//    public void deleteSchedule(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SCHEDULES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
//        db.close();
//    }
//}
