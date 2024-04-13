package com.example.res_api.Model;

public class Category {

    private String _id;
    private String name;
    private String created;

    public Category(String _id, String name, String created) {
        this._id = _id;
        this.name = name;
        this.created = created;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }


}
