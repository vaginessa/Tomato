package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.ContentValues;
import com.shivgadhia.android.tomato.persistance.DatabaseWriter;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import novoda.lib.sqliteprovider.util.DatabaseUtils;

import java.util.Arrays;

public class PostWriter {
    private final DatabaseWriter databaseWriter;

    public PostWriter(DatabaseWriter databaseWriter) {
        this.databaseWriter = databaseWriter;
    }

    public void savePost(PhotoPost post) {

        ContentValues values = new ContentValues();
        values.put(Tables.Posts.COL_BLOG_NAME, post.getBlogName());
        values.put(Tables.Posts.COL_ID, post.getId());
        values.put(Tables.Posts.COL_POST_URL, post.getPostUrl());
        values.put(Tables.Posts.COL_TYPE, post.getType());
        values.put(Tables.Posts.COL_POST_TIME, post.getTimestamp());
        values.put(Tables.Posts.COL_POST_DATE, post.getDateGMT());
        values.put(Tables.Posts.COL_TAGS, Arrays.toString(post.getTags().toArray()));
        values.put(Tables.Posts.COL_SOURCE_URL, post.getSourceUrl());

        values.put(Tables.Posts.COL_PHOTO_CAPTION, DatabaseUtils.sqlEscapeString(post.getCaption()));

        for (int i = 0; i < post.getPhotos().size(); i++) {
            values.put(Tables.Posts.COL_PHOTO_IMAGE_SMALL, getSmallPhotoUrl(post, i));
            values.put(Tables.Posts.COL_PHOTO_IMAGE_LARGE, getLargePhotoUrl(post, i));

            databaseWriter.saveDataToPostsTable(values);
        }

    }

    private String getSmallPhotoUrl(PhotoPost post, int i) {
        Photo photo = post.getPhotos().get(i);
        PhotoSize smallPhoto = photo.getSizes().get(halfwayIndexOf(photo.getSizes().size()));
        return smallPhoto.getUrl();
    }

    private int halfwayIndexOf(int photoSizes) {
        return Math.abs(Math.round(photoSizes / 2) - 1);
    }

    private String getLargePhotoUrl(PhotoPost post, int i) {
        Photo photo = post.getPhotos().get(i);
        PhotoSize bigPhoto = photo.getSizes().get(0);
        return bigPhoto.getUrl();
    }
}
