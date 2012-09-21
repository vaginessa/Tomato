package com.shivgadhia.android.tomato.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivgadhia.android.tomato.ImageCache;
import com.shivgadhia.android.tomato.R;

public class ThumbnailView extends LinearLayout{

	Context mContext;
	ImageCache mImgCache;
	View rootView;
	
	ImageView ivImage;
	TextView tvTitle;
	
public ThumbnailView(Context context, String url, String title) {
		super(context);
		mContext = context;
		mImgCache = ImageCache.getInstance(mContext);
		
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null){       
            rootView = inflater.inflate(R.layout.thumbnail_view, this);
        }
        ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
        tvTitle = (TextView) rootView.findViewById(R.id.tvImageTitle);
        
        if(title != null){
        	setTitle(title);
        }
        if(url != null){
        	setImage(url);
        }
        
	}


public void setTitle(String title){
	tvTitle.setText(title);
}
public void setImage(String url){
	new DownloadImageTask().execute(url);
}



private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
		
        @Override
		protected Bitmap doInBackground(String... urls) {
        	try{
        		return mImgCache.getImage(urls[0], 350, 350);
        	}catch(Exception e){
        		
        	}
        	return null;
        }

        @Override
		protected void onPreExecute (){
        	
        }
        
        @Override
		protected void onPostExecute(Bitmap result) {
        	
        	if(result != null)
        	{
        		ivImage.setImageBitmap(result);
        		ivImage.invalidate();
        	}
        }
    }



	public void clearImage() {
		ivImage.setImageBitmap(null);
		
	}

}
