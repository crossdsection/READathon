package com.example.crossdsection.readathon.util;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by chitra on 16/12/17.
 */

public class CustomeTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomeTextView(Context context) {
        super(context);
    }

    @Override
    public int getOffsetForPosition(float x, float y) {
        return super.getOffsetForPosition(x, y);
    }
}
