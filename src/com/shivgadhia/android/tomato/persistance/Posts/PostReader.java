package com.shivgadhia.android.tomato.persistance.Posts;

import android.database.Cursor;
import android.util.Log;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;

import java.util.ArrayList;

public class PostReader {
    private final DatabaseReader databaseReader;

    public PostReader(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public ArrayList<ImageModel> getAll() {
        Cursor cursor = databaseReader.getAllAndSortBy(Tables.TBL_POSTS, Tables.Posts.COL_POST_DATE + " DESC");
        ArrayList<ImageModel> imageModels = populateListWith(cursor);

        cursor.close();

        return imageModels;
    }

    private ArrayList<ImageModel> populateListWith(Cursor cursor) {
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
        String name = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_ID));
        String urlSmall = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_PHOTO_IMAGE_SMALL));
        String urlBig = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Posts.COL_PHOTO_IMAGE_LARGE));

        return new ImageModel(urlSmall, urlBig, name);
    }
}
