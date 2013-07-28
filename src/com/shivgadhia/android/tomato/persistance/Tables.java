package com.shivgadhia.android.tomato.persistance;

public final class Tables {

    public static final String TBL_POSTS = "POSTS";
    public static final String TBL_TAGS_FOR_POSTS = "TAGS_FOR_POSTS";
    public static final String VIEW_BLOGS = "BLOGS";

    public static class Posts {
        public static final String COL_BLOG_NAME = "blog_name";
        public static final String COL_ID = "id";
        public static final String COL_POST_URL = "post_url";
        public static final String COL_TYPE = "type";
        public static final String COL_POST_TIME = "post_time";
        public static final String COL_POST_DATE = "post_date";
        public static final String COL_TAGS = "tags";
        public static final String COL_SOURCE_URL = "source_url";
        public static final String COL_PHOTO_CAPTION = "post_photo_caption";
        public static final String COL_PHOTO_IMAGE_SMALL = "post_photo_image_small";
        public static final String COL_PHOTO_IMAGE_LARGE = "post_photo_image_large";
        public static final String _ID = "_id";
    }

    public static class Blogs extends Posts {
    }

    public static class TagsForPosts {
        public static final String COL_ID = "id";
        public static final String COL_POST_ID = "post_id";
        public static final String COL_TAG = "tag";
    }
}