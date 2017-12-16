package com.example.crossdsection.readathon.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.api.ApiGetMeaning;
import com.example.crossdsection.readathon.constant.ConstantApi;
import com.example.crossdsection.readathon.database.Contract;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.listeners.TouchableSpan;
import com.example.crossdsection.readathon.model.Stories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class StoriesActivity extends BaseActivity implements View.OnClickListener{

    DBHelper db;
    int level;

    ImageView storyIv;
    TextView storyTv;
    Button questionBtn;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        storyTv = findViewById(R.id.storyTv);
        questionBtn = findViewById(R.id.questionBtn);
        storyIv = findViewById(R.id.storyIv);
        progressBar = findViewById(R.id.progressBar);

        questionBtn.setOnClickListener(this);

        db = new DBHelper(getApplicationContext());
        level = getIntent().getIntExtra("level", 1);
        getDataForLevels(level);
    }

    public void getDataForLevels(int level){
        Cursor cursor = db.getData(level);

        while(cursor != null && cursor.moveToNext()){
            if(level == cursor.getInt(cursor.getColumnIndex("level_id"))) {
                Stories stories = new Stories();
                stories.setStory(cursor.getString(cursor.getColumnIndex("story")));
                String imagePath = cursor.getString(cursor.getColumnIndex(Contract.StoryIllustration.COLUMN_IMAGE_PATH));

                storyTv.setText(stories.getStory());

                Log.d("IMAGE", "" + imagePath);
                if(!TextUtils.isEmpty(imagePath)){
                    Glide.with(mActivity).load(imagePath)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(storyIv);
                }
            }
        }


        final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);
                String data =  item.getText().toString();
                if(!TextUtils.isEmpty(data)){
                    progressBar.setVisibility(View.VISIBLE);
                    getMeaningOfWord(data);
                }
            }
        });

        storyTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
              return false;
            }
        });
    }

    public void getMeaningOfWord(String data){
        String url = "https://od-api.oxforddictionaries.com/api/v1/entries/en/" + data.toLowerCase();

        new CallbackTask().execute(url);
    }

    public void showDialog(String defination, String domain, String example){
        Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(LayoutInflater.from(mActivity).inflate(R.layout.dialog_word_meaning, null, false));

        TextView definationTv = dialog.findViewById(R.id.definationTv);
        if(!TextUtils.isEmpty(defination)) {
            definationTv.setText("Defination : \n" + defination);
        }

        TextView domainTv = dialog.findViewById(R.id.domainTv);
        if(!TextUtils.isEmpty(domain)) {
            domainTv.setText("Domain : \n" + domain);
            domainTv.setVisibility(View.VISIBLE);
        }

        TextView exampleTv = dialog.findViewById(R.id.exampleTv);
        if(!TextUtils.isEmpty(example)) {
            exampleTv.setText("Example : \n" + example);
        }

        dialog.show();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mActivity, QuestionActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            //TODO: replace with your own app id and app key
            final String app_id = ConstantApi.APP_ID;
            final String app_key = ConstantApi.APP_KEY;
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }


                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String defination = "";
            String domains = "";
            String examples = "";
            try {
                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONArray lexArray = ((JSONObject)jsonArray.get(0)).getJSONArray("lexicalEntries");
                JSONArray entArray = ((JSONObject) lexArray.get(0)).getJSONArray("entries");
                JSONArray sensArray = ((JSONObject) entArray.get(0)).getJSONArray("senses");
                JSONObject sensObject = (JSONObject) sensArray.get(0);

                defination = (String) sensObject.getJSONArray("definitions").get(0);

                if(sensObject.has("domains")) {
                    domains = (String) sensObject.getJSONArray("domains").get(0);
                }

                examples = (String)((JSONObject) sensObject.getJSONArray("examples").get(0)).get("text");

                //System.out.println(defination + " : " + domains + " : " + examples);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            //System.out.println(result);
            showDialog(defination, domains, examples);
        }
    }
}

