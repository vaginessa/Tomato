package com.shivgadhia.android.tomato.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.shivgadhia.android.tomato.R;
import com.shivgadhia.android.tomato.models.ContentsListItem;

import java.util.ArrayList;

public class ContentsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ContentsListItem> data;
    private LayoutInflater inflater;
    private ActionsListener actionsListener;

    public interface ActionsListener {
        void onRefreshClicked(ContentsListItem item);

        void onDeleteClicked(ContentsListItem item);
    }

    public ContentsAdapter(Context context, ArrayList<ContentsListItem> data, ActionsListener actionsListener) {
        this.context = context;
        this.actionsListener = actionsListener;
        inflater = LayoutInflater.from(context);
        if (data == null) {
            this.data = new ArrayList<ContentsListItem>();
        } else {
            this.data = data;
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ContentsListItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return createViewFromResource(position, convertView, parent, R.layout.contents_list_item);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent,
                                        int resource) {
        View view;
        TextView text1;
        TextView text2;
        ImageButton refreshButton;
        ImageButton deleteButton;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        ContentsListItem item = getItem(position);

        text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(item.getTitle());

        text2 = (TextView) view.findViewById(R.id.text2);
        text2.setText(item.asUrl());

        refreshButton = (ImageButton) view.findViewById(R.id.refresh);
        refreshButton.setOnClickListener(createRefreshClickedListener(item, text2));
        deleteButton = (ImageButton) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(createDeleteClickedListener(item));

        return view;
    }

    private View.OnClickListener createDeleteClickedListener(final ContentsListItem item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsListener.onDeleteClicked(item);
            }
        };
    }

    private View.OnClickListener createRefreshClickedListener(final ContentsListItem item, final TextView text2) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsListener.onRefreshClicked(item);
                text2.setText("Updating...");
            }
        };
    }
}
