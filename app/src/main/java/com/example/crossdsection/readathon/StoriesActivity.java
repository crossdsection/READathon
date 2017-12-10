package com.example.crossdsection.readathon;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends AppCompatActivity {

    DBHelper db;
    int level;

    TextView storyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        storyTv = findViewById(R.id.storyTv);

        db = new DBHelper(getApplicationContext());
        level = getIntent().getIntExtra("level", 1);
        getDataForLevels(level);
    }

    public void getDataForLevels(int level){
        Cursor cursor = db.getData(level);

        while(cursor != null && cursor.moveToNext()){
            if(level == cursor.getInt(cursor.getColumnIndex("level_id"))) {
                Stories stories = new Stories();
                stories.setStory(cursor.getString(cursor.getColumnIndex("story")));
                storyTv.setText(stories.getStory());
            }

        }
    }
}
