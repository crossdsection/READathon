package com.example.crossdsection.readathon;

import com.example.crossdsection.readathon.DBHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d("mylog", " LOG WORKS ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("mylog", " LOG WORKS ");

        db = new DBHelper(getApplicationContext());
        db.getWritableDatabase();

        Log.d("mylog", " LOG WORKS ");
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
}
