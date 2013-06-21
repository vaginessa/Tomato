package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.shivgadhia.android.tomato.models.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

import java.util.ArrayList;

public class PostReader {
    protected final DatabaseReader databaseReader;

    public PostReader(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public ArrayList<ImageModel> getAll() {
        Cursor cursor = getCursor();
        ArrayList<ImageModel> imageModels = populateListWith(cursor);

        cursor.close();

        return imageModels;
    }

    protected Cursor getCursor() {
        return databaseReader.getAllAndSortBy(Tables.TBL_POSTS, Tables.Posts.COL_POST_DATE + " DESC");
    }

    public ArrayList<ImageModel> populateListWith(Cursor cursor) {
        ArrayList<ImageModel> data = new ArrayList<ImageModel>();
        if (cursor.moveToFirst()) {
            do {
                ImageModel imageModel = getPost(cursor);
                data.add(imageModel);
            } while (cursor.moveToNext());
        } else {
            Log.e("PostReader", "No data in the cursor.");
        }
        return data;
    }

    private ImageModel getPost(Cursor cursor) {
        String url_small = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_PHOTO_IMAGE_SMALL));
        String url_big = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_PHOTO_IMAGE_LARGE));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_BLOG_NAME));
        String post_id = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts._ID));
        return new ImageModel(url_small, url_big, title, post_id);
    }

    public Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context, Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_POSTS), null, null, new String[]{}, Tables.Posts.COL_POST_DATE + " DESC");
    }
}
