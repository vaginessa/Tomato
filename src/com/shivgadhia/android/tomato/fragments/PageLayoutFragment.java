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
        setPosition(state.getSavedPosition());
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images = images;
    }

    protected void setImage(ImageView imageView, LinearLayout actionsView, ImageModel imageModel) {
        try {
            ImageTag tag = imageTagFactory.build(imageModel.getSmallUrl());
            imageView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(imageView);

            imageView.setOnClickListener(createClickListener(imageModel, actionsView));
            imageView.setOnLongClickListener(createLongPressListener(imageModel, actionsView));
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

    private View.OnLongClickListener createLongPressListener(final ImageModel imageModel, final LinearLayout actionsView) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onImageLongClicked(imageModel, actionsView);
                return true;
            }
        };
    }

    protected abstract void onImageLongClicked(ImageModel imageModel, LinearLayout actionsView);

    protected abstract void onImageClicked(ImageModel imageModel, LinearLayout actionsView);

    protected void showPhoto(String postId) {
        Intent showPhoto = new Intent(getActivity(), PhotoViewPagerActivity.class);
        showPhoto.putExtra(PhotoViewPagerActivity.EXTRA_POST_ID, postId);
        startActivity(showPhoto);
    }

    protected void setPosition(int pos) {
        this.position = pos;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        State state = new State(outState);
        state.saveImages(images);
        state.savePosition(position);
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

        public void savePosition(int pos) {
            this.state.putInt(KEY_POSITION, pos);
        }

        public ArrayList<ImageModel> getSavedImages() {
            return this.state.getParcelableArrayList(KEY_IMAGES);
        }

        public int getSavedPosition() {
            return this.state.getInt(KEY_POSITION, 0);
        }
    }
}
