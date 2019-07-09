package tech.waxen.was.Modal.Subject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SubjectHandler extends SQLiteOpenHelper {
    public  final static String TAG = SubjectHandler.class.getSimpleName();

    private static final String DATABASE_NAME = "was";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "subject";

    private static final String S_ID = "s_id";
    private static final String S_NAME = "s_name";
    private static final String S_LENGTH = "s_length";
    private static final String S_DESCRIPTION = "s_description";
    private static final String S_CREATE_AT = "s_create_at";
    private static final String S_END_AT = "s_end_at";

    public SubjectHandler(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, S_ID, S_NAME, S_LENGTH, S_DESCRIPTION, S_CREATE_AT, S_END_AT);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long add(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_NAME, subject.getsName());
        values.put(S_LENGTH, subject.getsLength());
        values.put(S_DESCRIPTION, subject.getsDescription());
        values.put(S_CREATE_AT, subject.getsCreateAt());
        values.put(S_END_AT, subject.getsEndAt());

        long res = db.insert(TABLE_NAME, null, values);
        db.close();
        return res;
    }

    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
//            Log.e(TAG, "getAll: Nè " + String.format("%d - %s - %s - %s - %s - %s",cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            Subject subject = new Subject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            subjects.add(subject);
            cursor.moveToNext();
        }
        db.close();
        return subjects;
    }

    public Subject get(int sId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, S_ID + " = ?", new String[]{ String.valueOf(sId)}, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            return new Subject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        db.close();
        return null;
    }

    public int update(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_NAME, subject.getsName());
        values.put(S_LENGTH, subject.getsLength());
        values.put(S_DESCRIPTION, subject.getsDescription());
        values.put(S_END_AT, subject.getsEndAt());

        int res = db.update(TABLE_NAME, values, S_ID + " = ?", new String[]{ String.valueOf(subject.getsId()) });
        db.close();
        return res;
    }

    public int delete(int sId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, S_ID + " = ?", new String[]{ String.valueOf(sId) });
        db.close();
        return res;
    }
}
