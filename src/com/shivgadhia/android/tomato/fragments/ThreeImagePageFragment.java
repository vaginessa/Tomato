package com.shivgadhia.android.tomato.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;

import java.util.ArrayList;
import java.util.List;

public class ThreeImagePageFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ImageModel>> {
    ImageTagFactory imageTagFactory;
    private int pos;
    private ImageView one;
    private ImageView two;
    private ImageView three;

    public ThreeImagePageFragment() {
    }

    public void initLoader() {
        LoaderManager lm = getActivity().getLoaderManager();
        lm.destroyLoader(PostLoader.LOADER_ID);
        lm.initLoader(PostLoader.LOADER_ID, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageTagFactory = new ImageTagFactory(getActivity(), R.drawable.ic_launcher);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_three_images, container, false);
        one = (ImageView) v.findViewById(R.id.page_three_images_1);
        two = (ImageView) v.findViewById(R.id.page_three_images_2);
        three = (ImageView) v.findViewById(R.id.page_three_images_3);
        initLoader();
        return v;
    }

    public static Fragment newInstance(int pos) {
        ThreeImagePageFragment threeImagePageFragment = new ThreeImagePageFragment();
        threeImagePageFragment.setPosition(pos);

        return threeImagePageFragment;
    }


    private void setImages(List<ImageModel> images) {

        int pos_one = (pos * 3) + 0;
        setImage(images, one, pos_one);

        int pos_two = (pos * 3) + 1;
        setImage(images, two, pos_two);

        int pos_three = (pos * 3) + 2;
        setImage(images, three, pos_three);
    }

    private void setImage(List<ImageModel> images, ImageView imageView, int pos_one) {
        try {
            ImageModel imageModel = images.get(pos_one);
            Log.v("ThreeImagePageFragment", pos_one + ". " + imageModel.getSmallUrl());
            ImageTag tag = imageTagFactory.build(imageModel.getBigUrl());
            imageView.setTag(tag);
            TomatoApplication.getImageManager().getLoader().load(imageView);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    private void setPosition(int pos) {
        this.pos = pos;
    }

    @Override
    public Loader<ArrayList<ImageModel>> onCreateLoader(int id, Bundle args) {
        PostReader postReader = new PostReader(new DatabaseReader(getActivity().getContentResolver()));
        return new PostLoader(getActivity(), postReader);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ImageModel>> loader, ArrayList<ImageModel> data) {
        setImages(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ImageModel>> loader) {
    }
}
