package com.shivgadhia.android.tomato.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.RESTLoaderFlickr;

import java.util.ArrayList;

public class GridFragment extends Fragment implements LoaderCallbacks<ArrayList<ImageModel>> {
    private static final String SAVED_SEARCH_CRITERIA = "saved_search_criteria";
    private ArrayList<ImageModel> mList = new ArrayList<ImageModel>();
    private GridView mGridView;
    private ImageGridAdapter mAdapter;
    private Context mContext;
    private String mSearchCriteria;

    public void searchWithCriteria(String criteria) {
        mSearchCriteria = criteria;
        Bundle searchterms = getBundle(mSearchCriteria);
        Log.v("Search", criteria);
        doSearch(searchterms);

    }

    private Bundle getBundle(String criteria) {

        Bundle args = new Bundle();
        args.putString("tags", criteria.replace(" ", "+"));

        Bundle searchterms = new Bundle();
        searchterms.putParcelable("searchterms", args);
        return searchterms;
    }


    public void doSearch(Bundle searchterms) {
        LoaderManager lm = getLoaderManager();
        lm.destroyLoader(0);
        lm.initLoader(0, searchterms, this);
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        mAdapter = new ImageGridAdapter(mContext, null);
        mGridView.setAdapter(mAdapter);

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
        if (args != null && args.containsKey("searchterms")) {
            Bundle q = args.getParcelable("searchterms");

            Log.v("Search", "q: " + q.getString("tags"));
            return new RESTLoaderFlickr(mContext, q.getString("tags"));
        } else {
            return new RESTLoaderFlickr(mContext);
        }

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
