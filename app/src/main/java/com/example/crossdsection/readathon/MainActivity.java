package com.example.crossdsection.readathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DBHelper db;

    Button btnLevel1, btnLevel2, btnLevel3, btnLevel4, btnLevel5;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Stetho.initializeWithDefaults(this);

        db = new DBHelper(getApplicationContext());
        db.getWritableDatabase();
        ApiGetStories newApi = new ApiGetStories( getApplicationContext(), db.getWritableDatabase() );
        newApi.getData( db.getWritableDatabase() );

        btnLevel1 = findViewById(R.id.btn_level1);
        btnLevel2 = findViewById(R.id.btn_level2);
        btnLevel3 = findViewById(R.id.btn_level3);
        btnLevel4 = findViewById(R.id.btn_level4);
        btnLevel5 = findViewById(R.id.btn_level5);

        btnLevel1.setOnClickListener(this);
        btnLevel2.setOnClickListener(this);
        btnLevel3.setOnClickListener(this);
        btnLevel4.setOnClickListener(this);
        btnLevel5.setOnClickListener(this);
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
            case R.id.btn_level1 :
                btnLevel1.setVisibility(View.VISIBLE);
                btnLevel2.setVisibility(View.GONE);
                btnLevel3.setVisibility(View.GONE);
                btnLevel4.setVisibility(View.GONE);
                btnLevel5.setVisibility(View.GONE);

                goToStoryScreen(1);
                break;
            case R.id.btn_level2 :
                btnLevel2.setVisibility(View.VISIBLE);
                btnLevel1.setVisibility(View.GONE);
                btnLevel3.setVisibility(View.GONE);
                btnLevel4.setVisibility(View.GONE);
                btnLevel5.setVisibility(View.GONE);
                break;
            case R.id.btn_level3 :
                btnLevel3.setVisibility(View.VISIBLE);
                btnLevel2.setVisibility(View.GONE);
                btnLevel1.setVisibility(View.GONE);
                btnLevel4.setVisibility(View.GONE);
                btnLevel5.setVisibility(View.GONE);
                break;
            case R.id.btn_level4 :
                btnLevel4.setVisibility(View.VISIBLE);
                btnLevel2.setVisibility(View.GONE);
                btnLevel3.setVisibility(View.GONE);
                btnLevel1.setVisibility(View.GONE);
                btnLevel5.setVisibility(View.GONE);
                break;
            case R.id.btn_level5 :
                btnLevel5.setVisibility(View.VISIBLE);
                btnLevel2.setVisibility(View.GONE);
                btnLevel3.setVisibility(View.GONE);
                btnLevel4.setVisibility(View.GONE);
                btnLevel1.setVisibility(View.GONE);
                break;
        }
    }

    public void goToStoryScreen(int level){
        Intent intent = new Intent(this, StoriesActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

}
