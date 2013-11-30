package com.shivgadhia.android.tomato.activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.*;
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
    private String postUrl;
    private String bigImageUrl;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new HackyViewPager(this);
        setContentView(mViewPager);
        imageTagFactory = new ImageTagFactory(this, R.drawable.ic_launcher);

        setupActionBar();

        initLoader();
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_single_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
                startActivity(Intent.createChooser(i, getResources().getText(R.string.go_to)));
                return true;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getPostUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            case R.id.action_download:
                Uri Download_Uri = Uri.parse(bigImageUrl);
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                downloadManager.enqueue(request);
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }

    private String getPostUrl() {
        return postUrl;
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
        postUrl = list.get(0).getPostUrl();
        bigImageUrl = list.get(0).getBigUrl();
        updateActionBar(list);
    }

    private void updateActionBar(ArrayList<ImageModel> list) {
        getActionBar().setTitle(list.get(0).getTitle());
        getActionBar().setSubtitle(getPostUrl());
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
