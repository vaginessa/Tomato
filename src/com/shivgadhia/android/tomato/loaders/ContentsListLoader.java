package com.shivgadhia.android.tomato.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.Blogs.BlogsReader;

import java.util.ArrayList;

public class ContentsListLoader extends AsyncTaskLoader<ArrayList<ContentsListItem>> {
    ArrayList<ContentsListItem> blogs;
    public static final int LOADER_ID = 123;

    private final BlogsReader blogsReader;

    public ContentsListLoader(Context context, BlogsReader reader) {
        super(context);
        this.blogsReader = reader;
    }

    @Override
    public ArrayList<ContentsListItem> loadInBackground() {


        return blogsReader.getAll();
    }


    @Override
    public void deliverResult(ArrayList<ContentsListItem> data) {
        // Here we cache our response.
        blogs = data;
        Log.v("Debug", "deliver result");
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        // use cached result - and call deliverResult(), or forceLoad()
        if (blogs != null && blogs.size() > 0) {
            super.deliverResult(blogs);
            return;
        }

        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Stop the Loader if it is currently running.
        onStopLoading();

        // Get rid of our cache if it exists.
        blogs = null;
    }

}
