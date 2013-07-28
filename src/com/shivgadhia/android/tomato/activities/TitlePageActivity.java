package com.shivgadhia.android.tomato.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Posts.PostReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

public class TitlePageActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionbar();
        setContentView(R.layout.title_page_activity_layout);

//        getContentResolver().delete(Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_POSTS), Tables.Posts.COL_TAGS + " != ''", new String[]{});
    }

    private void setupActionbar() {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }
}
