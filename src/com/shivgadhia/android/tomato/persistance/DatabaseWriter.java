package com.shivgadhia.android.tomato.persistance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;


public class DatabaseWriter {

    private final ContentResolver contentResolver;

    public DatabaseWriter(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void saveDataToPostsTable(ContentValues values) {
        saveDataToTable(Tables.TBL_POSTS, values);
    }

    public void saveTagsForPosts(ContentValues tagsValues) {
        saveDataToTable(Tables.TBL_TAGS_FOR_POSTS, tagsValues);
    }

    private void saveDataToTable(String table, ContentValues values) {
        Uri uri = createUri(table);
        contentResolver.insert(uri, values);
    }

    private Uri createUri(String tableName) {
        return Uri.parse(TomatoProvider.AUTHORITY + tableName);
    }

    public void deletePostsAndTags(String blogName) {
        Uri uri = createUri(Tables.TBL_POSTS);
        contentResolver.delete(uri, Tables.Posts.COL_BLOG_NAME + " =?", new String[]{blogName});
    }
}
