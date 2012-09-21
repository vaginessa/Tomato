package com.shivgadhia.android.tomato;

//Model to hold each image url and title
public class ImageModel {
	private String mUrl;
	private String mTitle;
	public ImageModel(String url, String title) {
		super();
		this.mUrl = url;
		this.mTitle = title;
	}
	public String getUrl() {
		return mUrl;
	}
	public String getTitle() {
		return mTitle;
	}
}
