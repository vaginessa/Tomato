package com.shivgadhia.android.tomato.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.shivgadhia.android.tomato.R;

public class TitlePageActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionbar();
        setContentView(R.layout.title_page_activity_layout);
    }

    private void setupActionbar() {
        getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }
}
