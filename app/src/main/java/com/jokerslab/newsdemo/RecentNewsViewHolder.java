package com.jokerslab.newsdemo;

import android.view.View;

/**
 * Created by sayem on 11/16/2016.
 */

public class RecentNewsViewHolder extends BaseTypeViewHolder implements DataBinder<News>{

    private News news;
    final NewsSummaryFragment.OnListFragmentInteractionListener listener;

    public RecentNewsViewHolder(View itemView, int viewType, NewsSummaryFragment.OnListFragmentInteractionListener listener) {
        super(itemView, viewType);
        this.listener = listener;
    }

    @Override
    public void bindData(News news) {
        this.news = news;
    }
}
