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
import com.example.crossdsection.readathon.model.Questiontypes;
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
            + Contract.Levels.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_USER  = "create table if not exists " + Contract.User.TABLE_NAME + " ("
            + Contract.User.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.User.COLUMN_NAME + TEXT_TYPE + COMMA_SEP
            + Contract.User.COLUMN_SCORE + INTEGER_TYPE + " DEFAULT 0" + COMMA_SEP
            + Contract.User.COLUMN_LEVEL_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_STORIES = "create table if not exists " + Contract.Stories.TABLE_NAME + " ("
            + Contract.Stories.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.Stories.COLUMN_STORY + TEXT_TYPE + COMMA_SEP
            + Contract.Stories.COLUMN_LEVEL_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_QUESTION_TYPES = "create table if not exists "
            + Contract.QuestionTypes.TABLE_NAME + " ("
            + Contract.QuestionTypes.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.QuestionTypes.COLUMN_QUESTION_TYPE + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_QUESTION = "create table if not exists "
            + Contract.Questions.TABLE_NAME + " ("
            + Contract.Questions.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.Questions.COLUMN_ANSWER_KEYWORD+ TEXT_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_QUESTION + TEXT_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Questions.COLUMN_QUESTION_TYPE_ID + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_ANSWER = "create table if not exists "
            + Contract.Answers.TABLE_NAME + " ("
            + Contract.Answers.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.Answers.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Answers.COLUMN_ANSWER + TEXT_TYPE + COMMA_SEP
            + Contract.Answers.COLUMN_IS_CORRECT + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_SUBMITTED_ANSWER = "create table if not exists "
            + Contract.SubmittedAnswers.TABLE_NAME + " ("
            + Contract.SubmittedAnswers.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_USER_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_ANSWER + TEXT_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_IS_CORRECT + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_STORY_ILLUSTRATION = "create table if not exists "
            + Contract.StoryIllustration.TABLE_NAME + " ("
            + Contract.StoryIllustration.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.StoryIllustration.COLUMN_IMAGE_PATH + TEXT_TYPE + COMMA_SEP
            + Contract.StoryIllustration.COLUMN_LINE_NO + INTEGER_TYPE + COMMA_SEP
            + Contract.StoryIllustration.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_QUESTION_ILLUSTRATION = "create table if not exists "
            + Contract.QuestionIllustration.TABLE_NAME + " ("
            + Contract.QuestionIllustration.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.QuestionIllustration.COLUMN_IMAGE_PATH + TEXT_TYPE + COMMA_SEP
            + Contract.QuestionIllustration.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_CREATED + TEXT_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";


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

    public boolean insertData ( SQLiteDatabase db, String data ) throws JSONException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject object = new JSONObject( data );
        JSONObject user = (JSONObject) object.get("user");
        JSONArray questiontypes = (JSONArray) object.get("questiontypes");
        JSONArray contents = (JSONArray) object.get("content");

        ContentValues userValues = new ContentValues();
        userValues.put( Contract.User.COLUMN_NAME, (String) user.get("name"));
        userValues.put( Contract.User.COLUMN_SCORE, (Integer) user.get("score"));
        userValues.put( Contract.User.COLUMN_LEVEL_ID, (Integer) user.get("level_id"));
        userValues.put( Contract.Levels.COLUMN_CREATED, (String) user.get("created"));
        userValues.put( Contract.Levels.COLUMN_MODIFIED, (String) user.get("modified"));
        db.insert(Contract.User.TABLE_NAME, null, userValues );

        ContentValues questionTypeValues = new ContentValues();
        for ( int i = 0; i < questiontypes.length(); i++ ) {
            questionTypeValues.put( Contract.QuestionTypes.COLUMN_QUESTION_TYPE, (String)questiontypes.get(i) );
            questionTypeValues.put( Contract.Levels.COLUMN_CREATED, getDateTime() );
            questionTypeValues.put( Contract.Levels.COLUMN_MODIFIED, getDateTime() );
            db.insert( Contract.QuestionTypes.TABLE_NAME, null, questionTypeValues );
        }
        Cursor questionData = getData( "questiontypes", 0 );
        JSONObject questionTypes = new JSONObject();
        if ( questionData.moveToFirst() ) {
            do {
                int id = questionData.getInt(0);
                String questType = questionData.getString(1);
                questionTypes.put( questType, id );
            } while ( questionData.moveToNext() );
        }

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
            int storyId = getLastTransactionId( Contract.Stories.TABLE_NAME, Contract.Stories.COLUMN_ID );

            for( int j=0; j < questions.length(); j++ ){
                JSONObject questionObj = questions.getJSONObject( j );
                JSONArray answers = (JSONArray) questionObj.get("answers");

                ContentValues questionValues = new ContentValues();
                String questionType = (String) questionObj.get("questiontype");
                questionValues.put( Contract.Questions.COLUMN_ANSWER_KEYWORD, (String) questionObj.get("answerkeyword"));
                questionValues.put( Contract.Questions.COLUMN_QUESTION, (String) questionObj.get("question"));
                questionValues.put( Contract.Questions.COLUMN_ANSWER_KEYWORD, (String) questionObj.get("answerkeyword"));
                questionValues.put( Contract.Questions.COLUMN_QUESTION_TYPE_ID, (Integer) questionTypes.get( questionType ));
                questionValues.put( Contract.Questions.COLUMN_STORY_ID, (Integer) storyId);
                questionValues.put( Contract.Levels.COLUMN_CREATED, getDateTime() );
                questionValues.put( Contract.Levels.COLUMN_MODIFIED, getDateTime() );
                db.insert( Contract.Questions.TABLE_NAME, null, questionValues );
                int questionId = getLastTransactionId( Contract.Questions.TABLE_NAME, Contract.Questions.COLUMN_ID );

                for( int k = 0; k < answers.length(); k++ ){
                    JSONObject ansObj = answers.getJSONObject( k );
                    ContentValues answerValues = new ContentValues();
                    answerValues.put( Contract.Answers.COLUMN_ANSWER, (String) ansObj.get("answer") );
                    answerValues.put( Contract.Answers.COLUMN_IS_CORRECT, (Integer) ansObj.get("is_correct") );
                    answerValues.put( Contract.Answers.COLUMN_QUESTION_ID, (Integer) questionId );
                    answerValues.put( Contract.Levels.COLUMN_CREATED, getDateTime() );
                    answerValues.put( Contract.Levels.COLUMN_MODIFIED, getDateTime() );
                    db.insert( Contract.Answers.TABLE_NAME, null, answerValues );
                }
            }
        }
        return true;
    }

    public Cursor getData(String tabname, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        if( id == 0 ){
            res = db.rawQuery("select * from " + tabname, null);
        } else {
            res = db.rawQuery("select * from " + tabname + " where level_id = " + id + "", null);
        }
        return res;
    }

    public int getLastTransactionId( String tablename, String primarykey ) {
        int _id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename + " ORDER BY " + primarykey + " DESC LIMIT 1 ", null );
        if (cursor.moveToLast()) {
            _id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return _id;
    }
}
