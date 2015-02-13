package com.tonyjs.basicretrofit.example;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tonyjs on 15. 2. 13..
 */
public class Repo {
    private long id;
    private String name;
    @SerializedName("full_name") private String fullName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Id = " + id + "\n"
                + "Name = " + name;

    }
}
