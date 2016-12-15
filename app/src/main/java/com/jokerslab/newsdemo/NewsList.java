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
    private ArrayList<NewsModel> News;
    private int Count;



    public ArrayList<NewsModel> getNews() {
        if (News == null) {
            News = new ArrayList<>();
        }
        return News;
    }

    public void setNews(ArrayList<NewsModel> news) {
        News = news;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        this.Count = count;
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
