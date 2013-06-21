package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

public class PostReaderForBlog extends PostReader {
    private final String blogName;

    public PostReaderForBlog(DatabaseReader databaseReader, String blogName) {
        super(databaseReader);

        this.blogName = blogName;
    }

    @Override
    protected Cursor getCursor() {
        return databaseReader.getAllFrom(Tables.TBL_POSTS, Tables.Posts.COL_BLOG_NAME + "=?", new String[]{blogName});
    }

    @Override
    public Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context, Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_POSTS), null, Tables.Posts.COL_BLOG_NAME + "=?", new String[]{blogName}, Tables.Posts.COL_POST_DATE + " DESC");
    }

}
