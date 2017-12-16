package com.example.crossdsection.readathon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.Readathon;
import com.example.crossdsection.readathon.adapters.MainAdapter;
import com.example.crossdsection.readathon.api.ApiGetStories;
import com.example.crossdsection.readathon.constant.Constants;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.listeners.ClickListener;
import com.example.crossdsection.readathon.listeners.Listener_Success;
import com.example.crossdsection.readathon.listeners.RecyclerTouchListener;
//import com.facebook.stetho.Stetho;

public class MainActivity extends BaseActivity implements View.OnClickListener, Listener_Success {

    DBHelper db;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    int levelCleared;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Stetho.initializeWithDefaults(this);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        levelCleared = Readathon.getInstance().getPref().getInt(Constants.LEVEL_CLEARED, 0);
        db = new DBHelper(getApplicationContext());
        db.getWritableDatabase();
        db.deleteTables();

        ApiGetStories newApi = new ApiGetStories(getApplicationContext(), this);
        newApi.getData( db.getWritableDatabase() );

    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    public void goToStoryScreen(int level) {
        Intent intent = new Intent(mActivity, StoriesActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    @Override
    public void success (int level){
        loadAdapter(level);
    }


    public void loadAdapter(int level){
        progressBar.setVisibility(View.GONE);
        MainAdapter adapter = new MainAdapter(mActivity, level, levelCleared);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mActivity, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mActivity, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(levelCleared >= position){
                    goToStoryScreen(position + 1);
                } else {
                    Toast.makeText(mActivity, "Please Clear level " + position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
