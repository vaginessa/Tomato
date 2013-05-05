package com.shivgadhia.android.tomato.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.loaders.ContentsListLoader;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.Blogs.BlogsReader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;

import java.util.ArrayList;

public class TitlePageFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ContentsListItem>> {

    private ListView contentsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_title_contents, container, false);
        contentsList = (ListView) v.findViewById(R.id.page_title_contents_list);
        initLoader();
        return v;
    }

    public void initLoader() {
        LoaderManager lm = getActivity().getLoaderManager();
        lm.destroyLoader(PostLoader.LOADER_ID);
        lm.initLoader(PostLoader.LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<ContentsListItem>> onCreateLoader(int id, Bundle args) {
        return new ContentsListLoader(getActivity(), new BlogsReader(new DatabaseReader(getActivity().getContentResolver())));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ContentsListItem>> loader, ArrayList<ContentsListItem> data) {
        ArrayAdapter<ContentsListItem> adapter = new ArrayAdapter<ContentsListItem>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        contentsList.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ContentsListItem>> loader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
