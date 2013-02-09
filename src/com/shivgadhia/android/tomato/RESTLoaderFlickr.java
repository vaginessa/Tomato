package com.shivgadhia.android.tomato;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RESTLoaderFlickr extends RESTLoaderBase {
	private static String API_KEY = "";
	private static String SECRET = "";
	
	private static int LIMIT = 50;
	
	private static String INTERESTINGNESS_FEED_URL = "http://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key="+API_KEY+"&per_page="+LIMIT+"&format=json";
	private static String SEARCH_FEED_URL = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+API_KEY+"&per_page="+LIMIT+"&format=json&tags=";
	
	
	public RESTLoaderFlickr(Context context) {
		super(context);
		setFeedUrl(INTERESTINGNESS_FEED_URL);
	}
	
	public RESTLoaderFlickr(Context context, String searchTerms) {
		super(context);
		setFeedUrl(SEARCH_FEED_URL+searchTerms);
	}

	@Override
	public ArrayList<ImageModel> parseJsonToList(String jsonData) {
		jsonData= jsonData.substring(jsonData.indexOf('(')+1);
		Log.v("Debug", jsonData);
		JSONObject json;
		ArrayList<ImageModel> imgModels;
		try {
			json = new JSONObject(jsonData);
			JSONObject photos = json.getJSONObject("photos");
			JSONArray photo = photos.getJSONArray("photo");
			
			// Declaring variables to be reused within loop
			JSONObject photoAttributes;
			String mFarmID, mServerID, mPhotoID, mSecret, urlStr, title;
			if(photo.length() > 0){
				imgModels = new ArrayList<ImageModel>();
			
				for(int i=0; i<photo.length(); i++){
					photoAttributes = photo.getJSONObject(i);
					mFarmID = photoAttributes.getString("farm");
					mServerID = photoAttributes.getString("server");
					mPhotoID = photoAttributes.getString("id");
					mSecret = photoAttributes.getString("secret");
					
					// get url for image thumbnail. use _b for original size
					urlStr = "http://farm"+mFarmID+".static.flickr.com/"+mServerID+"/"+mPhotoID+"_"+mSecret+"_m.jpg";
					title = photoAttributes.getString("title");
					imgModels.add(new ImageModel(urlStr, title));
				}
				return imgModels;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	

}
