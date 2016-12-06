package com.jokerslab.newsdemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jokerslab.newsdemo.databinding.FragmentNewsCategoryBinding;
import com.jokerslab.newsdemo.dummy.DummyContent.DummyItem;
import com.jokerslab.newsdemo.network.ServerCalls;

import java.util.ArrayList;
import java.util.List;

import static com.jokerslab.newsdemo.NewsCategory.ALL;


public class NewsCategoryFragment extends Fragment implements MyItemRecyclerViewAdapter.ItemClickListener {

    public static final String TAG = NewsCategoryFragment.class.getSimpleName();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_NEWS_CATEGORY = "news-category";
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter adapter;

    private FragmentNewsCategoryBinding binding;

    public NewsCategoryFragment() {
    }


    private int mColumnCount = 1;
    private int newsCategory = ALL;


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
    }

    private void getNews(@NewsCategory final int newsCategory) {
        binding.progressBarLayout.setVisibility(View.VISIBLE);
        binding.messageLayout.setVisibility(View.GONE);
        ServerCalls.getNewsSummary(getActivity(), TAG, newsCategory, new ServerCalls.ResponseListener() {
            @Override
            public void onResponse(int code, ArrayList<News> model, String response) {
                binding.progressBarLayout.setVisibility(View.GONE);
                if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                    if (model != null &&model.size() > 0) {
                        adapter.setData(model);
                        mListener.storeNews(newsCategory, model);
                    } else {
                        binding.messageTextView.setText(R.string.content_not_available);
                        binding.messageLayout.setVisibility(View.VISIBLE);
                    }

                }else if (code == ServerCalls.NetworkResponseCode.SERVER_ERROR) {
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
        getNews(newsCategory);
        return binding.getRoot();
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

    public void loadNews(@NewsCategory int newsCategory, ArrayList<News> newses) {
        if (this.newsCategory != newsCategory) {
            this.newsCategory = newsCategory;
            if (newses != null && newses.size() > 0) {
                binding.progressBarLayout.setVisibility(View.GONE);
                binding.messageLayout.setVisibility(View.GONE);
                adapter.setData(newses);
            } else {
                getNews(newsCategory);
            }
        }
    }

    @Override
    public void onItemClicked(View view, int viewType, int position, int action) {
        Intent intent = new Intent(view.getContext(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.EXTRA_NEWS_ID, adapter.getNewsList().get(position).getId());

        startActivity(intent);
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
        void storeNews(@NewsCategory int newsCategory, ArrayList<News> newsItemList);
    }
}
