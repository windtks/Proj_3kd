package com.example.windkts.proj1;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fwaa2 on 2017.11.22.
 */

public class SQLite extends SQLiteOpenHelper {
    private static final String  DB_NAME = "PERSON.DB";//数据库名称
    private static final int DB_VERSION = 1;//数据库版本号
    public static final String TABLE_NAME="PERSONS";
    public SQLite(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                "Id integer primary key, " +
                "name text UNIQUE," +
                "imageId integer NULL," +
                "sex text ," +
                "birthAnddeath text NULL," +
                "hometown text NULL," +
                "force text ," +
                "isliked integer," +
                "comment text NULL," +
                "path text NULL"+
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
