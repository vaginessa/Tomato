package com.shivgadhia.android.tomato.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GetPostsReceiver extends BroadcastReceiver{


    public static final String ACTION_RESP =
            "com.shivgadhia.android.tomato.MESSAGE_PROCESSED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra(GetPostsService.PARAM_OUT_MSG);
        Log.v("SERVICE", "RESULT " + text);
    }
}
