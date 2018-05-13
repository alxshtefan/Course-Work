package ua.khnu.shtefanyankovska.entity;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;

    public Entity() {
        // nothing to do
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
