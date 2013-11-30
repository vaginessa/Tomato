package com.shivgadhia.android.tomato.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.activities.PhotoViewPagerActivity;
import com.shivgadhia.android.tomato.models.ImageModel;

import java.util.ArrayList;

public abstract class PageLayoutFragment extends Fragment {
    protected ArrayList<ImageModel> images;
    ImageTagFactory imageTagFactory;
    protected int position;

    protected abstract int getLayout();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        State state = new State(savedInstanceState);
        ArrayList<ImageModel> savedImages = state.getSavedImages();
        if (savedImages != null && !savedImages.isEmpty()) {
            setImages(savedImages);
        }
        setRetainInstance(true);
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images = images;
    }

    protected void setImage(ImageView imageView, ImageModel imageModel) {
        try {
            ImageTag tag = imageTagFactory.build(imageModel.getSmallUrl());
            imageView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(imageView);

            imageView.setOnClickListener(createClickListener(imageView, imageModel));

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener createClickListener(final ImageView imageView, final ImageModel imageModel) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClicked(imageView, imageModel);
            }
        };
    }

    protected abstract void onImageClicked(ImageView imageView, ImageModel imageModel);

    protected void showPhoto(ImageView imageView, String postId) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bm = drawable.getBitmap();
        Intent subActivity = new Intent(getActivity(), PhotoViewPagerActivity.class);
        subActivity.putExtra(PhotoViewPagerActivity.EXTRA_POST_ID, postId);
        Bundle scaleBundle = ActivityOptions.makeThumbnailScaleUpAnimation(
                imageView, bm, 0, 0).toBundle();
        getActivity().startActivity(subActivity, scaleBundle);


    }

    protected void setPosition(int pos) {
        this.position = pos;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        State state = new State(outState);
        state.saveImages(images);
    }

    private static class State {
        private static final String KEY_IMAGES = "key_images";
        private static final String KEY_POSITION = "key_position";
        private Bundle state;

        public State(Bundle state) {
            if (state == null) {
                this.state = new Bundle();
            } else {
                this.state = state;
            }
        }

        public void saveImages(ArrayList<ImageModel> images) {
            this.state.putParcelableArrayList(KEY_IMAGES, images);
        }

        public ArrayList<ImageModel> getSavedImages() {
            return this.state.getParcelableArrayList(KEY_IMAGES);
        }
    }
}
