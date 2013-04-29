package com.shivgadhia.android.tomato.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.fragments.GridFragment;
import com.shivgadhia.android.tomato.service.GetPostsReceiver;
import com.shivgadhia.android.tomato.service.GetPostsService;

public class TomatoActivity extends Activity {
    private GridFragment mGridFragment;

    private OnQueryTextListener queryListener = new OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return fetchPosts(query);
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        fetchPosts(null);

    }

    private boolean fetchPosts(String postUrl) {
        if (!TextUtils.isEmpty(postUrl)) {
            Intent msgIntent = new Intent(this, GetPostsService.class);
            msgIntent.putExtra(GetPostsService.PARAM_IN_POST_URL, postUrl);
            startService(msgIntent);
            return true;
        }
        return false;
    }

    private void initFragments() {
        FragmentManager fm = getFragmentManager();

        mGridFragment = new GridFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_content, mGridFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_home, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryListener);
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFragments();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }
}


/* attribution :
�Tomato� symbol used on the app icon is by Alessandro Suraci, from thenounproject.com collection.
*/