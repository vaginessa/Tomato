package com.shivgadhia.android.tomato.models;

//Model to hold each image url and title
public class ImageModel {
    private String small_image_url;
    private String big_image_url;
    private String post_url;
    private String mTitle;
    private String post_id;

    public ImageModel(String url_small, String url_big, String post_url, String title, String post_id) {
        super();
        this.small_image_url = url_small;
        this.big_image_url = url_big;
        this.post_url = post_url;
        this.mTitle = title;
        this.post_id = post_id;
    }

    public String getSmallUrl() {
        return small_image_url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBigUrl() {
        return big_image_url;
    }

    public String getPostId() {
        return post_id;
    }

    public String getPostUrl() {
        return post_url;
    }
}
