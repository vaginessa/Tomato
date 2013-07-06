package com.shivgadhia.android.tomato.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.activities.PhotoViewPagerActivity;
import com.shivgadhia.android.tomato.models.ImageModel;

import java.util.List;

public abstract class PageLayoutFragment extends Fragment {
    protected List<ImageModel> images;
    ImageTagFactory imageTagFactory;
    protected int position;

    protected abstract int getLayout();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    protected void setImage(ImageView imageView, LinearLayout actionsView, ImageModel imageModel) {
        try {
            ImageTag tag = imageTagFactory.build(imageModel.getSmallUrl());
            imageView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(imageView);

            imageView.setOnClickListener(createClickListener(imageModel, actionsView));
            setupClickListenersForActionView(actionsView, imageModel);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void setupClickListenersForActionView(LinearLayout actionsView, ImageModel imageModel) {
        actionsView.findViewById(R.id.btn_open).setOnClickListener(createOnOpenClicked(imageModel.getPostId()));
        actionsView.findViewById(R.id.btn_share).setOnClickListener(createOnShareClicked(imageModel));
        actionsView.findViewById(R.id.btn_go).setOnClickListener(createOnGoClicked(imageModel));
    }

    private View.OnClickListener createOnGoClicked(final ImageModel imageModel) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(imageModel.getPostUrl()));
                startActivity(Intent.createChooser(i, getResources().getText(R.string.go_to)));
            }
        };
    }

    private View.OnClickListener createOnShareClicked(final ImageModel imageModel) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, imageModel.getPostUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        };
    }

    private View.OnClickListener createOnOpenClicked(final String postId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoto(postId);
            }
        };
    }

    private View.OnClickListener createClickListener(final ImageModel imageModel, final LinearLayout actionsView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClicked(imageModel, actionsView);
            }
        };
    }

    protected abstract void onImageClicked(ImageModel imageModel, LinearLayout actionsView);

    protected void showPhoto(String postId) {
        Intent showPhoto = new Intent(getActivity(), PhotoViewPagerActivity.class);
        showPhoto.putExtra(PhotoViewPagerActivity.EXTRA_POST_ID, postId);
        startActivity(showPhoto);
    }

    protected void setPosition(int pos) {
        this.position = pos;
    }
}
