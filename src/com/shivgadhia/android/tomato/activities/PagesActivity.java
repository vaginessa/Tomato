package com.shivgadhia.android.tomato.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.shivgadhia.android.tomato.fragments.ThreeImagePageFragment;
import com.shivgadhia.android.tomato.fragments.TitlePageFragment;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;
import uk.co.senab.photoview.sample.HackyViewPager;

import java.util.ArrayList;

public class PagesActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<ImageModel>> {

    private HackyViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionbar();

        mViewPager = new HackyViewPager(this);
        mViewPager.setId(0x7F04FFF0);
        mViewPager.setOffscreenPageLimit(1);

        setContentView(mViewPager);
        initLoader();
    }

    private void setupActionbar() {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
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
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(samplePagerAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ImageModel>> loader) {
    }

    private class SamplePagerAdapter extends FragmentPagerAdapter {


        private final ArrayList<ImageModel> images;

        public SamplePagerAdapter(FragmentManager fm, ArrayList<ImageModel> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TitlePageFragment();
            } else {

                return getPage(position);
            }
        }

        private Fragment getPage(int position) {
            int startPos = (position - 1) * 3;
            ThreeImagePageFragment fragment = (ThreeImagePageFragment) ThreeImagePageFragment.newInstance(position);
            fragment.setImages(images.subList(startPos, startPos + 3));
            return fragment;

        }

        @Override
        public int getCount() {
            return images.size() / 3;
        }
    }
}