package com.jokerslab.newsdemo;

import android.content.Context;
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

import static com.jokerslab.newsdemo.NewsCategory.ALL;


public class NewsCategoryFragment extends Fragment {

    private static final String TAG = NewsCategoryFragment.class.getSimpleName();
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

    private void getNews(@NewsCategory int newsCategory) {
        binding.progressBarLayout.setVisibility(View.VISIBLE);
        ServerCalls.getNewsSummary(getActivity(), TAG, newsCategory, new ServerCalls.ResponseListener() {
            @Override
            public void onResponse(int code, ArrayList<News> model, String response) {
                if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                    adapter.setData(model);
                } else if (code == ServerCalls.NetworkResponseCode.SERVER_ERROR) {

                }else if (code == ServerCalls.NetworkResponseCode.NETWORK_ERROR) {

                }
                binding.progressBarLayout.setVisibility(View.GONE);
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
        adapter = new MyItemRecyclerViewAdapter(mListener);
        binding.list.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getNews(newsCategory);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onListFragmentInteraction(DummyItem item);
    }
}
