package com.shivgadhia.android.tomato.loaders;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.Blogs.BlogsReader;

import java.util.ArrayList;

public class BlogsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    ArrayList<ContentsListItem> imageModels;
    public static final int LOADER_ID = 124;

    private LoaderManager loaderManager;
    private final BlogsReader blogsReader;
    private Context context;
    private DataUpdatedListener dataUpdatedListener;

    public interface DataUpdatedListener {
        void dataUpdated(ArrayList<ContentsListItem> list);
    }

    public BlogsLoader(Context context, LoaderManager lm, BlogsReader blogsReader, DataUpdatedListener dataUpdatedListener) {
        loaderManager = lm;
        this.blogsReader = blogsReader;
        this.context = context;
        this.dataUpdatedListener = dataUpdatedListener;
    }

    public void initLoader() {
        destroyLoader();
        loaderManager.initLoader(LOADER_ID, null, this);
    }

    public void destroyLoader() {
        loaderManager.destroyLoader(LOADER_ID);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return blogsReader.getAll(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        imageModels = blogsReader.populateListWith(data);
        dataUpdatedListener.dataUpdated(imageModels);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        imageModels.clear();
    }
}
