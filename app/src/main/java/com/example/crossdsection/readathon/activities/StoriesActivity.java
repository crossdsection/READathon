package com.example.crossdsection.readathon.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.model.Stories;

public class StoriesActivity extends BaseActivity implements View.OnClickListener{

    DBHelper db;
    int level;

    TextView storyTv;
    Button questionBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        storyTv = findViewById(R.id.storyTv);
        questionBtn = findViewById(R.id.questionBtn);

        db = new DBHelper(getApplicationContext());
        level = getIntent().getIntExtra("level", 1);
        getDataForLevels(level);
    }

    public void getDataForLevels(int level){
        Cursor cursor = db.getData( "story", level );

        while(cursor != null && cursor.moveToNext()){
            if(level == cursor.getInt(cursor.getColumnIndex("level_id"))) {
                Stories stories = new Stories();
                stories.setStory(cursor.getString(cursor.getColumnIndex("story")));
                storyTv.setText(stories.getStory());
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mActivity, QuestionActivity.class);
        startActivity(intent);
    }
}
