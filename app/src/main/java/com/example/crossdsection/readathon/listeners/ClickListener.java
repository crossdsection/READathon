package com.example.crossdsection.readathon.listeners;

import android.view.View;

/**
 * Created by chitra on 14/12/17.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
