package com.jokerslab.newsdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.jokerslab.newsdemo.databinding.ActivityNewsDetailsBinding;
import com.jokerslab.newsdemo.network.ServerCalls;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String TAG = NewsDetailsActivity.class.getSimpleName();
    public static final String EXTRA_NEWS_ID = "news_id";

    private ActivityNewsDetailsBinding binding;
    private String newsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        newsID = getIntent().getStringExtra(EXTRA_NEWS_ID);

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        getNewsDetails(newsID);
    }

    private void getNewsDetails(String newsID) {
        binding.progressBarLayout.setVisibility(View.VISIBLE);
        binding.messageLayout.setVisibility(View.GONE);
        ServerCalls.getNewsDetails(this, TAG, newsID, new ServerCalls.ResponseListenerNews() {
            @Override
            public void onResponse(int code, NewsModel model, String response) {
                binding.progressBarLayout.setVisibility(View.GONE);

                if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                    binding.webView.loadData(model.getNewsdetails(), "text/html", null);
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
}
