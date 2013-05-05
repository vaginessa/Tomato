package com.shivgadhia.android.tomato.fragments;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.models.ImageModel;

import java.util.List;

public abstract class PageLayoutFragment extends Fragment {
    protected List<ImageModel> images;
    ImageTagFactory imageTagFactory;
    private int pos;

    protected abstract int getLayout();

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    protected void setImage(ImageView imageView, ImageModel imageModel) {
        try {
            ImageTag tag = imageTagFactory.build(imageModel.getBigUrl());
            imageView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(imageView);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    protected void setPosition(int pos) {
        this.pos = pos;
    }
}
