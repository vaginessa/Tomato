package com.shivgadhia.android.tomato;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;



public final class ImageCache{
	private static File cachedir;//new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/PokerPages/");
	public static ImageCache instance;
	private ImageCache(){
		
	}
	
	synchronized public static ImageCache getInstance(Context c) {
        if (instance == null) {
            instance = new ImageCache();
        }
        cachedir   = c.getCacheDir();
        return instance;
    }

	private String getFilename(String url){
		Log.v("Debug", "ImageCache-- "+url);
		String result;
		int index = url.lastIndexOf('/');
		if(index >0){
		result = url.substring(index);
		return result;
		}else{
			return null;
		}
	}
	
	
	public Bitmap getImage(String playerImgUrl, int width, int height){
		Bitmap img = null;
		Bitmap ret = null;
		
		String fn = getFilename(playerImgUrl);
		if(fn != null){
			File f = new File(cachedir.getAbsolutePath()+fn);
		
			if(f.exists()){
				Log.v("Debug", fn + " : File Already Exists...getting image from file");
				
				img = BitmapHelper.decodeFile(f, height, width);//createBitmapFromFile(f);
			}else{
				Log.v("Debug", fn + " : File Does Not Exist...downloading + saving image");
				Bitmap img_temp = download(playerImgUrl);
				File f_temp;
				if(cachedir.exists()){
					f_temp = createFileFromBitmap(img_temp,fn);
					img = BitmapHelper.decodeFile(f_temp, height, width);
				}
			}
		}
		return img;
	}
	
	
	/*
	public Bitmap getFlickrImage(){
		String photoId = "4979957268";
		String mSecret = "0dff1f6b73";
		return getImage("http://farm5.static.flickr.com/4103/"+photoId+"_"+mSecret+".jpg", -1, -1);	
	}
	*/
	
	/* - done by BitmapHelper now
	private Bitmap createBitmapFromFile(File f) {
		Bitmap image = null;
		try{
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			image = BitmapFactory.decodeStream(bis);
            bis.close();
            fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return image;
	}
	*/

	private Bitmap download(String urlstring){
		
		//Log.v("Debug", urlstring);
		Bitmap image = null;
		try {
            URL url = new URL(urlstring);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            
            image = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
		}catch(IOException e){
		
		}
		return image;
	}
	
	public File createFileFromBitmap(Bitmap newbm, String filename) {
		
		if(!cachedir.exists() && !cachedir.isDirectory()){

		try {
			cachedir.mkdirs();
			cachedir.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		File f = new File(cachedir.getAbsolutePath()+filename); 
	    try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!f.exists()){
			Log.v("Debug", "file not created");
		}
	    FileOutputStream fos = null; 
	      try { 
	           fos = new FileOutputStream(f);
	           newbm.compress(Bitmap.CompressFormat.JPEG, 100, fos);  
	           fos.close();
	      } catch (FileNotFoundException e) { 
	           Log.e("filenotfound", "error4:"+e.toString()); 
	      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return f;
		
	}

}
