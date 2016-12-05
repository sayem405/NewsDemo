package com.jokerslab.newsdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jokerslab.newsdemo.NewsCategoryFragment.OnListFragmentInteractionListener;
import com.jokerslab.newsdemo.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<BaseTypeViewHolder> {

    private List<News> newsList;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public BaseTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_item, parent, false);
        return new RecentNewsViewHolder(binding, 1, mListener);
    }

    @Override
    public void onBindViewHolder(BaseTypeViewHolder holder, int position) {
        News news = getNewsList().get(position);
        if (holder instanceof RecentNewsViewHolder) {
            ((RecentNewsViewHolder)holder).bindData(news);
        }
    }


    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public List<News> getNewsList() {
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
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

    public void setData(ArrayList<News> data) {
        getNewsList().clear();
        getNewsList().addAll(data);
        notifyDataSetChanged();
    }

    public class ViewType {
        public static final int COVER_NEWS = 400;
        public static final int SUMMARY_NEWS = 787;
    }
}
