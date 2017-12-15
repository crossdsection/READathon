package com.example.crossdsection.readathon.listeners;

import com.example.crossdsection.readathon.model.Questions;

/**
 * Created by chitra on 14/12/17.
 */

public interface Listener_SubmitAnswers  {
    public void submitAnswer(int questionId, String answer, int isCorrect);
}
