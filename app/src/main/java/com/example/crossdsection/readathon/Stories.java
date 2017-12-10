package com.example.crossdsection.readathon;

/**
 * Created by crossdsection on 10/12/17.
 */

public class Stories {
    int _id;
    String story;
    int level_id;
    String created;
    String modified;

    public Stories( int _id, String story, int level_id, String created, String modified ){
        this._id = _id;
        this.story = story;
        this.level_id = level_id;
        this.created = created;
        this.modified = modified;
    }
}
