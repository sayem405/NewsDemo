package com.jokerslab.newsdemo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.jokerslab.newsdemo.network.ServerCalls;

import java.util.ArrayList;

/**
 * Created by sayem on 12/7/16.
 */

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFireBaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Util.saveToPref(this, getString(R.string.token_key), refreshedToken);
        ServerCalls.saveToken(this, TAG, refreshedToken, new ServerCalls.ResponseListener() {
            @Override
            public void onResponse(int code, ArrayList<NewsModel> model, String response) {
                if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                    Util.saveToPref(MyFireBaseInstanceIDService.this, getString(R.string.token_key), "");
                }
            }
        });
    }
}
