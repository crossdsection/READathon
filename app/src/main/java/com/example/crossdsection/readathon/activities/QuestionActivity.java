package com.example.crossdsection.readathon.activities;

import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.Readathon;
import com.example.crossdsection.readathon.adapters.QuestionAdapter;
import com.example.crossdsection.readathon.constant.Constants;
import com.example.crossdsection.readathon.database.Contract;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.listeners.Listener_Speak;
import com.example.crossdsection.readathon.listeners.Listener_SubmitAnswers;
import com.example.crossdsection.readathon.model.Answers;
import com.example.crossdsection.readathon.model.Questions;
import com.example.crossdsection.readathon.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class QuestionActivity extends BaseActivity implements View.OnClickListener
        , Listener_SubmitAnswers, Listener_Speak , TextToSpeech.OnInitListener{

    RecyclerView recyclerView;
    Button submitAnswersBtn;

    int level;

    DBHelper db;
    List<Questions> questionsList;
    HashMap<Integer, List<Answers>> answerMap;
    QuestionAdapter adapter;
    TextToSpeech tts;
    String wordToSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        level = getIntent().getIntExtra("level", 1);

        recyclerView = findViewById(R.id.recyclerView);
        submitAnswersBtn = findViewById(R.id.submitAnswersBtn);
        submitAnswersBtn.setOnClickListener(this);


        db = new DBHelper(mActivity);
        Cursor cursor = db.getQuestions(level);
        questionsList = new ArrayList<>();
        answerMap = new HashMap<>();
        while (cursor != null && cursor.moveToNext()){
            Questions questions = new Questions();
            questions.setStory_id(cursor.getInt(cursor.getColumnIndex(Contract.Questions.COLUMN_STORY_ID)));
            questions.setQuestiontype_id(cursor.getInt(cursor.getColumnIndex(Contract.Questions.COLUMN_QUESTION_TYPE_ID)));
            questions.setQuestionId(cursor.getInt(cursor.getColumnIndex(Contract.Questions.COLUMN_QUESTION_ID)));
            questions.setQuestion(cursor.getString(cursor.getColumnIndex(Contract.Questions.COLUMN_QUESTION)));
            questions.setAnswerkeyword(cursor.getString(cursor.getColumnIndex(Contract.Questions.COLUMN_ANSWER_KEYWORD)));
            questionsList.add(questions);

            List<Answers> answersList = db.getAnswers(questions.getQuestionId(), questions.getStory_id());
            answerMap.put(questions.getQuestionId(), answersList);
        }

        adapter = new QuestionAdapter(mActivity, questionsList, answerMap, this, this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tts != null) {
            tts.shutdown();
        }
    }

    @Override
    public void onClick(View view) {
        if(submitAnswersBtn.getText().toString().equalsIgnoreCase("Done")){

            Utils.goToMainScreen(mActivity);
        } else {
            submitAnswersBtn.setText("Done");
            int result = db.getResult(level);
            Toast.makeText(mActivity, "Correct Answers " + result + "/" + questionsList.size(), Toast.LENGTH_SHORT).show();
            if(result == questionsList.size()){
                Readathon.getPref().edit().putInt(Constants.LEVEL_CLEARED, level).commit();
            }
        }
    }

    @Override
    public void submitAnswer(int questionId, String answer, int isCorrect) {
        db.submitAnswer(questionId, level, isCorrect, answer);
    }

    @Override
    public void speak(String word) {
        wordToSpeak = word;
        tts = new TextToSpeech(mActivity, this);
    }

    @Override
    public void onInit(int status) {
        if (!TextUtils.isEmpty(wordToSpeak) && status == TextToSpeech.SUCCESS && tts != null){
            int result = tts.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("QuestionActivity", "Language not supported or Missing Data");
            } else {
                tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null, "");
            }

            wordToSpeak = "";
        }
    }
}
