package com.shivgadhia.android.tomato.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;

import java.util.ArrayList;

public class GridFragment extends Fragment implements LoaderCallbacks<ArrayList<ImageModel>> {
    private ArrayList<ImageModel> mList = new ArrayList<ImageModel>();
    private GridView mGridView;
    private ImageGridAdapter mAdapter;
    private Context mContext;


    public void initLoader() {
        LoaderManager lm = getLoaderManager();
        lm.destroyLoader(PostLoader.LOADER_ID);
        lm.initLoader(PostLoader.LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new ImageGridAdapter(mContext, null);
        mGridView.setAdapter(mAdapter);
        initLoader();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mContext = getActivity();

        View v = inflater.inflate(R.layout.grid_fragment_layout, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridview1);

        return v;
    }

    @Override
    public Loader<ArrayList<ImageModel>> onCreateLoader(int id, Bundle args) {
        PostReader postReader = new PostReader(new DatabaseReader(getActivity().getContentResolver()));
        return new PostLoader(getActivity(), postReader);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ImageModel>> arg0,
                               ArrayList<ImageModel> arg1) {

        mList = arg1;
        mAdapter.updateData(mList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ImageModel>> arg0) {
        mList.clear();
        mAdapter.updateData(mList);
        mAdapter.notifyDataSetChanged();
    }

}
