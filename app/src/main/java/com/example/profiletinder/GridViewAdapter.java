package com.example.profiletinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public GridViewAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item2, parent, false);
        }

        EditText editText = convertView.findViewById(R.id.editText1);
        editText.setText(mData.get(position));

        return convertView;
    }
}
