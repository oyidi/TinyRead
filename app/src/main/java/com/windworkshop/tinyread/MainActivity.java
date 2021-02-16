package com.windworkshop.tinyread;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("init");
        mTextView = (TextView) findViewById(R.id.text);
        Log.e("tinyread", "init");
        // Enables Always-on
        setAmbientEnabled();
        // test
        Intent intent = new Intent(MainActivity.this, FileListActivity.class);
        getApplicationContext().startActivity(intent);

    }
}