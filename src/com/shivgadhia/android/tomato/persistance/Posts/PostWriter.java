package com.shivgadhia.android.tomato.persistance.Posts;

import android.content.ContentValues;
import com.shivgadhia.android.tomato.persistance.DatabaseWriter;
import com.shivgadhia.android.tomato.persistance.Tables;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;

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

        values.put(Tables.Posts.COL_PHOTO_CAPTION, post.getCaption());
        values.put(Tables.Posts.COL_PHOTO_IMAGE_SMALL, getSmallPhotoUrl(post));
        values.put(Tables.Posts.COL_PHOTO_IMAGE_LARGE, getLargePhotoUrl(post));

        databaseWriter.saveDataToPostsTable(values);

    }

    private String getSmallPhotoUrl(PhotoPost post) {
        Photo photo = post.getPhotos().get(0);
        PhotoSize smallPhoto = photo.getSizes().get(0);
        return smallPhoto.getUrl();
    }

    private String getLargePhotoUrl(PhotoPost post) {
        Photo photo = post.getPhotos().get(0);
        PhotoSize bigPhoto = photo.getSizes().get(photo.getSizes().size()-1);
        return bigPhoto.getUrl();
    }
}
