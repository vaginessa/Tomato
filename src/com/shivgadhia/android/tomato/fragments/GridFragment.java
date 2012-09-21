package com.shivgadhia.android.tomato.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.RESTLoaderFlickr;
import com.shivgadhia.android.tomato.views.ThumbnailView;

public class GridFragment extends Fragment implements LoaderCallbacks<ArrayList<ImageModel>>{
	private static final String SAVED_SEARCH_CRITERIA = "saved_search_criteria";
	private ArrayList<ImageModel> mList = new ArrayList<ImageModel>();
	private GridView mGridView;
	private ImageGridAdapter mAdapter;
	private Context mContext;
	private String mSearchCriteria;
	
	public void searchWithCriteria(String criteria){
		mSearchCriteria = criteria;
		Bundle args = new Bundle();
		args.putString("tags", criteria.replace(" ", "+"));
		
		Bundle searchterms = new Bundle();
		searchterms.putParcelable("searchterms", args);
		Log.v("Search", criteria);
		getLoaderManager().restartLoader(0, searchterms, this);
		
	}
	
	public void init(){
		getLoaderManager().restartLoader(0, null, this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_SEARCH_CRITERIA, mSearchCriteria);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String criteria = null;
		if (savedInstanceState != null) {
			criteria = savedInstanceState.getString(SAVED_SEARCH_CRITERIA);
			
		}
		
		if(criteria != null){
			searchWithCriteria(criteria);
		}else{
		
			// Initialize the Loader.
			LoaderManager lm = getLoaderManager();
			if (lm.getLoader(0) != null) {
	            lm.initLoader(0, null, this);
	        }
        
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mContext = getActivity().getApplicationContext();
		
		View v = inflater.inflate(R.layout.grid_fragment_layout, container, false);
		mGridView = (GridView) v.findViewById(R.id.gridview1);
		mAdapter = new ImageGridAdapter(mContext);
        mGridView.setAdapter(mAdapter);
        
        
        
        return v;
	}

	@Override
	public Loader<ArrayList<ImageModel>> onCreateLoader(int id, Bundle args) {
		if(args != null && args.containsKey("searchterms")){
			Bundle q = args.getParcelable("searchterms");
			
			Log.v("Search", "q: "+q.getString("tags"));
			return new RESTLoaderFlickr(mContext, q.getString("tags"));
		}else{
			return new RESTLoaderFlickr(mContext);
		}
		
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<ImageModel>> arg0,
			ArrayList<ImageModel> arg1) {

		//Toast.makeText(mContext, "OnLoadFinished", Toast.LENGTH_SHORT).show();	
		mList = arg1;
		mAdapter.notifyDataSetChanged();
		
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ImageModel>> arg0) {
		mList.clear();
		mAdapter.notifyDataSetChanged();
	}
	public class ImageGridAdapter extends BaseAdapter{
		
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
			if(mList != null){
				return mList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			if(mList != null){
				return mList.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return mList.get(arg0).hashCode();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			ViewHolder holder;
			
	        if(arg1==null){
	        	int lp_size = getResources().getDimensionPixelOffset(R.dimen.grid_size);
	        	arg1 = new ThumbnailView(mContext, mList.get(arg0).getUrl(), mList.get(arg0).getTitle());
	            holder = new ViewHolder();
	            holder.mTNView = (ThumbnailView) arg1;
	            
	            holder.mTNView.setLayoutParams(new GridView.LayoutParams(lp_size, lp_size));
	            arg1.setTag(holder);
	        }
	        else
	        {
	            holder = (ViewHolder)arg1.getTag();
	            
	        }
	        
	        holder.mTNView.setImage(mList.get(arg0).getUrl());
	        holder.mTNView.setTitle(mList.get(arg0).getTitle());
	        
	        return arg1;
			
		}

	}
}
