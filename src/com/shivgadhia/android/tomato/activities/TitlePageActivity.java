package com.shivgadhia.android.tomato.activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

public class TitlePageActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionbar();
        setContentView(R.layout.title_page_activity_layout);

        Cursor query = getContentResolver().query(Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_TAGS_FOR_POSTS), null, null, null, null);
        query.moveToFirst();
        while (query.moveToNext()) {
            Log.v("TAGS", query.getString(query.getColumnIndexOrThrow(Tables.TagsForPosts.COL_TAG)));
        }
    }

    private void setupActionbar() {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }
}
