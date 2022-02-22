package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class GTDatabaseClass extends SQLiteOpenHelper {

    Context context;
    private static final String databaseName = "BTL_Android";
    private static final int databaseVersion = 1;
    private static final String tableName = "GlobalTime";
    private static final String idColumn = "id";
    private static final String timezoneColumn = "timezone";

    public GTDatabaseClass(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + idColumn +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + timezoneColumn + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(sqLiteDatabase);
    }

    public void addTimezone (String timezone) { // THÊM MÚI GIỜ
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(timezoneColumn, timezone);

        long resultValue = db.insert(tableName, null, contentValues);

        if (resultValue == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() { // LẤY TOÀN BỘ
        String getAllDataQuery = "SELECT timezone FROM " + tableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(getAllDataQuery, null);
        }

        return cursor;
    }

    public void deleteAllTimezone() { // XÓA TOÀN BỘ
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + tableName;
        database.execSQL(deleteQuery);
    }

    public void deleteSingleTimezone(String id) // XÓA MỘT MÚI GIỜ
    {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(tableName, "id=?", new String[]{id});

        if (result == -1)
        {
            Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
        }
    }
}
