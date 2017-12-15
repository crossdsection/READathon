package com.example.crossdsection.readathon.model;

/**
 * Created by crossdsection on 10/12/17.
 */

public class Questions {
    int _id;
    int story_id;
    String question;
    int questiontype_id;
    int questionId;
    String answerkeyword;
    String created, modified;

    public Questions(){
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestiontype_id() {
        return questiontype_id;
    }

    public void setQuestiontype_id(int questiontype_id) {
        this.questiontype_id = questiontype_id;
    }

    public String getAnswerkeyword() {
        return answerkeyword;
    }

    public void setAnswerkeyword(String answerkeyword) {
        this.answerkeyword = answerkeyword;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
