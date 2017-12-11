package com.example.crossdsection.readathon.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.crossdsection.readathon.util.Utils;

/**
 * Created by chitra on 11/12/17.
 */

public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;
    public Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mActivity = this;
        mUtils = new Utils(mActivity);
    }

}
