package com.example.crossdsection.readathon.model;

/**
 * Created by crossdsection on 10/12/17.
 */

public class Stories {
    int _id;
    String story;
    int level_id;
    String created;
    String modified;

    public Stories() {
    }

    public Stories(int _id, String story, int level_id, String created, String modified ){
        this._id = _id;
        this.story = story;
        this.level_id = level_id;
        this.created = created;
        this.modified = modified;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
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
