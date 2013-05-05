package com.shivgadhia.android.tomato.persistance.Blogs;

import android.database.Cursor;
import android.util.Log;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;

import java.util.ArrayList;

public class BlogsReader {
    private final DatabaseReader databaseReader;

    public BlogsReader(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public ArrayList<ContentsListItem> getAll() {
        Cursor cursor = databaseReader.getAllFrom(Tables.VIEW_BLOGS);
        ArrayList<ContentsListItem> blogs = populateListWith(cursor);

        cursor.close();

        return blogs;
    }

    private ArrayList<ContentsListItem> populateListWith(Cursor cursor) {
        ArrayList<ContentsListItem> data = new ArrayList<ContentsListItem>();
        if (cursor.moveToFirst()) {
            do {
                data.add(getPost(cursor));
            } while (cursor.moveToNext());
        } else {
            Log.e("PostReader", "No data in the cursor.");
        }
        return data;
    }

    private ContentsListItem getPost(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Blogs.COL_BLOG_NAME));
        return new ContentsListItem(name);
    }
}
