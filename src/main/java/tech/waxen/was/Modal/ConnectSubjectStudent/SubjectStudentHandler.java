package tech.waxen.was.Modal.ConnectSubjectStudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tech.waxen.was.Modal.Student.StudentHandler;

public class SubjectStudentHandler extends SQLiteOpenHelper {
    public final static String TAG = StudentHandler.class.getSimpleName();

    private static final String DATABASE_NAME = "was_subject_student";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "subject_student";

    private static final String SS_ID = "c_id";
    private static final String S_ID = "st_id";
    private static final String ST_ID = "st_code";
    private static final String SS_HAVE = "st_name";

    public SubjectStudentHandler(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER, %s INTEGER)", TABLE_NAME, SS_ID, S_ID, ST_ID, SS_HAVE);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long add(ConnectSubjectStudent subjectStudent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(S_ID, subjectStudent.sId);
        values.put(ST_ID, subjectStudent.stId);
        values.put(SS_HAVE, subjectStudent.ssHave);

        long res = db.insert(TABLE_NAME, null, values);
        db.close();
        return res;
    }

    public List<ConnectSubjectStudent> getAll(int sId) {
        List<ConnectSubjectStudent> subjectStudents = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, S_ID + " = ?", new String[]{String.valueOf(sId)}, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ConnectSubjectStudent subjectStudent = new ConnectSubjectStudent(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
            subjectStudents.add(subjectStudent);
            cursor.moveToNext();
        }
        db.close();
        return subjectStudents;
    }

    public int getMaxAttendanceCount(int sId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{SS_HAVE}, S_ID + " = ?", new String[]{String.valueOf(sId)}, null, null, SS_HAVE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            return cursor.getInt(0);
        }
        db.close();
        return 0;
    }

    public int update(ConnectSubjectStudent subjectStudent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SS_HAVE, subjectStudent.ssHave);

        int res = db.update(TABLE_NAME, values, SS_ID + " = ? ", new String[]{String.valueOf(subjectStudent.ssId)});
        db.close();
        return res;
    }

    public int delete(int ssId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, SS_ID + " = ? ", new String[]{String.valueOf(ssId)});
        db.close();
        return res;
    }
}
