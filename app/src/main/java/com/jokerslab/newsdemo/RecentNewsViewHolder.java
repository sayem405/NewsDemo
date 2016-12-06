package com.jokerslab.newsdemo;

import android.view.View;

import com.bumptech.glide.Glide;
import com.jokerslab.newsdemo.databinding.FragmentItemBinding;

import java.lang.ref.WeakReference;

/**
 * Created by sayem on 11/16/2016.
 */

public class RecentNewsViewHolder extends BaseTypeViewHolder implements DataBinder<News>, View.OnClickListener {

    private final FragmentItemBinding binding;
    private News news;
    private MyItemRecyclerViewAdapter.ItemClickListener listener;

    public RecentNewsViewHolder(FragmentItemBinding binding,  int viewType, MyItemRecyclerViewAdapter.ItemClickListener listener) {
        super(binding.getRoot(), viewType);
        binding.getRoot().setOnClickListener(this);
        this.listener = listener;
        this.binding = binding;
    }

    @Override
    public void bindData(News news) {
        this.news = news;
        binding.title.setText(news.getTitle());
        binding.content.setText(news.getSummary());
        Glide.with(getContext()).load(news.getFullUrl()).into(binding.summaryImageView);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClicked(view,getViewType(),getAdapterPosition(), MyItemRecyclerViewAdapter.ActionType.GO_DETAILS_PAGE);
        }
    }
}
