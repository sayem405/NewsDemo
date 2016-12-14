package com.jokerslab.newsdemo;

import android.view.View;

import com.bumptech.glide.Glide;
import com.jokerslab.newsdemo.databinding.FragmentItemBinding;

/**
 * Created by sayem on 11/16/2016.
 */

public class RecentNewsViewHolder extends BaseTypeViewHolder implements DataBinder<NewsModel>, View.OnClickListener {

    private final FragmentItemBinding binding;
    private NewsModel newsModel;
    private MyItemRecyclerViewAdapter.ItemClickListener listener;

    public RecentNewsViewHolder(FragmentItemBinding binding,  int viewType, MyItemRecyclerViewAdapter.ItemClickListener listener) {
        super(binding.getRoot(), viewType);
        binding.getRoot().setOnClickListener(this);
        this.listener = listener;
        this.binding = binding;
    }

    @Override
    public void bindData(NewsModel newsModel) {
        this.newsModel = newsModel;
        binding.title.setText(newsModel.getTitle());
        binding.content.setText(newsModel.getSummary());
        Glide.with(getContext()).load(newsModel.getFullUrl()).into(binding.summaryImageView);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClicked(view,getViewType(),getAdapterPosition(), MyItemRecyclerViewAdapter.ActionType.GO_DETAILS_PAGE);
        }
    }
}
