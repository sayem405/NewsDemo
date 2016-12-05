package com.jokerslab.newsdemo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sayem on 11/16/2016.
 */

public class News {
    private String id;
    private String title;
    private String summary;
    private String newsContent;
    private @NewsCategory int category;
    private ArrayList<String> images;

    //app side
    private int viewType;


    //region setter getter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public @NewsCategory int getCategory() {
        return category;
    }

    public void setCategory(@NewsCategory int category) {
        this.category = category;
    }



    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
    //endregion

    //region Gson conversion
    public static News fromJson(String jsonString) {
        return new GsonBuilder().create().fromJson(jsonString, News.class);
    }

    public static ArrayList<News> listFromJson(String jsonString) {
        Type collectionType = new TypeToken<Collection<News>>(){}.getType();
        return new GsonBuilder().create().fromJson(jsonString, collectionType);
    }

    public String getJsonString() {
        return new GsonBuilder().create().toJson(this);
    }

    public String getFullUrl() {
        String url = AppConstant.IMAGE_URL + getImages().get(0);
        return url;
    }
    //endregion


}
