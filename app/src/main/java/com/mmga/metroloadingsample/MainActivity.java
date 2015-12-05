package com.mmga.metroloadingsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mmga.metroloading.MetroLoadingView;

public class MainActivity extends AppCompatActivity {

    private MetroLoadingView mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLoading = (MetroLoadingView) findViewById(R.id.loading);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoading.isAnimating()) {
                    mLoading.stop();
                } else {
                    mLoading.start();
                }
            }
        });
    }


}
