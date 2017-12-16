package com.example.crossdsection.readathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crossdsection.readathon.R;
import com.example.crossdsection.readathon.listeners.Listener_Speak;
import com.example.crossdsection.readathon.listeners.Listener_SubmitAnswers;
import com.example.crossdsection.readathon.model.Answers;
import com.example.crossdsection.readathon.model.Questions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chitra on 14/12/17.
 */

public class QuestionAdapter extends  RecyclerView.Adapter<QuestionAdapter.QuestionHolder>{

    Context context;
    List<Questions> questionsList;
    HashMap<Integer, List<Answers>> answerMap;
    Listener_SubmitAnswers callback;
    Listener_Speak speakCallback;

    public QuestionAdapter(Context context, List<Questions> questionsList,
                           HashMap<Integer, List<Answers>> answerMap
            , Listener_SubmitAnswers callback, Listener_Speak speakCallback){
        this.context = context;
        this.questionsList = questionsList;
        this.answerMap = answerMap;
        this.callback = callback;
        this.speakCallback = speakCallback;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_questions, parent, false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionHolder holder, int position) {
        final Questions questions = questionsList.get(position);
        List<Answers> answersList = answerMap.get(questions.getQuestionId());

        String question = questions.getQuestion();

        holder.questionTv.setText(question);

        holder.optionRb1.setText(answersList.get(0).getAnswer());
        holder.optionRb1.setTag(answersList.get(0).getIsCorrect());

        holder.optionRb2.setText(answersList.get(1).getAnswer());
        holder.optionRb2.setTag(answersList.get(1).getIsCorrect());

        holder.optionRb3.setText(answersList.get(2).getAnswer());
        holder.optionRb3.setTag(answersList.get(2).getIsCorrect());

        holder.optionRb4.setText(answersList.get(3).getAnswer());
        holder.optionRb4.setTag(answersList.get(3).getIsCorrect());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String answer = "";
                int isCorrect = 0;
                switch (i){
                    case R.id.optionRb1:
                        answer = holder.optionRb1.getText().toString();
                        isCorrect = (int) holder.optionRb1.getTag();
                        break;

                    case R.id.optionRb2:
                        answer = holder.optionRb2.getText().toString();
                        isCorrect = (int) holder.optionRb2.getTag();
                        break;

                    case R.id.optionRb3:
                        answer = holder.optionRb3.getText().toString();
                        isCorrect = (int) holder.optionRb3.getTag();
                        break;

                    case R.id.optionRb4:
                        answer = holder.optionRb4.getText().toString();
                        isCorrect = (int) holder.optionRb4.getTag();
                        break;
                }

                if(!TextUtils.isEmpty(answer)){
                    speakCallback.speak(answer);
                    callback.submitAnswer(questions.getQuestionId(), answer, isCorrect);
                }
            }
        });

        holder.questionTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String text = ((TextView) view).getText().toString();
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder{

        TextView questionTv;
        RadioGroup radioGroup;
        RadioButton optionRb1, optionRb2, optionRb3, optionRb4;

        public QuestionHolder(View itemView) {
            super(itemView);

            questionTv = itemView.findViewById(R.id.questionTv);
            radioGroup = itemView.findViewById(R.id.radioGp);
            optionRb1 = itemView.findViewById(R.id.optionRb1);
            optionRb2 = itemView.findViewById(R.id.optionRb2);
            optionRb3 = itemView.findViewById(R.id.optionRb3);
            optionRb4 = itemView.findViewById(R.id.optionRb4);
        }
    }
}
