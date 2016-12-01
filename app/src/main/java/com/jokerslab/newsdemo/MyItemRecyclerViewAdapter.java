package com.jokerslab.newsdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jokerslab.newsdemo.NewsSummaryFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<BaseTypeViewHolder> {

    private List<News> newsList;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<News> items, OnListFragmentInteractionListener listener) {
        newsList = items;
        mListener = listener;
    }

    @Override
    public BaseTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new RecentNewsViewHolder(view, 1, mListener);
    }

    @Override
    public void onBindViewHolder(BaseTypeViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return ViewType.COVER_NEWS;
        else return ViewType.SUMMARY_NEWS;
    }

    public class ViewType {
        public static final int COVER_NEWS = 400;
        public static final int SUMMARY_NEWS = 787;
    }
}
