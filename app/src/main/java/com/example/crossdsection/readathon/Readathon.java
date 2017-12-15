package com.example.crossdsection.readathon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chitra on 14/12/17.
 */

public class Readathon extends Application {

    private static Readathon mInstance;
    private static SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Readathon getInstance(){
        return mInstance;
    }

    public static SharedPreferences getPref(){
        pref = mInstance.getSharedPreferences(getInstance().getString(R.string.app_name), Context.MODE_PRIVATE);
        return pref;
    }

}
