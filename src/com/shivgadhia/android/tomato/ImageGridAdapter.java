package com.shivgadhia.android.tomato;

import com.shivgadhia.android.tomato.views.ThumbnailView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageGridAdapter extends BaseAdapter {
	private ViewHolder mViewHolder;
	class ViewHolder {
		ThumbnailView mTNView;
	}
	
	private Context mContext;
	
	public ImageGridAdapter(Context context) {
		super();
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
