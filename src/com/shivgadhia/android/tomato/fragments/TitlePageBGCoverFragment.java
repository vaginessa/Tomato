package com.shivgadhia.android.tomato.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.RandomPostReader;

import java.util.ArrayList;

public class TitlePageBGCoverFragment extends Fragment implements PostLoader.DataUpdatedListener {


    private ImageView coverBackground;
    private ImageTagFactory imageTagFactory;
    private PostLoader postLoader;

    private String bgImageUrl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageTagFactory = new ImageTagFactory(getActivity(), R.drawable.ic_launcher);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_title_bg_cover, container, false);
        coverBackground = (ImageView) v.findViewById(R.id.page_title_bg);
        State state = new State(savedInstanceState);
        if (state.hasImageUrl()) {
            setImage(state.getImageUrl());
        } else {
            initLoader();
        }
        return v;
    }

    public void initLoader() {
        postLoader = new PostLoader(getActivity(), getActivity().getLoaderManager(), new RandomPostReader(new DatabaseReader(getActivity().getContentResolver())), this);
        postLoader.initLoader();
    }

    @Override
    public void dataUpdated(ArrayList<ImageModel> list) {
        postLoader.destroyLoader();
        if (!list.isEmpty()) {
            ImageModel imageModel = list.get(0);
            String imageUrl = imageModel.getBigUrl();
            setImage(imageUrl);
        }
    }

    private void setImage(String imageUrl) {
        bgImageUrl = imageUrl;
        ImageTag tag = imageTagFactory.build(imageUrl);
        coverBackground.setTag(tag);
        TomatoApplication.getImageManager().getLoader().load(coverBackground);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        new State(outState).setSavedImageUrl(bgImageUrl);
    }

    private class State {
        private static final String SAVED_IMAGE_URL = "savedImageUrl";
        private Bundle bundle;

        public State(Bundle bundle) {
            if (bundle == null) {
                this.bundle = new Bundle();
            } else {
                this.bundle = bundle;
            }
        }

        public String getImageUrl() {
            return bundle.getString(SAVED_IMAGE_URL);
        }

        public void setSavedImageUrl(String imageUrl) {
            bundle.putString(SAVED_IMAGE_URL, imageUrl);
        }

        public boolean hasImageUrl() {
            return !TextUtils.isEmpty(getImageUrl());
        }
    }
}
