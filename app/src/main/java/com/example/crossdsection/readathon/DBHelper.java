package com.example.crossdsection.readathon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;

/**
 * Created by crossdsection on 9/12/17.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "READathon.db";
//    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("mylog", " DBWORKS ");

        // TODO Auto-generated method stub
        db.execSQL(
                "create table levels " +
                        "(id integer primary key, created text, modified text )"
        );
        db.execSQL(
                "create table user (" +
                            "id integer primary key, " +
                            "name text, " +
                            "score integer default 0, " +
                            "level_id integer, " +
                            "created text, " +
                            "modified text )"
        );
        db.execSQL(
                "create table stories (" +
                            "id integer primary key, " +
                            "story text, " +
                            "level_id text, " +
                            "created text, " +
                            "modified text )"
        );
        db.execSQL(
                "create table questiontypes (" +
                            "id integer primary key, " +
                            "questiontype text, " +
                            "is_active bool, " +
                            "created text, " +
                            "modified text )"
        );
        db.execSQL(
                "create table questions (" +
                        "id integer primary key, " +
                        "story_id integer, " +
                        "question text, " +
                        "questiontype_id text, " +
                        "answerkeyword text, " +
                        "created text, " +
                        "modified text )"
        );
        db.execSQL(
                "create table answers (" +
                        "id integer primary key, " +
                        "question_id integer, " +
                        "answer text, " +
                        "is_correct integer, " +
                        "created text, " +
                        "modified text )"
        );
        db.execSQL(
                "create table submitted_answers (" +
                        "id integer primary key, " +
                        "user_id integer, " +
                        "question_id integer, " +
                        "answer text, " +
                        "is_correct integer, " +
                        "created text, " +
                        "modified text )"
        );
        db.execSQL(
                "create table story_illustration (" +
                        "id integer primary key, " +
                        "imagepath text, " +
                        "story_id integer, " +
                        "lineno integer, " +
                        "created text, " +
                        "modified text )"
        );
        db.execSQL(
                "create table question_illustration (" +
                        "id integer primary key, " +
                        "imagepath text, " +
                        "question_id integer, " +
                        "created text, " +
                        "modified text )"
        );
        Log.i("Successfully DB Created", "truth");
        insertData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // user levels questiontypes questions answers submitted_answers story_illustration question_illustration
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS stories");
        db.execSQL("DROP TABLE IF EXISTS questiontypes");
        db.execSQL("DROP TABLE IF EXISTS answers");
        db.execSQL("DROP TABLE IF EXISTS submitted_answers");
        db.execSQL("DROP TABLE IF EXISTS story_illustration");
        db.execSQL("DROP TABLE IF EXISTS question_illustration");
        onCreate(db);
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        try {
            URL url = new URL( urlString );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                String jsonString = sb.toString();
//                Log.i( jsonString );

                return new JSONObject(jsonString);
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public boolean insertData () {
        try {
            try {
                JSONObject object = getJSONObjectFromURL( "https://my-json-server.typicode.com/crossdsection/crossdsection.github.io/db" );
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("story", "People ke pateele mein papeete ka achaar");
                contentValues.put("level_id", 0);
                contentValues.put("created", "2016-08-1");
                contentValues.put("modified", "2016-08-1");
                db.insert("stories", null, contentValues);
                return true;
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return false;
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return false;
        }
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stories where id="+id+"", null );
        return res;
    }

//    public int numberOfRows(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
//        return numRows;
//    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

//    public Integer deleteContact (Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("contacts",
//                "id = ? ",
//                new String[] { Integer.toString(id) });
//    }

//    public ArrayList<String> getAllCotacts() {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        return array_list;
//    }
}
