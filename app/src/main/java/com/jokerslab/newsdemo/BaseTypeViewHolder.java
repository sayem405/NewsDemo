package com.jokerslab.newsdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sayem on 11/16/2016.
 */

public abstract class BaseTypeViewHolder extends RecyclerView.ViewHolder{
    private int viewType;

    public BaseTypeViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
