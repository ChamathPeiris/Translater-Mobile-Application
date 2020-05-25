package com.example.assignment02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ibm.watson.visual_recognition.v4.model.CollectionObjects;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Coursework.db";
    public static final String TABLE_NAME1 = "phrases";
    public static final String COLUMN_1_1 = "ID";

    public static final String TABLE_NAME2 = "languages";
    public static final String COLUMN_2_1 = "ID";
    public static final String COLUMN_2_2 = "NAME";
    public static final String COLUMN_2_3 = "CODE";
    public static final String COLUMN_2_4 = "ISCHECKED";

    public static final String TABLE_NAME3 = "translation";
    public static final String COLUMN_3_1 = "ID";
    public static final String COLUMN_3_2 = "CODE";
    public static final String COLUMN_3_3 = "TRANSLATE";


    public DatabaseHelper( Context context) {

        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 + " (ID TEXT  ) ");
        db.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,CODE TEXT,ISCHECKED INTEGER )");
        db.execSQL("create table " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CODE TEXT,TRANSLATE TEXT )");
    }


    public boolean addData(String id){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_1_1,id);
        long result = database.insert(TABLE_NAME1,null,values);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean isIDExist(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = null;
        String checkQuery = "SELECT " + COLUMN_1_1 + " FROM " + TABLE_NAME1 + " WHERE " + COLUMN_1_1 + "= '"+id + "'";
        cursor= database.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public Cursor displayAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select "+COLUMN_1_1+" from " + TABLE_NAME1+" ORDER BY " +COLUMN_1_1+ " COLLATE NOCASE ASC;" ,null);
        return cursor;

    }

    public void removeItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME1 + " WHERE " + COLUMN_1_1 + " ='" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + id + " from database.");
        db.execSQL(query);

    }


    public Cursor getPhraseName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " select "+ COLUMN_1_1+ " from "+ TABLE_NAME1+ " where "+ COLUMN_1_1+ "='" + name+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean addLanguage(String name, String code, int isChecked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_2_2,name);
        values.put(COLUMN_2_3,code);
        values.put(COLUMN_2_4,isChecked);
        long result = db.insert(TABLE_NAME2,null,values);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor displayAllLanguages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select "+COLUMN_2_2+" from " + TABLE_NAME2 ,null);
        return cursor;

    }

    public void UpdateCheck(int checkStatus,String language){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_2_4 +
                " = '" + checkStatus + "' WHERE " + COLUMN_2_2 + " = '" + language + "'";
        db.execSQL(query);
    }

    public Cursor isChecked(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select "+COLUMN_2_4+" from " + TABLE_NAME2 ,null);
        return cursor;

    }
    public Cursor getSubscribedLanguages(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select " + COLUMN_2_2 +" from " + TABLE_NAME2 + " where " + COLUMN_2_4 + " = " + " 1 ",null);
        return cursor;
    }
    public Cursor getSelectedLanguageCode(String selectedLanguage){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select " + COLUMN_2_3 +" from " + TABLE_NAME2 + " WHERE " + COLUMN_2_2 + " = '" + selectedLanguage + "'" ,null);
        return cursor;
    }

    public boolean addTranslationData(String code, String translation){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(" insert into " + TABLE_NAME3 +" ( "+ COLUMN_3_2 +" , " + COLUMN_3_3 + " ) " + " values " + " ( " selectedLanguage + "'" ,null);
//        return cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_3_2,code);
        values.put(COLUMN_3_3,translation);
        long result = db.insert(TABLE_NAME3,null,values);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean addCodes(String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_3_2,code);
        long result = db.insert(TABLE_NAME3,null,values);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor displayAllTranslate(String code){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select "+ COLUMN_3_3 +" from " + TABLE_NAME3 + " where " + COLUMN_3_2 + " ='"+code+ "'" ,null);
        System.out.println(" select "+ COLUMN_3_3 +" from " + TABLE_NAME3 + " where " + COLUMN_3_2 + " = ' " +code+ "'");
        return cursor;
    }

    public boolean deleteAllTranslation(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from "+ TABLE_NAME3);
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
