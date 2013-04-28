package com.shivgadhia.android.tomato.service;

import android.app.IntentService;
import android.content.Intent;
import com.shivgadhia.android.tomato.persistance.DatabaseWriter;
import com.shivgadhia.android.tomato.persistance.Posts.PostWriter;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPostsService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public GetPostsService() {
        super("GET_POSTS_SERVICE");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String blogUrl = intent.getStringExtra(PARAM_IN_MSG);

        JumblrClient client = new JumblrClient("t435qKhNgWh86lgc6r4GP6A5N8tWP2C6yW6JYh1y4mrVgyaLut", "z3G1RTTo3hyuSp33Z7RXpZ9VNsUIozA0gk0bhwacUEVphdOrkY");

        Blog blog = client.blogInfo("seejohnrun.tumblr.com");
        blog.getTitle();

        Map<String, String> options = new HashMap<String, String>();
        options.put("type", "photo");
        List<Post> posts = blog.posts(options);

        DatabaseWriter databaseWriter = new DatabaseWriter(getContentResolver());
        PostWriter postWriter = new PostWriter(databaseWriter);
        for (Post p : posts) {
            postWriter.savePost((PhotoPost) p);
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(GetPostsReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, blog.getName() + " has " + blog.getPostCount() + " posts");
        sendBroadcast(broadcastIntent);

    }
}

