package com.jokerslab.newsdemo;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by sayem on 11/30/2016.
 */

public class NewsList {
    private ArrayList<News> News;
    private int count;

    public static NewsList fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, NewsList.class);
    }

    public ArrayList<com.jokerslab.newsdemo.News> getNews() {
        return News;
    }

    public void setNews(ArrayList<com.jokerslab.newsdemo.News> news) {
        News = news;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
