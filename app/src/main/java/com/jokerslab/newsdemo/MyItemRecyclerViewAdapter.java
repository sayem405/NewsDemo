package com.jokerslab.newsdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jokerslab.newsdemo.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<BaseTypeViewHolder> {

    private ArrayList<NewsModel> newsModelList;
    private final MyItemRecyclerViewAdapter.ItemClickListener mListener;

    public MyItemRecyclerViewAdapter(MyItemRecyclerViewAdapter.ItemClickListener listener) {
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
        NewsModel newsModel = getNewsModelList().get(position);
        if (holder instanceof RecentNewsViewHolder) {
            ((RecentNewsViewHolder) holder).bindData(newsModel);
        }
    }


    @Override
    public int getItemCount() {
        return newsModelList == null ? 0 : newsModelList.size();
    }

    public ArrayList<NewsModel> getNewsModelList() {
        if (newsModelList == null) {
            newsModelList = new ArrayList<>();
        }
        return newsModelList;
    }

    public void setNewsModelList(ArrayList<NewsModel> newsModelList) {
        this.newsModelList = newsModelList;
    }

    public void addNewsList(List<NewsModel> newsModelList) {
        getNewsModelList().addAll(newsModelList);
        notifyItemMoved(getNewsModelList().size() + 1, getNewsModelList().size() - newsModelList.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return ViewType.COVER_NEWS;
        else return ViewType.SUMMARY_NEWS;
    }

    public void setData(ArrayList<NewsModel> data) {
        newsModelList = data;
        notifyDataSetChanged();
    }

    public class ViewType {
        public static final int COVER_NEWS = 400;
        public static final int SUMMARY_NEWS = 787;
    }

    public interface ItemClickListener {
        void onItemClicked(View view, int viewType, int position, int action);
    }

    public @interface ActionType {
        int GO_DETAILS_PAGE = 1;
    }
}
