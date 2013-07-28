package com.shivgadhia.android.tomato.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.models.ImageModel;

public class ThreeImagePageFragment extends PageLayoutFragment {


    private ImageView one;
    private ImageView two;
    private ImageView three;
    private static int[] LAYOUTS = new int[]{R.layout.page_three_images_alt, R.layout.page_three_images, R.layout.page_three_images_alt2};
    private LinearLayout one_actions;
    private LinearLayout two_actions;
    private LinearLayout three_actions;

    public ThreeImagePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);
        one = (ImageView) v.findViewById(R.id.page_three_images_1);
        one_actions = (LinearLayout) v.findViewById(R.id.page_three_images_1_actions);
        two = (ImageView) v.findViewById(R.id.page_three_images_2);
        two_actions = (LinearLayout) v.findViewById(R.id.page_three_images_2_actions);
        three = (ImageView) v.findViewById(R.id.page_three_images_3);
        three_actions = (LinearLayout) v.findViewById(R.id.page_three_images_3_actions);

        setImage(one, one_actions, images.get(0));
        setImage(two, two_actions, images.get(1));
        setImage(three, three_actions, images.get(2));
        return v;
    }

    public static Fragment newInstance(int pos) {
        ThreeImagePageFragment threeImagePageFragment = new ThreeImagePageFragment();
        threeImagePageFragment.setPosition(pos);

        return threeImagePageFragment;
    }


    protected int getLayout() {
        return LAYOUTS[position % LAYOUTS.length];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageTagFactory = new ImageTagFactory(getActivity(), R.drawable.ic_launcher);
    }

    @Override
    protected void onImageLongClicked(ImageModel imageModel, LinearLayout actionsView) {
        one_actions.setVisibility(View.GONE);
        two_actions.setVisibility(View.GONE);
        three_actions.setVisibility(View.GONE);
        actionsView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onImageClicked(ImageModel imageModel, LinearLayout actionsView) {
        showPhoto(imageModel.getPostId());
    }


}
