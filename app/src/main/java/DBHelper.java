
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS stories");
        onCreate(db);
    }

    public boolean insertStories (String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
//        db.insert("contacts", null, contentValues);
        return true;
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
