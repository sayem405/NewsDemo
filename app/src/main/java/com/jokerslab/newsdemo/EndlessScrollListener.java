package com.jokerslab.newsdemo;

import android.support.v7.widget.RecyclerView;

/**
 * Created by sayem on 12/14/16.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!recyclerView.canScrollVertically(1)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
