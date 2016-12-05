package com.jokerslab.newsdemo.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jokerslab.newsdemo.AppConstant;
import com.jokerslab.newsdemo.News;
import com.jokerslab.newsdemo.NewsCategory;
import com.jokerslab.newsdemo.Util;

import java.util.ArrayList;

/**
 * Created by sayem on 7/29/2016.
 */
public class ServerCalls {

    private static final String TAG = ServerCalls.class.getSimpleName();

    public static void getNewsSummary(Context context, String tag, @NewsCategory int category, final ResponseListener listener) {
        String url = AppConstant.BASE_URL + AppConstant.METHOD_GET_NEWS_SUMMARY;
        if (category != NewsCategory.ALL) {
            url = url + "?category=" + category;
        }
        Log.d(TAG, "get data " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!Util.isEmpty(response)) {
                    ArrayList<News> model = News.listFromJson(response);
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
/*
    public static void requestForBook(Context context, String tag, String user, String bookQR, final ResponseListener listener) {
        String url = "http://spinelbd.com/qr_service/getbook.php?qr=" + bookQR + "&qr_student=" + user;
        Log.d(TAG, "url requestForBook request" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("requestForBook reponse", response);
                Model model = Model.fromJson(response);
                if (model.isStatus()) {
                    listener.onResponse(NetworkResponseCode.RESULT_OK, model, response);
                } else {
                    listener.onResponse(NetworkResponseCode.SERVER_ERROR, model, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(NetworkResponseCode.NETWORK_ERROR, null, null);
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void returnBook(Context context, String tag, String user, String bookQR, final ResponseListener listener) {
        String url = "http://spinelbd.com/qr_service/return_a_book.php?qr=" + bookQR + "&qr_student=" + user;
        Log.d(TAG, "url returnBook request" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("returnBook reponse", response);
                Model model = Model.fromJson(response);
                if (model.isStatus()) {
                    listener.onResponse(NetworkResponseCode.RESULT_OK, model, response);
                } else {
                    listener.onResponse(NetworkResponseCode.SERVER_ERROR, model, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(NetworkResponseCode.NETWORK_ERROR, null, null);
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }*/

    public static interface ResponseListener {
        void onResponse(int code, ArrayList<News> model, String response);
    }

    public static class NetworkResponseCode {
        public static final int RESULT_OK = 1;
        public static final int SERVER_ERROR = 2;
        public static final int NETWORK_ERROR = 3;
    }
}
