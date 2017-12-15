package com.example.crossdsection.readathon.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.database.Contract;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.model.Stories;

public class StoriesActivity extends BaseActivity implements View.OnClickListener{

    DBHelper db;
    int level;

    ImageView storyIv;
    TextView storyTv;
    Button questionBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        storyTv = findViewById(R.id.storyTv);
        questionBtn = findViewById(R.id.questionBtn);
        storyIv = findViewById(R.id.storyIv);

        questionBtn.setOnClickListener(this);

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
                String imagePath = cursor.getString(cursor.getColumnIndex(Contract.StoryIllustration.COLUMN_IMAGE_PATH));
                storyTv.setText(stories.getStory());
                Log.d("IMAGE", "" + imagePath);
                if(!TextUtils.isEmpty(imagePath)){
                    Glide.with(mActivity).load(imagePath)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(storyIv);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mActivity, QuestionActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }
}
