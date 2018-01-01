package com.example.windkts.proj1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fwaa2 on 2017.11.22.
 */

public class HeroOp {
    private final String[] heros_COL={"Id","name","imageId","sex","birthAnddeath","hometown","force","isliked","comment","path"};
    private Context context;
    private SQLite herosDBHelper;
    private static final String TAG = "HeroOp";
    public HeroOp(Context context){
        this.context=context;
        herosDBHelper = new SQLite(context);
    }

    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = herosDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(herosDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public int getCount(){
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = herosDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(herosDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return count;
    }
    /**
     *插入数据
     */
    public boolean insertData(Person nn) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = herosDBHelper.getReadableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(heros_COL[0],nn.getId());
            contentValues.put(heros_COL[1],nn.getName());
            contentValues.put(heros_COL[2],nn.getImageid());
            contentValues.put(heros_COL[3],nn.getSex());
            contentValues.put(heros_COL[4],nn.getBirthAnddeath());
            contentValues.put(heros_COL[5],nn.getHometown());
            contentValues.put(heros_COL[6],nn.getForce());
            contentValues.put(heros_COL[7],nn.isIs_liked());
            contentValues.put(heros_COL[8],nn.getComment());
            contentValues.put(heros_COL[9],nn.getPath());
            db.insertOrThrow(SQLite.TABLE_NAME,null,contentValues);

            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "已有重名人物", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e(TAG," ",e);
        } finally {
            if(db!=null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public void insertDataList(List<Person> list){
        for(Person nn:list){
            insertData(nn);
        }
    }

    public boolean deleteDataById(int Id){
        SQLiteDatabase db=null;
        try{
            db = herosDBHelper.getReadableDatabase();
            db.beginTransaction();

            db.delete(SQLite.TABLE_NAME,"Id = ? ",new String[]{String.valueOf(Id)});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
            Log.e(TAG," ",e);
        }finally {
            if(db!=null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean deleteDataByName(String text){
        SQLiteDatabase db=null;
        try{
            db = herosDBHelper.getReadableDatabase();
            db.beginTransaction();

            db.delete(SQLite.TABLE_NAME,"name = ? ",new String[]{text});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){
            Log.e(TAG," ",e);
        }finally {
            if(db!=null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public void initTable(){
        SQLiteDatabase db = null;

        try {
            db = herosDBHelper.getWritableDatabase();
            db.beginTransaction();
            int imgid=R.drawable.guanyu;

            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public List<Person> getDataById(int Id){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        try{
            db=herosDBHelper.getReadableDatabase();
            cursor=db.query(SQLite.TABLE_NAME,
                    heros_COL,
                    "Id = ?",
                    new String[]{String.valueOf(Id)},
                    null,null,null);
            if(cursor.getCount()>0){
                List<Person> list=new ArrayList<Person>(cursor.getCount());
                while(cursor.moveToNext()){
                    Person object=parseObject(cursor);
                    list.add(object);
                }
                return list;
            }
        }catch (Exception e){
            Log.e(TAG," ",e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public void upDataData(Person l){
        if (deleteDataById(l.getId())) insertData(l);
        else insertData(l);
    }

    public List<Person> getDataByName(String name){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        try{
            db=herosDBHelper.getReadableDatabase();
            cursor=db.query(SQLite.TABLE_NAME,
                    heros_COL,
                    "Name = ?",
                    new String[]{name},
                    null,null,null);
            if(cursor.getCount()>0){
                List<Person> list=new ArrayList<Person>(cursor.getCount());
                while(cursor.moveToNext()){
                    Person object=parseObject(cursor);
                    list.add(object);
                }
                return list;
            }
        }catch (Exception e){
            Log.e(TAG," ",e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public List<Person> getAllLiked(){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        List<Person> null_list=new ArrayList<>();
        try{
            db=herosDBHelper.getReadableDatabase();
            cursor=db.query(SQLite.TABLE_NAME,
                    heros_COL,
                    "isliked = 1",
                    null,
                    null,null,"name COLLATE LOCALIZED ASC");
            if(cursor.getCount()>0){
                List<Person> list=new ArrayList<Person>(cursor.getCount());
                while(cursor.moveToNext()){
                    Person object=parseObject(cursor);
                    list.add(object);
                }
                return list;
            }
            else{
                return null_list;
            }
        }catch (Exception e){
            Log.e(TAG," ",e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null_list;
    }

    public List<Person> getAllLikeName(String name){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        List<Person> null_list=new ArrayList<>();
        try{
            db=herosDBHelper.getReadableDatabase();
            cursor=db.query(SQLite.TABLE_NAME,
                    heros_COL,
                    "name like ? ",
                    new String[]{"%"+name+"%"},
                    null,null,null);
            if(cursor.getCount()>0){
                List<Person> list=new ArrayList<Person>(cursor.getCount());
                while(cursor.moveToNext()){
                    Person object=parseObject(cursor);
                    list.add(object);
                }
                return list;
            }
            else{
                return null_list;
            }
        }catch (Exception e){
            Log.e(TAG," ",e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null_list;
    }



    public List<Person> getAllData(){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        List<Person> null_list=new ArrayList<>();
        try{
            db=herosDBHelper.getReadableDatabase();
            //cursor = db.execSQL("SELECT * FROM PERSONS order by name COLLATE LOCALIZED ASC;");
            cursor=db.query(SQLite.TABLE_NAME,heros_COL,null,null,null,null,"name COLLATE LOCALIZED ASC");
            if(cursor.getCount()>0){
                List<Person> list=new ArrayList<Person>(cursor.getCount());
                while(cursor.moveToNext()){
                    Person object=parseObject(cursor);
                    list.add(object);
                }
                return list;
            }
            else{

                return null_list;
            }
        }catch (Exception e){
            Log.e(TAG," ",e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null_list;
    }



    private Person parseObject(Cursor cursor){
        Person object = new Person();
        object.setId(cursor.getInt(cursor.getColumnIndex(heros_COL[0])));
        object.setName(cursor.getString(cursor.getColumnIndex(heros_COL[1])));
        object.setImageid(cursor.getInt(cursor.getColumnIndex(heros_COL[2])));
        object.setSex(cursor.getString(cursor.getColumnIndex(heros_COL[3])));
        object.setBirthAnddeath(cursor.getString(cursor.getColumnIndex(heros_COL[4])));
        object.setHometown(cursor.getString(cursor.getColumnIndex(heros_COL[5])));
        object.setForce(cursor.getString(cursor.getColumnIndex(heros_COL[6])));
        object.setIs_liked(cursor.getInt(cursor.getColumnIndex(heros_COL[7])));
        object.setComment(cursor.getString(cursor.getColumnIndex(heros_COL[8])));
        object.setPath(cursor.getString(cursor.getColumnIndex(heros_COL[9])));
        return object;
    }

}
