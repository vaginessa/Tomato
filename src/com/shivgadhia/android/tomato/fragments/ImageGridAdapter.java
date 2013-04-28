package com.shivgadhia.android.tomato.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.shivgadhia.android.tomato.ImageModel;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.TomatoApplication;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private final ImageTagFactory imageTagFactory;
    private List<ImageModel> data;

    public void updateData(ArrayList<ImageModel> mList) {
        data = mList;
    }

    class ViewHolder {
            TextView thumbnailTitle;
            ImageView thumbnailImage;
        }

        private Context mContext;

        public ImageGridAdapter(Context context, List<ImageModel> data) {
            this.data = data;
            mContext = context;
            inflater = LayoutInflater.from(context);
            imageTagFactory = new ImageTagFactory(context, R.drawable.ic_launcher);
            imageTagFactory.setErrorImageId(R.drawable.ic_launcher);
        }

        @Override
        public int getCount() {
            if (data != null) {
                return data.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int arg0) {
            if (data!= null) {
                return data.get(arg0);
            }
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return data.get(arg0).hashCode();
        }

        @Override
        public View getView(int arg0, View view, ViewGroup arg2) {

            ViewHolder holder;

            if (view == null) {
                view = inflater.inflate(R.layout.thumbnail_view, null);
                holder = new ViewHolder();
                holder.thumbnailTitle = (TextView) view.findViewById(R.id.tvImageTitle);
                holder.thumbnailImage = (ImageView) view.findViewById(R.id.ivImage);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();

            }
            String imageUrl = data.get(arg0).getSmallUrl();
            ImageTag imgTag = imageTagFactory.build(imageUrl);
            holder.thumbnailImage.setTag(imgTag);
            holder.thumbnailTitle.setText(data.get(arg0).getTitle());
            TomatoApplication.getImageManager().getLoader().load(holder.thumbnailImage);
            return view;

        }


}