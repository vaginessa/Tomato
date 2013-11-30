package com.shivgadhia.android.tomato.service;

import android.app.IntentService;
import android.content.Intent;
import com.shivgadhia.android.tomato.persistance.DatabaseWriter;
import com.shivgadhia.android.tomato.persistance.Posts.PostWriter;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import org.scribe.exceptions.OAuthConnectionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPostsService extends IntentService {
    public static final String PARAM_IN_POST_URL = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public GetPostsService() {
        super("GET_POSTS_SERVICE");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String blogUrl = intent.getStringExtra(PARAM_IN_POST_URL);

        JumblrClient client = new JumblrClient("t435qKhNgWh86lgc6r4GP6A5N8tWP2C6yW6JYh1y4mrVgyaLut", "z3G1RTTo3hyuSp33Z7RXpZ9VNsUIozA0gk0bhwacUEVphdOrkY");
        try {
            Blog blog = client.blogInfo(blogUrl);
            blog.getTitle();

            List<Post> posts;

            Map<String, String> options = new HashMap<String, String>();
            options.put("type", "photo");


            if (blogUrl.endsWith(".tumblr.com")) {
                posts = blog.posts(options);
            } else {
                posts = client.tagged(blogUrl, options);
            }

            DatabaseWriter databaseWriter = new DatabaseWriter(getContentResolver());
            PostWriter postWriter = new PostWriter(databaseWriter);
            for (Post p : posts) {
                if (p.getType().equalsIgnoreCase("photo")) {
                    postWriter.savePost((PhotoPost) p);
                }
            }

            broadcastFinishedMessage(blog);
        } catch (JumblrException e) {
            handleError(blogUrl, e);
        } catch (OAuthConnectionException e) {
            handleError(blogUrl, e);
        }
    }

    private void handleError(String blogUrl, Exception e) {
        e.printStackTrace();
        broadcastErrorMessage(blogUrl);
    }

    private void broadcastFinishedMessage(Blog blog) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(GetPostsReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, blog.getName() + " has " + blog.getPostCount() + " posts");
        sendBroadcast(broadcastIntent);
    }

    private void broadcastErrorMessage(String blogUrl) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(GetPostsReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, "'" + blogUrl + "' not found!");
        sendBroadcast(broadcastIntent);
    }
}

