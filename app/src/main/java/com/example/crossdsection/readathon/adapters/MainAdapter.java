package com.example.crossdsection.readathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.crossdsection.readathon.R;

/**
 * Created by chitra on 14/12/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    Context context;
    int level;
    int clearedLevel;

    public MainAdapter(Context context, int level, int clearedLevel) {
        this.context = context;
        this.level = level;
        this.clearedLevel = clearedLevel;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_main_activity, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.levelBtn.setText("level " + (position + 1));

        if(clearedLevel < position && position != 0){
            holder.levelBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_black, 0 ,0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return level;
    }

    class MainHolder extends RecyclerView.ViewHolder {

        Button levelBtn;

        public MainHolder(View itemView) {
            super(itemView);
            levelBtn = itemView.findViewById(R.id.levelBtn);
        }
    }

}
