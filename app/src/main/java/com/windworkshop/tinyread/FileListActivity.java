package com.windworkshop.tinyread;

import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.WearableActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

public class FileListActivity extends WearableActivity {

    WearableRecyclerView listview;
    FileListAdapter adapter;
    File nowFile = null;

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

        nowFile = Environment.getExternalStorageDirectory();
        //File[] filelist = nowFile.listFiles();

        //Log.e("tinyread", "filelist size:" + filelist.length);
        //ArrayList<File> f = new ArrayList<File>(Arrays.asList(filelist));

        adapter.setFilesList(nowFile);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(adapter.isPath(Environment.getExternalStorageDirectory().getPath())) {
                return false;
            } else {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileListAdapterViewHolder> {
        ArrayList<File> filesList = new ArrayList<File>();
        ArrayList<ShowFileUtil> showFileUtil = new ArrayList<ShowFileUtil>();
        File parentFile = null;
        public void setFilesList(File nowFile) {
            //this.parentFile = filesList.get(0).getParentFile();
            //this.filesList = filesList;
            enterFilePath(nowFile);
        }
        public boolean isPath(String path) {
            return parentFile.getPath().equals(path);
        }
        @NonNull
        @Override
        public FileListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FileListAdapterViewHolder(View.inflate(parent.getContext(), R.layout.file_lsit_activity_list_adapter, null));
        }

        @Override
        public void onBindViewHolder(@NonNull FileListAdapterViewHolder holder, final int position) {
            holder.itemView.setTag(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // System.out.println("click:" + position);
                    File nextFile = showFileUtil.get(position).filePath;
                    enterFilePath(nextFile);
                }
            });
            holder.filename.setText(showFileUtil.get(position).fileName);
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
        private void enterFilePath(File path) {
            File[] nextFiles = path.listFiles();
            parentFile = path.getParentFile();
            filesList.clear();
            showFileUtil.clear();
            if(!path.getPath().equals(Environment.getExternalStorageDirectory().getPath())) {
                filesList.add(parentFile);
                showFileUtil.add(new ShowFileUtil("..", parentFile));
            }
            ArrayList<File> f = new ArrayList<File>(Arrays.asList(nextFiles));
            filesList.addAll(f);
            for(File file : f) {
                showFileUtil.add(new ShowFileUtil(file));
            }
            notifyDataSetChanged();
        }
    }

    class ShowFileUtil {
        String fileName;
        File filePath;

        public ShowFileUtil(File filePath) {
            this.fileName = filePath.getName();
            this.filePath = filePath;
        }

        public ShowFileUtil(String fileName, File filePath) {
            this.fileName = fileName;
            this.filePath = filePath;
        }
    }
}
