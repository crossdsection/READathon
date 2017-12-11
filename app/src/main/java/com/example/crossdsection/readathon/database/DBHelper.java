package com.example.crossdsection.readathon.database;

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

    //All static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "READathon.db";

    private static final String TEXT_TYPE = " text";
    private static final String INTEGER_TYPE = " integer";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_LEVELS = "create table if not exists " + Contract.Levels.TABLE_NAME + " ( "
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_USER  = "create table if not exists " + Contract.User.TABLE_NAME + " ("
                               + Contract.User.COLUMN_NAME + TEXT_TYPE + COMMA_SEP
                               + Contract.User.COLUMN_SCORE + INTEGER_TYPE + " defualt 0 " + COMMA_SEP
                               + Contract.User.COLUMN_LEVEL_ID + INTEGER_TYPE + COMMA_SEP
                               + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
                               + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_STORIES = "create table if not exists " + Contract.Stories.TABLE_NAME + " ("
            + Contract.Stories.COLUMN_STORY + TEXT_TYPE + COMMA_SEP
            + Contract.Stories.COLUMN_LEVEL_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_QUESTION_TYPES = "create table if not exists "
            + Contract.QuestionTypes.TABLE_NAME + " ("
            + Contract.QuestionTypes.COLUMN_QUESTION_TYPE + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_QUESTION = "create table if not exists "
            + Contract.Questions.TABLE_NAME + " ("
            + Contract.Questions.COLUMN_ANSWER_KEYWORD+ TEXT_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_QUESTION + TEXT_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_QUESTION_TYPE_ID + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_ANSWER = "create table if not exists "
            + Contract.Answers.TABLE_NAME + " ("
            + Contract.Answers.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Answers.COLUMN_ANSWER + TEXT_TYPE + COMMA_SEP
            + Contract.Answers.COLUMN_IS_CORRECT + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_SUBMITTED_ANSWER = "create table if not exists "
            + Contract.SubmittedAnswers.TABLE_NAME + " ("
            + Contract.SubmittedAnswers.COLUMN_USER_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_ANSWER + TEXT_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_IS_CORRECT + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_STORY_ILLUSTRATION = "create table if not exists "
            + Contract.StoryIllustration.TABLE_NAME + " ("
            + Contract.StoryIllustration.COLUMN_IMAGE_PATH + TEXT_TYPE + COMMA_SEP
            + Contract.StoryIllustration.COLUMN_LINE_NO + INTEGER_TYPE + COMMA_SEP
            + Contract.StoryIllustration.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";

    private static  final String SQL_CREATE_QUESTION_ILLUSTRATION = "create table if not exists "
            + Contract.QuestionIllustration.TABLE_NAME + " ("
            + Contract.QuestionIllustration.COLUMN_IMAGE_PATH + TEXT_TYPE + COMMA_SEP
            + Contract.QuestionIllustration.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + COMMA_SEP + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_LEVELS);
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_STORIES);
        db.execSQL(SQL_CREATE_QUESTION_TYPES);
        db.execSQL(SQL_CREATE_QUESTION);
        db.execSQL(SQL_CREATE_ANSWER);
        db.execSQL(SQL_CREATE_SUBMITTED_ANSWER);
        db.execSQL(SQL_CREATE_STORY_ILLUSTRATION);
        db.execSQL(SQL_CREATE_QUESTION_ILLUSTRATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // user levels questiontypes questions answers submitted_answers story_illustration question_illustration
        db.execSQL("DROP TABLE IF EXISTS " + Contract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Stories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.QuestionTypes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Answers.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.SubmittedAnswers.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.StoryIllustration.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.QuestionIllustration.TABLE_NAME);
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
            storyValues.put( Contract.Stories.COLUMN_STORY, (String) storyObj.get("story"));
            storyValues.put( Contract.Stories.COLUMN_LEVEL_ID, (int) storyObj.get("level_id"));
            storyValues.put( Contract.Levels.COLUMN_CREATED, getDateTime() );
            storyValues.put( Contract.Levels.COLUMN_MODIFIED, getDateTime());
            db.insert(Contract.Stories.TABLE_NAME, null, storyValues );
        }

        ContentValues userValues = new ContentValues();
        userValues.put( Contract.User.COLUMN_NAME, (String) user.get("name"));
        userValues.put( Contract.User.COLUMN_SCORE, (Integer) user.get("score"));
        userValues.put( Contract.User.COLUMN_LEVEL_ID, (Integer) user.get("level_id"));
        userValues.put( Contract.Levels.COLUMN_CREATED, (Integer) user.get("created"));
        userValues.put( Contract.Levels.COLUMN_MODIFIED, (Integer) user.get("modified"));
        db.insert(Contract.User.TABLE_NAME, null, userValues );

        ContentValues questionTypeValues = new ContentValues();
        for (int i = 0; i < questiontypes.length(); i++) {
            questionTypeValues.put( Contract.QuestionTypes.COLUMN_QUESTION_TYPE, (String)questiontypes.get(i) );
            questionTypeValues.put( Contract.Levels.COLUMN_CREATED, getDateTime() );
            questionTypeValues.put( Contract.Levels.COLUMN_MODIFIED, getDateTime() );
            db.insert(Contract.QuestionTypes.TABLE_NAME, null, questionTypeValues );
        }
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stories where level_id = " + id + "", null );
        return res;
    }
}
