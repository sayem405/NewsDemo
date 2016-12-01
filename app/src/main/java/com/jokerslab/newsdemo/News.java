package com.jokerslab.newsdemo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by sayem on 11/16/2016.
 */

public class News {
    private String newsId;
    private String title;
    private String newsSummary;
    private String newsSummaryNewsImageUrl;
    private String newsContent;
    private String category;

    //app side
    private int viewType;


    //region setter getter
    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }

    public String getNewsSummaryNewsImageUrl() {
        return newsSummaryNewsImageUrl;
    }

    public void setNewsSummaryNewsImageUrl(String newsSummaryNewsImageUrl) {
        this.newsSummaryNewsImageUrl = newsSummaryNewsImageUrl;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /*public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }*/

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public static List<News> listFromJson(String jsonString) {
        Type collectionType = new TypeToken<Collection<News>>(){}.getType();
        return new GsonBuilder().create().fromJson(jsonString, collectionType);
    }

    public String getJsonString() {
        return new GsonBuilder().create().toJson(this);
    }
    //endregion


}
