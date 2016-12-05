package com.jokerslab.newsdemo;

import com.bumptech.glide.Glide;
import com.jokerslab.newsdemo.databinding.FragmentItemBinding;

/**
 * Created by sayem on 11/16/2016.
 */

public class RecentNewsViewHolder extends BaseTypeViewHolder implements DataBinder<News>{

    private final FragmentItemBinding binding;
    private News news;
    final NewsCategoryFragment.OnListFragmentInteractionListener listener;

    public RecentNewsViewHolder(FragmentItemBinding binding,  int viewType, NewsCategoryFragment.OnListFragmentInteractionListener listener) {
        super(binding.getRoot(), viewType);
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
}
