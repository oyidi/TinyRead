package com.windworkshop.tinyread;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class FileListActivity extends WearableActivity {

    WearableRecyclerView listview;
    FileListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_list_activity);

        setAmbientEnabled();

        listview = findViewById(R.id.file_list_activity_file_list);
        listview.setLayoutManager(new WearableLinearLayoutManager(this));

        listview.setEdgeItemsCenteringEnabled(true);
        listview.setCircularScrollingGestureEnabled(true);
        listview.setBezelFraction(0.5f);
        //listview.setScrollDegreesPerScreen(90f);
        adapter = new FileListAdapter();
        listview.setAdapter(adapter);
    }

    class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListAdapterViewHolder> {
        ArrayList<File> filesList = null;

        @NonNull
        @Override
        public FileListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FileListAdapterViewHolder(View.inflate(parent.getContext(), R.layout.file_lsit_activity_list_adapter, null));
        }

        @Override
        public void onBindViewHolder(@NonNull FileListAdapterViewHolder holder, int position) {
            holder.filename.setText(filesList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return filesList.size();
        }

        class FileListAdapterViewHolder extends RecyclerView.ViewHolder {
            TextView filename;
            public FileListAdapterViewHolder(@NonNull View itemView) {
                super(itemView);
                filename = itemView.findViewById(R.id.file_list_activity_file_list_name);
            }
        }
    }

}
