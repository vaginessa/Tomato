package com.shivgadhia.android.tomato.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;

public class OneImagePageFragment extends PageLayoutFragment {
    private ImageView one;
    private static int[] LAYOUTS = new int[]{R.layout.page_one_image, R.layout.page_one_image_alt};


    public OneImagePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);
        one = (ImageView) v.findViewById(R.id.page_three_images_1);
        if (!images.isEmpty()) {
            setImage(one, images.get(0));
        }
        return v;
    }

    public static Fragment newInstance(int pos) {
        OneImagePageFragment threeImagePageFragment = new OneImagePageFragment();
        threeImagePageFragment.setPosition(pos);

        return threeImagePageFragment;
    }

    protected int getLayout() {
        int index = (int) (Math.random() * LAYOUTS.length);
        return LAYOUTS[index];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageTagFactory = new ImageTagFactory(getActivity(), R.drawable.ic_launcher);
    }
}
