package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.shivgadhia.android.tomato.persistance.TomatoProvider;

public class RandomPostReader extends PostReader {

    public RandomPostReader(DatabaseReader databaseReader) {
        super(databaseReader);
    }

    @Override
    public Loader<Cursor> getAll(Context context) {
        return new CursorLoader(context, Uri.parse(TomatoProvider.AUTHORITY + Tables.TBL_POSTS), null, null, new String[]{}, "RANDOM() LIMIT 1");
    }
}
