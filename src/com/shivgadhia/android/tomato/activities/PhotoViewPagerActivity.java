package com.shivgadhia.android.tomato.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.sample.*;

import java.util.ArrayList;

public class PhotoViewPagerActivity extends Activity implements LoaderManager.LoaderCallbacks<ArrayList<ImageModel>> {

    ImageTagFactory imageTagFactory;
    private HackyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        imageTagFactory = new ImageTagFactory(this, R.drawable.ic_launcher);
        ArrayList<ImageModel> imageModels = new ArrayList<ImageModel>();
        imageModels.add(new ImageModel("http://24.media.tumblr.com/tumblr_m98uytdr3Z1rxiqm6o1_1280.jpg","http://25.media.tumblr.com/e7ad7269fdda256c800a21b4e4839601/tumblr_ml7jr4UbeX1qflpkjo1_1280.jpg", "title"));
        imageModels.add(new ImageModel("http://25.media.tumblr.com/e7ad7269fdda256c800a21b4e4839601/tumblr_ml7jr4UbeX1qflpkjo1_1280.jpg","http://25.media.tumblr.com/e7ad7269fdda256c800a21b4e4839601/tumblr_ml7jr4UbeX1qflpkjo1_1280.jpg", "title"));
        imageModels.add(new ImageModel("http://25.media.tumblr.com/tumblr_m6hpirkbhE1r674r7o1_500.jpg", "http://25.media.tumblr.com/tumblr_m6hpirkbhE1r674r7o1_500.jpg", "title"));
        initLoader();
    }


    public void initLoader() {
        LoaderManager lm = getLoaderManager();
        lm.destroyLoader(PostLoader.LOADER_ID);
        lm.initLoader(PostLoader.LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<ImageModel>> onCreateLoader(int id, Bundle args) {
        PostReader postReader = new PostReader(new DatabaseReader(getContentResolver()));
        return new PostLoader(this, postReader);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ImageModel>> loader, ArrayList<ImageModel> data) {
        mViewPager.setAdapter(new SamplePagerAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ImageModel>> loader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class SamplePagerAdapter extends PagerAdapter {

        private ArrayList<ImageModel> images;

        private SamplePagerAdapter(ArrayList<ImageModel> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageModel imageModel = images.get(position);
            ImageTag tag = imageTagFactory.build(imageModel.getBigUrl());
            photoView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
