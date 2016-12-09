package com.jokerslab.newsdemo;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by sayem on 7/28/2016.
 */
public class Util {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 34343;

    public static boolean isEmpty(String stringValue) {
        return stringValue == null || stringValue.isEmpty();
    }

    public static boolean checkPlayServices(Activity activity) {

        final int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);

        if (resultCode != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(activity,resultCode,
                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
            return false;
        }
        return true;
    }
}
