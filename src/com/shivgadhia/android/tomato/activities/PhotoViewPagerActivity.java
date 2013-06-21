package com.shivgadhia.android.tomato.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.SinglePostReader;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.sample.HackyViewPager;

import java.util.ArrayList;

public class PhotoViewPagerActivity extends Activity implements PostLoader.DataUpdatedListener {

    public static final String EXTRA_POST_ID = "extraPostId";
    ImageTagFactory imageTagFactory;
    private HackyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        imageTagFactory = new ImageTagFactory(this, R.drawable.ic_launcher);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        initLoader();
    }


    public void initLoader() {
        PostLoader postLoader = new PostLoader(this, getLoaderManager(), new SinglePostReader(new DatabaseReader(getContentResolver()), getPostId()), this);
        postLoader.initLoader();
    }

    private String getPostId() {
        return getIntent().getStringExtra(EXTRA_POST_ID);
    }

    @Override
    public void dataUpdated(ArrayList<ImageModel> list) {
        mViewPager.setAdapter(new SamplePagerAdapter(list));
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
