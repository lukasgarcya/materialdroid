package com.programadorlga.materialdroid.view;

import android.view.Menu;

import com.programadorlga.materialdroid.R;

public class AppCompatFormActivityToolBar extends AppCompatActivityToolBar {
    private int id;

    public AppCompatFormActivityToolBar(int layout) {
        super(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_form, menu);
        if (id <= 0) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }
}
