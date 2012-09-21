package com.shivgadhia.android.tomato;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

abstract class RESTLoaderBase extends AsyncTaskLoader<ArrayList<ImageModel>>{
	private String mFeedUrl;
	
	ArrayList<ImageModel> mImageModels;
	RESTResponse mRestResponse;
	public RESTLoaderBase(Context context) {
		super(context);
		Log.v("Debug","RESTLoader constructor : "+mFeedUrl);
	}
	public void setFeedUrl(String url) {
		mFeedUrl = url;
		
	}
	
	// Holds the result of each Rest response
	public class RESTResponse {
		private String mData;
        private int    mCode;
        
        public RESTResponse() {
        }
        
        public RESTResponse(String data, int code) {
            mData = data;
            mCode = code;
        }
        
        public String getData() {
            return mData;
        }
        
        public int getCode() {
            return mCode;
        }
	}
	@Override
	public ArrayList<ImageModel> loadInBackground() {
		Log.v("Debug", "FEED: "+mFeedUrl);
		HttpGet request = new HttpGet(mFeedUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		
		try {
			response = httpClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			
			    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			    
					response.getEntity().writeTo(ostream);
					// Log.v("Debug", ostream.toString());
					 
					 String responseData = ostream.toString();
					 //responseData= responseData.substring(responseData.indexOf('(')+1);
					 //Log.v("Debug", responseData);
					 //JSONObject json = new JSONObject(responseData); 
					 //IF stat = ok
					 //JSONObject photos = json.getJSONObject("photos");
					 //JSONArray photo = photos.getJSONArray("photo");
					 //int rand = (int)(Math.random() * photo.length());
					/* JSONObject photoAttributes = photo.getJSONObject(rand);
					 mPhotoID = photoAttributes.getString("id");
					 mSecret = photoAttributes.getString("secret");
					 mServerID = photoAttributes.getString("server");
					 mFarmID = photoAttributes.getString("farm");
					 mTitle = photoAttributes.getString("title");
					 */
					 //Log.v("debug", photo.toString());
					 return parseJsonToList(responseData);
		} catch (ClientProtocolException e) {
			 Log.v("Debug", e.getMessage());
		} catch (IOException e) {
			 Log.v("Debug", e.getMessage());
		}

		return null;
	}
	
	abstract protected ArrayList<ImageModel> parseJsonToList(String jsonData);
	
	@Override
    public void deliverResult(ArrayList<ImageModel> data) {
        // Here we cache our response.
        mImageModels = data;
		Log.v("Debug", "deliver result");
        super.deliverResult(data);
    }
    
    @Override
    protected void onStartLoading() {
    	// use cached result - and call deliverResult(), or forceLoad()
    	if(mImageModels != null && mImageModels.size() > 0){
    		super.deliverResult(mImageModels);
    		return;
    	}
    	
    	forceLoad();
    }
    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
    @Override
    protected void onReset() {
        super.onReset();
        
        // Stop the Loader if it is currently running.
        onStopLoading();
        
        // Get rid of our cache if it exists.
        mImageModels = null;
    }
}

//http://neilgoodman.net/2011/12/26/modern-techniques-for-implementing-rest-clients-on-android-4-0-and-below-part-1/