package com.shivgadhia.android.tomato.fragments;

import android.app.LoaderManager;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.loaders.ContentsListLoader;
import com.shivgadhia.android.tomato.loaders.PostLoader;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.Blogs.BlogsReader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.service.GetPostsReceiver;
import com.shivgadhia.android.tomato.service.GetPostsService;

import java.util.ArrayList;

public class TitlePageFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ContentsListItem>> {

    private ListView contentsList;
    private ArrayList<ContentsListItem> data;
    private PostsFetchedReceiver receiver;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        receiver = new PostsFetchedReceiver();
        IntentFilter filter = new IntentFilter(GetPostsReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(receiver, filter);
    }

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
        this.data = data;
        ArrayAdapter<ContentsListItem> adapter = new ArrayAdapter<ContentsListItem>(getActivity(),
                android.R.layout.simple_list_item_1, data);
        contentsList.setAdapter(adapter);
        contentsList.setOnItemClickListener(onBlogTitleClicked);

    }

    private AdapterView.OnItemClickListener onBlogTitleClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = data.get(position).asUrl();
            fetchPosts(url);
        }
    };

    private void fetchPosts(String url) {
        Intent msgIntent = new Intent(getActivity(), GetPostsService.class);
        msgIntent.putExtra(GetPostsService.PARAM_IN_POST_URL, url);
        getActivity().startService(msgIntent);
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<ContentsListItem>> loader) {
    }


    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    private class PostsFetchedReceiver extends BroadcastReceiver {


        public static final String ACTION_RESP =
                "com.shivgadhia.android.tomato.POSTS_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            initLoader();
        }
    }

}
