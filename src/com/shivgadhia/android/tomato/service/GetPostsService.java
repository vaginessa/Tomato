package com.shivgadhia.android.tomato.service;

import android.app.IntentService;
import android.content.Intent;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;

public class GetPostsService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public GetPostsService() {
        super("GET_POSTS_SERVICE");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String blogUrl = intent.getStringExtra(PARAM_IN_MSG);

        JumblrClient client = new JumblrClient("", "");

        Blog blog = client.blogInfo("seejohnrun.tumblr.com");
        blog.getTitle();

        //Persist stuff

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(GetPostsReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, blogUrl + " Done!");
        sendBroadcast(broadcastIntent);

    }
}

