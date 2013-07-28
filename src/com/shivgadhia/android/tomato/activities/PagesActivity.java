package com.shivgadhia.android.tomato.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.shivgadhia.android.tomato.fragments.ThreeImagePageFragment;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReaderForBlog;
import uk.co.senab.photoview.sample.HackyViewPager;

import java.util.ArrayList;

public class PagesActivity extends FragmentActivity implements PostLoader.DataUpdatedListener {

    public static final String EXTRA_BLOG_NAME = "extraBlogName";
    private HackyViewPager mViewPager;
    private PostLoader postLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createViewPager();
        setContentView(mViewPager);
        State state = new State(savedInstanceState);
        String blogName = state.getBlogName();

        if (blogName.isEmpty()) {
            blogName = getBlogName();
        }

        setupActionbar(blogName);
        initLoader(blogName);
    }

    private void createViewPager() {
        mViewPager = new HackyViewPager(this);
        mViewPager.setId(0x7F04FFF0);
        mViewPager.setOffscreenPageLimit(1);
    }

    private void initLoader(String blogName) {
        postLoader = new PostLoader(this, getLoaderManager(), new PostReaderForBlog(new DatabaseReader(getContentResolver()), blogName), this);
        postLoader.initLoader();
    }

    private void setupActionbar(String title) {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setTitle(title);
    }

    private String getBlogName() {
        return getIntent().getStringExtra(EXTRA_BLOG_NAME);
    }

    @Override
    public void dataUpdated(ArrayList<ImageModel> list) {
        int currentItem = mViewPager.getCurrentItem();
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(samplePagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.requestLayout();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        State state = new State(outState);
        state.saveBlogName(getBlogName());
    }

    private class SamplePagerAdapter extends FragmentPagerAdapter {


        private final ArrayList<ImageModel> images;

        public SamplePagerAdapter(FragmentManager fm, ArrayList<ImageModel> images) {
            super(fm);
            this.images = images;
        }

        @Override
        public Fragment getItem(int position) {
            return getPage(position);
        }

        private Fragment getPage(int position) {
            int startPos = (position) * 3;
            ThreeImagePageFragment fragment = (ThreeImagePageFragment) ThreeImagePageFragment.newInstance(position);
            fragment.setImages(images.subList(startPos, startPos + 3));
            return fragment;

        }

        @Override
        public int getCount() {
            return images.size() / 3;
        }
    }


    private static class State {
        private static final String STATE_BLOG_NAME = "blogName";
        private Bundle state;

        State(Bundle state) {
            this.state = state;
        }

        public void saveBlogName(String blogname) {
            state.putString(STATE_BLOG_NAME, blogname);
        }

        public String getBlogName() {
            if (state != null) {
                return state.getString(STATE_BLOG_NAME);
            }
            return "";
        }
    }
}