package com.example.ninopkhakadze.myfileexplorer;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView dirPathTextView;

    private AbsListView[] views;


    private enum ViewState {
    GRID,
    LIST
}

    private ViewState viewState;

    private CurrentWorkingDirectory currentWorkingDirectory;

    private ActionMode actionMode;

    private Set<View> selectedItems;

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ic_delete:
                    deleteSelected();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            unselectItems();
        }
    };

    private void unselectItems() {
        for (View v : selectedItems) {
            v.setBackgroundColor(Color.TRANSPARENT);
        }

        selectedItems.clear();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemClicked(view, position);
        }
    };

    private void itemClicked(View view, int position) {
        if (actionMode == null) {
            changeDirectory(position);
        } else {
            selectItem(view);
        }
    }

    private void changeDirectory(int position) {
        currentWorkingDirectory.changeDirectory(position);
        dirPathTextView.setText(currentWorkingDirectory.getPath());
        ((AbstractAdapter) views[0].getAdapter()).notifyDataSetChanged();
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            itemLongClicked(view);
            return true;
        }
    };

      private void itemLongClicked(View view) {
        selectItem(view);

        if (actionMode == null) {
            actionMode = startActionMode(actionModeCallBack);
        }
    }

    private void selectItem(View view) {
        view.setBackgroundColor(Color.GRAY);
        selectedItems.add(view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dirPathTextView = (TextView) findViewById(R.id.dir_path);
        currentWorkingDirectory = new CurrentWorkingDirectory("/");
        dirPathTextView.setText(currentWorkingDirectory.getPath());
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        if (gridView != null) {
            gridView.setAdapter(new GridViewAdapter(this, currentWorkingDirectory));
        }

        ListView listView = (ListView) findViewById(R.id.list_view);
        if (listView != null) {
            listView.setAdapter(new ListViewAdapter(this, currentWorkingDirectory));
        }

        views = new AbsListView[]{gridView, listView};
        viewState = ViewState.GRID;

        for (AbsListView v : views) {
            v.setOnItemClickListener(onItemClickListener);
//            v.setOnItemLongClickListener(onItemLongClickListener);
        }

        selectedItems = new HashSet<>();
    }

    private void deleteSelected() {
        String msg = selectedItems.size() + " items deleted.";
        final View view = findViewById(android.R.id.content);
        if (view != null) {
           Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
        }
        unselectItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_grid:
                showGridView();
                return true;
            case R.id.ic_list:
                showListView();
                return true;
            default:
                return false;
        }
    }

    private void showListView() {
        if (viewState != ViewState.LIST) {
            swapViews();
            viewState = ViewState.LIST;
        }
    }

    private void showGridView() {
        if (viewState != ViewState.GRID) {
            swapViews();
            viewState = ViewState.GRID;
        }
    }

    private void swapViews() {
        AbsListView tmp = views[0];
        views[0] = views[1];
        views[1] = tmp;

        views[0].setVisibility(View.VISIBLE);
        views[1].setVisibility(View.GONE);

        ((AbstractAdapter) views[0].getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (actionMode != null) {
            super.onBackPressed();
        } else {
            currentWorkingDirectory.changeDirectory(-1);
            dirPathTextView.setText(currentWorkingDirectory.getPath());
            ((AbstractAdapter) views[0].getAdapter()).notifyDataSetChanged();
        }
    }
}
