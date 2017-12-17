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

import com.example.crossdsection.readathon.model.Answers;
import com.example.crossdsection.readathon.model.Questiontypes;
import java.net.URL;
import java.util.List;
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
            + Contract.Questions.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
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
            + Contract.Answers.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.Levels.COLUMN_MODIFIED + TEXT_TYPE + ")";

    private static  final String SQL_CREATE_SUBMITTED_ANSWER = "create table if not exists "
            + Contract.SubmittedAnswers.TABLE_NAME + " ("
            + Contract.SubmittedAnswers.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_USER_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_QUESTION_ID + INTEGER_TYPE + COMMA_SEP
            + Contract.SubmittedAnswers.COLUMN_STORY_ID + INTEGER_TYPE + COMMA_SEP
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
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Levels.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Questions.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Stories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.QuestionTypes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Answers.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.SubmittedAnswers.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.StoryIllustration.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.QuestionIllustration.TABLE_NAME);
        onCreate(db);
    }

    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Contract.Levels.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.User.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.Stories.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.QuestionTypes.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.Questions.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.Answers.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.StoryIllustration.TABLE_NAME);
        db.execSQL("DELETE FROM " + Contract.QuestionIllustration.TABLE_NAME);
    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean insertData ( SQLiteDatabase db, String data ) throws JSONException, ParseException {

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

            for(int l=0 ; l<storyIllus.length(); l++){
                JSONObject storyIllusObject = (JSONObject) storyIllus.get(l);
                ContentValues storyIllusValues = new ContentValues();
                storyIllusValues.put(Contract.StoryIllustration.COLUMN_STORY_ID, (int) storyObj.get("level_id"));
                storyIllusValues.put(Contract.StoryIllustration.COLUMN_LINE_NO, (int) storyIllusObject.get("lineno"));
                storyIllusValues.put(Contract.StoryIllustration.COLUMN_IMAGE_PATH, (String) storyIllusObject.get("url"));
                db.insert(Contract.StoryIllustration.TABLE_NAME, null, storyIllusValues);
            }

            for(int k=0; k< questions.length(); k++){
                JSONObject questionObject = (JSONObject) questions.get(k);
                ContentValues questionValues = new ContentValues();
                questionValues.put(Contract.Questions.COLUMN_STORY_ID, (int) storyObj.get("level_id"));
                questionValues.put(Contract.Questions.COLUMN_QUESTION_ID, k);
                questionValues.put(Contract.Questions.COLUMN_QUESTION_TYPE_ID, (String) questionObject.get("questiontype"));
                questionValues.put(Contract.Questions.COLUMN_QUESTION, (String) questionObject.get("question"));
                db.insert(Contract.Questions.TABLE_NAME, null, questionValues);

                JSONArray answerArr = (JSONArray) questionObject.getJSONArray("answers");
                JSONArray imageArr = (JSONArray) questionObject.getJSONArray("images");

                for(int j=0; j<answerArr.length(); j++){
                    JSONObject answerObject = (JSONObject) answerArr.get(j);
                    ContentValues answerValues = new ContentValues();
                    answerValues.put(Contract.Answers.COLUMN_QUESTION_ID, k);
                    answerValues.put(Contract.Answers.COLUMN_ANSWER, (String) answerObject.get("answer"));
                    answerValues.put(Contract.Answers.COLUMN_IS_CORRECT, (int) answerObject.get("is_correct"));
                    answerValues.put(Contract.Answers.COLUMN_STORY_ID, (int) storyObj.get("level_id"));
                    db.insert(Contract.Answers.TABLE_NAME, null, answerValues);
                }
            }
        }

        ContentValues userValues = new ContentValues();
        userValues.put( Contract.User.COLUMN_NAME, (String) user.get("name"));
        userValues.put( Contract.User.COLUMN_SCORE, (Integer) user.get("score"));
        userValues.put( Contract.User.COLUMN_LEVEL_ID, (Integer) user.get("level_id"));
        userValues.put( Contract.Levels.COLUMN_CREATED, (String) user.get("created"));
        userValues.put( Contract.Levels.COLUMN_MODIFIED, (String) user.get("modified"));
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
        String query = "select * from " + Contract.Stories.TABLE_NAME + " as a"
                + COMMA_SEP + " " + Contract.StoryIllustration.TABLE_NAME + " as b "
                + "where a." + Contract.Stories.COLUMN_LEVEL_ID + "=" + id
                + " and b." + Contract.StoryIllustration.COLUMN_STORY_ID + "=" + id;
        Cursor res =  db.rawQuery( query, null );
        Log.d("query", query);
        return res;
    }

    public Cursor getQuestions(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Contract.Questions.TABLE_NAME
                + " where " + Contract.Questions.COLUMN_STORY_ID + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public List<Answers> getAnswers(int questionId, int storyId){
        List<Answers> answersList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + Contract.Answers.TABLE_NAME + " where "
                + Contract.Answers.COLUMN_STORY_ID + "=" + storyId + " and "
                + Contract.Answers.COLUMN_QUESTION_ID + "=" + questionId;

        Cursor cursor = db.rawQuery(query, null);
        while(cursor != null && cursor.moveToNext()){
            Answers answers = new Answers();
            answers.setQuestionId(questionId);
            answers.setStoryId(questionId);
            answers.setIsCorrect(cursor.getInt(cursor.getColumnIndex(Contract.Answers.COLUMN_IS_CORRECT)));
            answers.setAnswer(cursor.getString(cursor.getColumnIndex(Contract.Answers.COLUMN_ANSWER)));
            answersList.add(answers);
        }

        return answersList;
    }

    public void submitAnswer(int questionId, int storyId, int isCorrect, String answer){
        String query = "select * from " + Contract.SubmittedAnswers.TABLE_NAME + " where "
                + Contract.SubmittedAnswers.COLUMN_QUESTION_ID + "=" + questionId + " and "
                + Contract.SubmittedAnswers.COLUMN_STORY_ID + "=" + storyId;

        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        ContentValues values = new ContentValues();
        values.put(Contract.SubmittedAnswers.COLUMN_IS_CORRECT, isCorrect);
        values.put(Contract.SubmittedAnswers.COLUMN_USER_ID, 0);
        values.put(Contract.SubmittedAnswers.COLUMN_QUESTION_ID, questionId);
        values.put(Contract.SubmittedAnswers.COLUMN_ANSWER, answer);
        values.put(Contract.SubmittedAnswers.COLUMN_STORY_ID, storyId);
        if(cursor != null && cursor.getCount() > 0){
            String where = Contract.SubmittedAnswers.COLUMN_QUESTION_ID + "= ? and "
                    + Contract.SubmittedAnswers.COLUMN_STORY_ID + "= ?";
            this.getWritableDatabase().update(Contract.SubmittedAnswers.TABLE_NAME, values, where, new String[]{String.valueOf(questionId), String.valueOf(storyId)});
        } else {
            this.getWritableDatabase().insert(Contract.SubmittedAnswers.TABLE_NAME, null, values);
        }
    }

    public int getResult(int level){
        String query = "select count(" + Contract.SubmittedAnswers.COLUMN_IS_CORRECT
                + ") from " + Contract.SubmittedAnswers.TABLE_NAME
                + " where " + Contract.SubmittedAnswers.COLUMN_STORY_ID + "=" + level;
        Cursor cursor = this.getWritableDatabase().rawQuery(query, null);
        return  cursor != null ? cursor.getCount() : 0;
    }
}
