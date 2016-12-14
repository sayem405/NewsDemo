package com.jokerslab.newsdemo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sayem on 11/30/2016.
 */

public class NewsList {
    private ArrayList<NewsModel> NewsModel;
    private int count;



    public ArrayList<NewsModel> getNewsModel() {
        if (NewsModel == null) {
            NewsModel = new ArrayList<>();
        }
        return NewsModel;
    }

    public void setNewsModel(ArrayList<NewsModel> newsModel) {
        NewsModel = newsModel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public static ArrayList<NewsModel> listFromJson(String jsonString) {
        Type collectionType = new TypeToken<Collection<NewsModel>>(){}.getType();
        return new GsonBuilder().create().fromJson(jsonString, collectionType);
    }

    public String getJsonString() {
        return new GsonBuilder().create().toJson(this);
    }

    public static NewsList fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, NewsList.class);
    }
}
