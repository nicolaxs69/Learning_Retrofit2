package com.example.retrofit_tutorial;

import com.google.gson.annotations.SerializedName;

public class Comment {
private int postId;
private int id;
private String name;
private String email;

@SerializedName("body")
private String text;

    public String getName() {
        return name;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
