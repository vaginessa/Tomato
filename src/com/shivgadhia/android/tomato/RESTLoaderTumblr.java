package com.shivgadhia.android.tomato;

import java.util.ArrayList;

import android.content.Context;

public class RESTLoaderTumblr extends RESTLoaderBase {
	private static String TAGGED_FEED_URL = "http://api.tumblr.com/v2/tagged?tag=";
	public RESTLoaderTumblr(Context context) {
		super(context);
		setFeedUrl(TAGGED_FEED_URL+"Tomato");
	}

	@Override
	protected ArrayList<ImageModel> parseJsonToList(String jsonData) {
		// TODO Auto-generated method stub
		return null;
	}

}
