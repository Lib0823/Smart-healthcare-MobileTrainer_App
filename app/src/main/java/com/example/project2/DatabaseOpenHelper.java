package com.example.project2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String tableName = "Users";
    public static final String tableNameBoard = "Board";
    public static final String tableNameRecord = "Record";

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","db 생성_db가 없을때만 최초로 실행함");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }

    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(id text, pw text, name text, age integer, height integer, weight integer, gender text, run integer, login text, date text)";
        String sql2 = "CREATE TABLE " + tableNameBoard + "(id text, content text, field text)";
        String sql3 = "CREATE TABLE " + tableNameRecord + "(id text, date text, time integer, distance double, step integer, kcal double)";
        try {
            db.execSQL(sql);
            db.execSQL(sql2);
            db.execSQL(sql3);
        }catch (SQLException e){
        }
    }

    public void insertRecord(SQLiteDatabase db, String id, String date, int time, double distance, int step, double kcal){
        Log.i("tag","러닝 종료시 실행 (기록저장)");
        db.beginTransaction();
        try {
            String sql3 = "INSERT INTO " + tableNameRecord + "(id, date, time, distance, step, kcal)" + "values('"+ id +"', '"+ date +"', "+ time +", "+distance+", "+step+", "+kcal+")";
            db.execSQL(sql3);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void insertBoard(SQLiteDatabase db, String id, String content, String field){
        Log.i("tag","게시판 등록했을때 실행함");
        db.beginTransaction();
        try {
            String sql2 = "INSERT INTO " + tableNameBoard + "(id, content, field)" + "values('"+ id +"', '"+ content +"', '"+ field +"')";
            db.execSQL(sql2);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void insertUser(SQLiteDatabase db, String id, String pw, String name, int age, int height, int weight, String gender, int run, String login, String date){
        Log.i("tag","회원가입을 했을때 실행함");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName + "(id, pw, name, age, height, weight, gender, run, login, date)" + "values('"+ id +"', '"+pw+"', '"+name+"', "+age+
                    ", "+height+", "+weight+", '"+gender+"', "+run+", '"+ login +"', '"+ date +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

}
