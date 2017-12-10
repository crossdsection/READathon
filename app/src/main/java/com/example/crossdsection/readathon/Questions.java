package com.example.crossdsection.readathon;

/**
 * Created by crossdsection on 10/12/17.
 */

public class Questions {
    int _id;
    int story_id;
    String question;
    int questiontype_id;
    String answerkeyword;
    String created, modified;

    public Questions( int _id, int story_id, String question, String answerkeyword, String created, String modified ){
        this._id = _id;
        this.story_id = story_id;
        this.question = question;
        this.questiontype_id = questiontype_id;
        this.answerkeyword = answerkeyword;
        this.created = created;
        this.modified = modified;
    }
}
