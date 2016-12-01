package com.jokerslab.newsdemo;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by sayem on 11/30/2016.
 */

public class NewsList {
    private ArrayList<News> News;

    public static NewsList fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, NewsList.class);
    }
}
