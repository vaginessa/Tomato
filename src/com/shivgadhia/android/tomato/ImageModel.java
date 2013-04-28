package com.shivgadhia.android.tomato;

//Model to hold each image url and title
public class ImageModel {
	private String small_image_url;
	private String big_image_url;
	private String mTitle;
	public ImageModel(String url_small, String url_big, String title) {
		super();
		this.small_image_url = url_small;
		this.big_image_url = url_big;
		this.mTitle = title;
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
}
