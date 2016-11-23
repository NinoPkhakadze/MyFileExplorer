package com.example.ninopkhakadze.myfileexplorer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by NinoPkhakadze on 11/23/2016.
 */

public abstract class AbstractAdapter extends BaseAdapter {

    protected CurrentWorkingDirectory cwd;
    protected Context context;

    public AbstractAdapter(Context context, CurrentWorkingDirectory cwd) {
        this.cwd = cwd;
        this.context = context;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cwd.getContentsCount();
    }

    @Override
    public Object getItem(int position) {
        return cwd.getContent(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
