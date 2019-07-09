package tech.waxen.was.Modal.Student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentHandler extends SQLiteOpenHelper {
    public final static String TAG = StudentHandler.class.getSimpleName();

    private static final String DATABASE_NAME = "was_student";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "student";

    private static final String C_ID = "c_id";
    private static final String ST_ID = "st_id";
    private static final String ST_CODE = "st_code";
    private static final String ST_NAME = "st_name";
    private static final String ST_EMAIL = "s_temail";

    public StudentHandler(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s(%s INTEGER, %s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, C_ID, ST_ID, ST_CODE, ST_NAME, ST_EMAIL);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long add(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_ID, student.getcId());
        values.put(ST_CODE, student.getStCode());
        values.put(ST_NAME, student.getStName());
        values.put(ST_EMAIL, student.getStEmail());

        long res = db.insert(TABLE_NAME, null, values);
        db.close();
        return res;
    }

    public Student get(int stId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, ST_ID + " = ?", new String[] { String.valueOf(stId) }, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            return new Student(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        db.close();
        return null;
    }
    public Student get(String stCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, ST_CODE + " = ?", new String[] { stCode }, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            return new Student(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        db.close();
        return null;
    }

    public List<Student> getAll(int cId) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_NAME, null, C_ID + " = ?", new String[]{String.valueOf(cId)}, null, null, null);
        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()) {
            Student student = new Student(mCursor.getInt(0), mCursor.getInt(1), mCursor.getString(2), mCursor.getString(3), mCursor.getString(4));
            students.add(student);
            mCursor.moveToNext();
        }
        db.close();
        return students;
    }

    public int update(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ST_CODE, student.getStCode());
        values.put(ST_NAME, student.getStName());
        values.put(ST_EMAIL, student.getStEmail());

        int res = db.update(TABLE_NAME, values, ST_ID + " = ?", new String[]{String.valueOf(student.getcId())});
        db.close();
        return res;
    }

    public int delete(int stId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, ST_ID + " = ?", new String[]{String.valueOf(stId)});
        db.close();
        return res;
    }
    public int deleteByClass(int cId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, C_ID + " = ?", new String[]{String.valueOf(cId)});
        db.close();
        return res;
    }

}
