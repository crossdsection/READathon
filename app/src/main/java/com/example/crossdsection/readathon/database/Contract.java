package com.example.crossdsection.readathon.database;

import android.provider.BaseColumns;

/**
 * Created by chitra on 11/12/17.
 */

public class Contract {

    public static class Levels implements BaseColumns {
        public static final String TABLE_NAME = "levels";
        public static final String COLUMN_ID = "level_id";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_MODIFIED = "modified";
    }

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "user_id";
        public static final String COLUMN_LEVEL_ID = "level_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
    }

    public static class Stories implements BaseColumns {
        public static final String TABLE_NAME = "stories";
        public static final String COLUMN_ID = "story_id";
        public static final String COLUMN_LEVEL_ID = "level_id";
        public static final String COLUMN_STORY = "story";
    }

    public static class QuestionTypes implements BaseColumns {
        public static final String TABLE_NAME = "questiontypes";
        public static final String COLUMN_ID = "questiontype_id";
        public static final String COLUMN_QUESTION_TYPE = "questiontype";
    }

    public static class Questions implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_ANSWER_KEYWORD = "answer_keyword";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_QUESTION_TYPE_ID = "questiontype_id";
        public static final String COLUMN_STORY_ID = "story_id";
    }

    public static class Answers implements BaseColumns {
        public static final String TABLE_NAME = "answers";
        public static final String COLUMN_ID = "answer_id";
        public static final String COLUMN_ANSWER= "answer";
        public static final String COLUMN_IS_CORRECT = "is_correct";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_STORY_ID = "story_id";
    }

    public static class SubmittedAnswers implements BaseColumns {
        public static final String TABLE_NAME = "submitted_answers";
        public static final String COLUMN_ID = "submitted_answers_id";
        public static final String COLUMN_ANSWER= "answer";
        public static final String COLUMN_IS_CORRECT = "is_correct";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_STORY_ID = "story_id";
    }

    public static class StoryIllustration implements BaseColumns {
        public static final String TABLE_NAME = "story_illustration";
        public static final String COLUMN_ID = "story_illustration_id";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String COLUMN_LINE_NO = "lineno";
        public static final String COLUMN_STORY_ID = "story_id";
    }

    public static class QuestionIllustration implements BaseColumns {
        public static final String TABLE_NAME = "question_illustration";
        public static final String COLUMN_ID = "question_illustration_id";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String COLUMN_QUESTION_ID = "question_id";
    }

}
