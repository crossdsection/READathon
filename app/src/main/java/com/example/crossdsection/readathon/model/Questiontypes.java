package com.example.crossdsection.readathon.model;

/**
 * Created by crossdsection on 13/12/17.
 */

public class Questiontypes {
    int _id;
    String questiontype;
    String created;
    String modified;

    public Questiontypes () {
    }

    public Questiontypes (int _id, String questiontype, String created, String modified ){
        this._id = _id;
        this.questiontype = questiontype;
        this.created = created;
        this.modified = modified;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
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

