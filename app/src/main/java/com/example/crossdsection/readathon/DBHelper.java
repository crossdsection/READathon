package com.example.crossdsection.readathon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;
import java.util.Locale;

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
                            "level_id integer, " +
                            "created text, " +
                            "modified text )"
        );
        db.execSQL(
                "create table questiontypes (" +
                            "id integer primary key, " +
                            "questiontype text, " +
//                            "is_active bool, " +
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
        Log.d("Successfully DB Created", "truth");
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

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean insertData ( SQLiteDatabase db, String data ) throws JSONException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject object = new JSONObject( data );
        JSONObject user = (JSONObject) object.get("user");
        JSONArray questiontypes = (JSONArray) object.get("questiontypes");
        JSONArray contents = (JSONArray) object.get("content");

        for( int i=0; i < contents.length(); i++ ){
            JSONObject storyObj = contents.getJSONObject(i);
            JSONArray questions = (JSONArray) storyObj.get("questions");
            JSONArray storyIllus = (JSONArray) storyObj.get("images");

            ContentValues storyValues = new ContentValues();
            storyValues.put( "id", i);
            storyValues.put( "story", (String) storyObj.get("story"));
            storyValues.put( "level_id", (int) storyObj.get("level_id"));
            storyValues.put( "created", getDateTime() );
            storyValues.put( "modified", getDateTime());
            db.insert("stories", null, storyValues );
        }

        ContentValues userValues = new ContentValues();
        userValues.put( "id", (Integer) user.get("id"));
        userValues.put( "name", (String) user.get("name"));
        userValues.put( "score", (Integer) user.get("score"));
        userValues.put( "level_id", (Integer) user.get("level_id"));
        userValues.put( "created", (Integer) user.get("created"));
        userValues.put( "modified", (Integer) user.get("modified"));
        db.insert("users", null, userValues );

        ContentValues questionTypeValues = new ContentValues();
        for (int i = 0; i < questiontypes.length(); i++) {
            questionTypeValues.put( "id", i );
            questionTypeValues.put( "questiontype", (String)questiontypes.get(i) );
            questionTypeValues.put( "created", getDateTime() );
            questionTypeValues.put( "modified", getDateTime() );
            db.insert("questiontypes", null, questionTypeValues );
        }
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stories where level_id = " + id + "", null );
        return res;
    }


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
