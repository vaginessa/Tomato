package com.shivgadhia.android.tomato.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;

import java.util.ArrayList;

public class PostLoader extends AsyncTaskLoader<ArrayList<ImageModel>> {
    ArrayList<ImageModel> mImageModels;
    public static final int LOADER_ID = 123;

    private final PostReader postReader;
    public PostLoader(Context context, PostReader postReader) {
        super(context);
        this.postReader = postReader;
    }

    @Override
    public ArrayList<ImageModel> loadInBackground() {


        return postReader.getAll();
    }



    @Override
    public void deliverResult(ArrayList<ImageModel> data) {
        // Here we cache our response.
        mImageModels = data;
        Log.v("Debug", "deliver result");
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        // use cached result - and call deliverResult(), or forceLoad()
        if(mImageModels != null && mImageModels.size() > 0){
            super.deliverResult(mImageModels);
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
        mImageModels = null;
    }

}
