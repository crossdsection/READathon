package com.example.crossdsection.readathon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.crossdsection.readathon.activities.MainActivity;

/**
 * Created by chitra on 11/12/17.
 */

public class Utils {

    Activity mActivity;

    public Utils(Activity mActivity){
        this.mActivity = mActivity;
    }

    public static void goToMainScreen(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
