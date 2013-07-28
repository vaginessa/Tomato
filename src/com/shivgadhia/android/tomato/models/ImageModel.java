package com.shivgadhia.android.tomato.models;

import android.os.Parcel;
import android.os.Parcelable;

//Model to hold each image url and title
public class ImageModel implements Parcelable {
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

    private ImageModel(Parcel in) {
        small_image_url = in.readString();
        big_image_url = in.readString();
        post_url = in.readString();
        mTitle = in.readString();
        post_id = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(small_image_url);
        dest.writeString(big_image_url);
        dest.writeString(post_url);
        dest.writeString(mTitle);
        dest.writeString(post_id);
    }

    public static final Parcelable.Creator<ImageModel> CREATOR = new Parcelable.Creator<ImageModel>() {
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
