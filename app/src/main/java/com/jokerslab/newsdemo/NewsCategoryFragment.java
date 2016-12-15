package com.jokerslab.newsdemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jokerslab.newsdemo.databinding.FragmentNewsCategoryBinding;
import com.jokerslab.newsdemo.network.ServerCalls;

import java.util.ArrayList;

import static com.jokerslab.newsdemo.NewsCategory.ALL;


public class NewsCategoryFragment extends Fragment implements MyItemRecyclerViewAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = NewsCategoryFragment.class.getSimpleName();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_NEWS_CATEGORY = "news-category";
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter adapter;

    private FragmentNewsCategoryBinding binding;
    private int totalCount;
    private int startCount;
    private static final int BUCKET_SIZE = 5;

    public NewsCategoryFragment() {
    }


    private int mColumnCount = 1;
    private int newsCategory = ALL;
    private int dividerHeight;



    @SuppressWarnings("unused")
    public static NewsCategoryFragment newInstance(int columnCount, @NewsCategory int newsCategory) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putInt(ARG_NEWS_CATEGORY, newsCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            newsCategory = getArguments().getInt(ARG_NEWS_CATEGORY);
        }

        dividerHeight = (int) getResources().getDimension(R.dimen.devider_height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_category, container, false);
        Context context = binding.getRoot().getContext();

        if (mColumnCount <= 1) {
            binding.list.setLayoutManager(new LinearLayoutManager(context));
        } else {
            binding.list.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyItemRecyclerViewAdapter(this);
        binding.list.setAdapter(adapter);
        binding.list.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                if (startCount < totalCount) {
                    startCount = startCount + BUCKET_SIZE + 1;
                    getNews(newsCategory, AddingType.END);
                }
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.DKGRAY);
        getNews(newsCategory, AddingType.NEW);
        return binding.getRoot();
    }

    private void getNews(@NewsCategory final int newsCategory, @AddingType final int addingType) {

        if (addingType == AddingType.NEW) {
            totalCount = 0;
            startCount = 0;
        }
        //binding.progressBarLayout.setVisibility(View.VISIBLE);
        binding.messageLayout.setVisibility(View.GONE);
        ServerCalls.getNewsSummary(getActivity(), TAG, newsCategory, startCount, BUCKET_SIZE, new ServerCalls.ResponseListenerNewsList() {
            @Override
            public void onResponse(int code, NewsList model, String response) {
                binding.progressBarLayout.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
                if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                    if (addingType == AddingType.NEW) {
                        if (model != null && model.getNews().size() > 0) {
                            totalCount = model.getCount();
                            adapter.setData(model.getNews());
                            if (mListener != null)
                                mListener.storeNews(newsCategory, adapter.getNewsModelList());
                        } else {
                            binding.messageTextView.setText(R.string.content_not_available);
                            binding.messageLayout.setVisibility(View.VISIBLE);
                        }

                    } else if (addingType == AddingType.END) {
                        if (model != null && model.getNews().size() > 0) {
                            adapter.addNewsList(model.getNews());
                            if (mListener != null)
                                mListener.storeNews(newsCategory, adapter.getNewsModelList());
                        }
                    }

                } else if (code == ServerCalls.NetworkResponseCode.SERVER_ERROR) {
                    binding.messageTextView.setText(R.string.content_not_available);
                    binding.messageLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.messageTextView.setText(R.string.network_error_check);
                    binding.messageLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loadNews(@NewsCategory int newsCategory, ArrayList<NewsModel> newses) {
        if (this.newsCategory != newsCategory) {
            this.newsCategory = newsCategory;
            if (newses != null && newses.size() > 0) {
                binding.progressBarLayout.setVisibility(View.GONE);
                binding.messageLayout.setVisibility(View.GONE);
                adapter.setData(newses);
            } else {
                adapter.setData(null);
                binding.swipeRefreshLayout.setRefreshing(true);
                getNews(newsCategory, AddingType.NEW);
            }
        }
    }

    @Override
    public void onItemClicked(View view, int viewType, int position, int action) {
        Intent intent = new Intent(view.getContext(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.EXTRA_NEWS_ID, adapter.getNewsModelList().get(position).getId());

        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        totalCount = 0;
        startCount = 0;
        getNews(newsCategory, AddingType.NEW);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void storeNews(@NewsCategory int newsCategory, ArrayList<NewsModel> newsModelItemList);
    }
}
