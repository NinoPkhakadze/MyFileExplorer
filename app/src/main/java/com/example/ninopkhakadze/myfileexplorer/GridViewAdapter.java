package com.example.ninopkhakadze.myfileexplorer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by NinoPkhakadze on 11/23/2016.
 */

public class GridViewAdapter extends AbstractAdapter {

    public GridViewAdapter(Context context, CurrentWorkingDirectory cwd) {
        super(context, cwd);
    }

    private class ViewHolder {
        private ImageView icon;
        private TextView name;

        public ViewHolder(ImageView icon, TextView name) {
            this.icon = icon;
            this.name = name;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object[] viewAndHolder = getViewAndHolder(convertView);

        View rootView = (View) viewAndHolder[0];
        ViewHolder viewHolder = (ViewHolder) viewAndHolder[1];

        fillHolder(viewHolder, position);

        return rootView;
    }

    private void fillHolder(ViewHolder viewHolder, int position) {
        File file = (File) getItem(position);

        viewHolder.icon.setImageResource(Util.getIcon(file));
        viewHolder.name.setText(file.getName());
    }

    private Object[] getViewAndHolder(View convertView) {
        View rootView;
        ViewHolder viewHolder;

        if (convertView != null) {
            rootView = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            rootView = View.inflate(context, R.layout.grid_view_item, null);
            viewHolder = new ViewHolder(
                    (ImageView) rootView.findViewById(R.id.grid_view_item_icon),
                    (TextView) rootView.findViewById(R.id.grid_view_item_name)
            );
            rootView.setTag(viewHolder);
        }

        return new Object[]{rootView, viewHolder};
    }
}
