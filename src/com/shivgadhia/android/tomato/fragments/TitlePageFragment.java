package com.shivgadhia.android.tomato.fragments;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.activities.PagesActivity;
import com.shivgadhia.android.tomato.loaders.BlogsLoader;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.Blogs.BlogsReader;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.DatabaseWriter;
import com.shivgadhia.android.tomato.persistance.Posts.PostWriter;
import com.shivgadhia.android.tomato.service.GetPostsReceiver;
import com.shivgadhia.android.tomato.service.GetPostsService;

import java.util.ArrayList;

public class TitlePageFragment extends Fragment implements BlogsLoader.DataUpdatedListener, ContentsAdapter.ActionsListener {

    private ListView contentsList;
    private ArrayList<ContentsListItem> data;
    private PostsFetchedReceiver receiver;
    private EditText blogNameInput;
    private ImageButton blogNameSearch;

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
        blogNameInput = (EditText) v.findViewById(R.id.add_blog_input);
        blogNameSearch = (ImageButton) v.findViewById(R.id.add_blog_submit);

        blogNameSearch.setOnClickListener(blogNameSearchListener);

        initLoader();
        return v;
    }

    private View.OnClickListener blogNameSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputUrl = blogNameInput.getText().toString();
            fetchPosts(inputUrl);
        }
    };

    private void clearEditText() {
        blogNameInput.setText("");
    }

    public void initLoader() {
        LoaderManager lm = getActivity().getLoaderManager();
        BlogsLoader loader = new BlogsLoader(getActivity(), lm, new BlogsReader(new DatabaseReader(getActivity().getContentResolver())), this);
        loader.initLoader();
    }

    @Override
    public void dataUpdated(ArrayList<ContentsListItem> list) {
        this.data = list;
        ContentsAdapter adapter = new ContentsAdapter(getActivity(), data, this);
        contentsList.setAdapter(adapter);
        contentsList.setOnItemClickListener(onBlogTitleClicked);
    }


    private AdapterView.OnItemClickListener onBlogTitleClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = data.get(position).asUrl();

            String blogName = data.get(position).getTitle();
            Intent showPosts = new Intent(getActivity(), PagesActivity.class);
            showPosts.putExtra(PagesActivity.EXTRA_BLOG_NAME, blogName);
            startActivity(showPosts);
        }
    };

    private void fetchPosts(String url) {
        Intent msgIntent = new Intent(getActivity(), GetPostsService.class);
        msgIntent.putExtra(GetPostsService.PARAM_IN_POST_URL, url);
        getActivity().startService(msgIntent);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onRefreshClicked(ContentsListItem item) {
        fetchPosts(item.asUrl());
    }

    @Override
    public void onDeleteClicked(ContentsListItem item) {
        new PostWriter(new DatabaseWriter(getActivity().getContentResolver())).deletePostsFor(item.getTitle());
    }

    private class PostsFetchedReceiver extends BroadcastReceiver {


        public static final String ACTION_RESP =
                "com.shivgadhia.android.tomato.POSTS_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            initLoader();
            clearEditText();
        }
    }

}
