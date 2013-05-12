package com.shivgadhia.android.tomato.persistance.Posts;

import android.database.Cursor;
import com.shivgadhia.android.tomato.persistance.DatabaseReader;
import com.shivgadhia.android.tomato.persistance.Tables;

public class SinglePostReader extends PostReader {
    private String postId;

    public SinglePostReader(DatabaseReader databaseReader, String postId) {
        super(databaseReader);
        this.postId = postId;
    }

    @Override
    protected Cursor getCursor() {
        return databaseReader.getAllFrom(Tables.TBL_POSTS, Tables.Posts.COL_ID + "=?", new String[]{postId});
    }
}
