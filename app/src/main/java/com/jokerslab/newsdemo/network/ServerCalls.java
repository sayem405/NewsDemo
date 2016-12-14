package com.jokerslab.newsdemo.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.jokerslab.newsdemo.AppConstant;
import com.jokerslab.newsdemo.NewsModel;
import com.jokerslab.newsdemo.NewsCategory;
import com.jokerslab.newsdemo.NewsList;
import com.jokerslab.newsdemo.Util;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sayem on 7/29/2016.
 */
public class ServerCalls {

    private static final String TAG = ServerCalls.class.getSimpleName();

    public static void getNewsSummary(Context context, String tag, @NewsCategory int category, int startCount, int bucket, final ResponseListenerNewsList listener) {
        String url = AppConstant.BASE_URL + AppConstant.METHOD_GET_NEWS_SUMMARY;
        url = url + "?category=" + category;
        url = url + "&start=" + startCount + "&reqcount=" + bucket;
        Log.d(TAG, "get data " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!Util.isEmpty(response)) {
                    NewsList model = NewsList.fromJson(response);
                    listener.onResponse(NetworkResponseCode.RESULT_OK, model, response);
                } else {
                    listener.onResponse(NetworkResponseCode.SERVER_ERROR, null, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                listener.onResponse(NetworkResponseCode.NETWORK_ERROR, null, null);
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getNewsDetails(Context context, String tag, String newID, final ResponseListenerNews listener) {
        String url = AppConstant.BASE_URL + AppConstant.METHOD_GET_NEWS_DETAILS + newID;

        Log.d(TAG, "request getNewsDetails" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response getNewsDetails", response);
                if (!Util.isEmpty(response)) {
                    NewsModel model = NewsModel.fromJson(response);
                    listener.onResponse(NetworkResponseCode.RESULT_OK, model, response);
                } else {
                    listener.onResponse(NetworkResponseCode.SERVER_ERROR, null, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                listener.onResponse(NetworkResponseCode.NETWORK_ERROR, null, null);
            }
        }
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String jsonString = new String(response.data);
                return Response.success(jsonString, parseIgnoreCacheHeaders(response));
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void saveToken(Context context, String tag, String token, final ResponseListener listener) {
        String url = AppConstant.BASE_URL + AppConstant.METHOD_GET_TOKEN + token;

        Log.d(TAG, "request saveToken" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response saveToken", response);
                if (!Util.isEmpty(response) && "DONE".equals(response)) {
                    listener.onResponse(NetworkResponseCode.RESULT_OK, null, response);
                } else {
                    listener.onResponse(NetworkResponseCode.SERVER_ERROR, null, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                listener.onResponse(NetworkResponseCode.NETWORK_ERROR, null, null);
            }
        }
        );

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static interface ResponseListener {
        void onResponse(int code, ArrayList<NewsModel> model, String response);
    }

    public static interface ResponseListenerNews {
        void onResponse(int code, NewsModel model, String response);
    }

    public static interface ResponseListenerNewsList {
        void onResponse(int code, NewsList model, String response);
    }

    public static class NetworkResponseCode {
        public static final int RESULT_OK = 1;
        public static final int SERVER_ERROR = 2;
        public static final int NETWORK_ERROR = 3;
    }

    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }
}
