package com.example.crossdsection.readathon.model;

/**
 * Created by crossdsection on 10/12/17.
 */

public class User {

    int _id;
    String name;
    int score;
    int level_id;
    String created, modified;

    public User( int _id, String name, int score, int level_id, String created, String modified ){
        this._id = _id;
        this.name = name;
        this.score = score;
        this.level_id = level_id;
        this.created = created;
        this.modified = modified;
    }

}
