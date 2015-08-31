package com.programadorlga.materialdroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.programadorlga.materialdroid.R;


public class AppCompatActivityToolBar extends AppCompatActivity {
    private Toolbar mToolbar;
    private int layout;

    public AppCompatActivityToolBar(int layout) {
        this.layout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setToolBar();
    }

    protected Toolbar getmToolbar() {
        return mToolbar;
    }

    private void setToolBar() {
        setSupportActionBar(mToolbar);
    }
}
