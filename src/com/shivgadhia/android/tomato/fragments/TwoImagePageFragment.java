package com.shivgadhia.android.tomato.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;

public class TwoImagePageFragment extends PageLayoutFragment {
    private ImageView one;
    private ImageView two;
    private static int[] LAYOUTS = new int[]{R.layout.page_two_images};


    public TwoImagePageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);
        one = (ImageView) v.findViewById(R.id.page_three_images_1);
        two = (ImageView) v.findViewById(R.id.page_three_images_2);
        setImage(one, images.get(0));

        setImage(two, images.get(1));
        return v;
    }

    public static Fragment newInstance(int pos) {
        TwoImagePageFragment threeImagePageFragment = new TwoImagePageFragment();
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
