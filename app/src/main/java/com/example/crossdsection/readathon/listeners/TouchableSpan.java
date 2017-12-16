package com.example.crossdsection.readathon.listeners;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by chitra on 16/12/17.
 */

public abstract class TouchableSpan extends ClickableSpan {
    String clicked;
    String word;

    public TouchableSpan(String clicked) {
        super();
        this.clicked = clicked;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    abstract public void onClick(View view);
}
