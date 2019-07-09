package tech.waxen.was.Modal.wClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class wClassHandler extends SQLiteOpenHelper {
    public final static String TAG = wClassHandler.class.getSimpleName();

    private static final String DATABASE_NAME = "was_class";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "wclass";

    private static final String C_ID = "c_id";
    private static final String C_NAME = "c_name";
    private static final String C_SCHOOL_YEAR = "C_SCHOOL_YEAR";

    public wClassHandler(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, C_ID, C_NAME, C_SCHOOL_YEAR);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long addClass(wClass wclass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_NAME, wclass.cName);
        values.put(C_SCHOOL_YEAR, wclass.cSchoolYear);

        long res = db.insert(TABLE_NAME, null, values);
        db.close();
        return res;
    }

    public List<wClass> getAllClass() {
//        Log.d(TAG, "getAllClass: Đã zô đây!");
        List<wClass> wClasses = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            wClass wclass = new wClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            wClasses.add(wclass);
            cursor.moveToNext();
        }
        db.close();
        return wClasses;
    }

    public wClass get(int cId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, C_ID + " = ?", new String[] { String.valueOf(cId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            return new wClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        db.close();
        return null;
    }

    public int update(wClass wclass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_NAME, wclass.cName);
        values.put(C_SCHOOL_YEAR, wclass.cSchoolYear);

        int res = db.update(TABLE_NAME, values, C_ID + " = ?", new String[] {String.valueOf(wclass.cId)});
        db.close();
        return res;
    }

    public int delete(int cId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, C_ID + " = ?", new String[]{ String.valueOf(cId) });
        db.close();
        return res;
    }
}
