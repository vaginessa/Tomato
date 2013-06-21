package com.shivgadhia.android.tomato.persistance.Blogs;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.shivgadhia.android.tomato.models.ContentsListItem;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

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

    public ArrayList<ContentsListItem> populateListWith(Cursor cursor) {
        ArrayList<ContentsListItem> data = new ArrayList<ContentsListItem>();
        if (cursor.moveToFirst()) {
            do {
                data.add(getBlog(cursor));
            } while (cursor.moveToNext());
        } else {
            Log.e("PostReader", "No data in the cursor.");
        }
        return data;
    }

    private ContentsListItem getBlog(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Blogs.COL_BLOG_NAME));
        return new ContentsListItem(name);
    }

    public Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context, Uri.parse(TomatoProvider.AUTHORITY + Tables.VIEW_BLOGS), null, null, new String[]{}, null);
    }
}
