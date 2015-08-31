package com.programadorlga.materialdroid.controller.recyclerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.programadorlga.materialdroid.R;
import com.programadorlga.materialdroid.annotation.model.EntityMaterial;
import com.programadorlga.materialdroid.annotation.recyclerview.AddButtonRecyclerView;
import com.programadorlga.materialdroid.view.AppCompatActivityToolBar;

public class RecyclerViewMaterialActivity extends AppCompatActivityToolBar implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Class entity;
    private Class addForm;
    private RecyclerView.Adapter adapter;
    FloatingActionButton btAddForm;
    public static final int NEW = 1;
    public static final int UPDATE = 2;

    public RecyclerViewMaterialActivity() {
        super(R.layout.listactivity);
        if (getClass().isAnnotationPresent(EntityMaterial.class)) {
            this.entity = this.getClass().getAnnotation(EntityMaterial.class).value();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterMaterial(this, entity);
        this.setmRecyclerView();
        adapter = new AdapterMaterial(this, entity);
        this.mRecyclerView.setAdapter(adapter);
        btAddForm = (FloatingActionButton) findViewById(R.id.bt_add_form);
        if (this.getClass().isAnnotationPresent(AddButtonRecyclerView.class)) {
            btAddForm.setOnClickListener(this);
            addForm = this.getClass().getAnnotation(AddButtonRecyclerView.class).value();
        } else {
            btAddForm.setVisibility(View.INVISIBLE);
        }
    }

    private void setmRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btAddForm)) {
            startActivityForResult(new Intent(this, addForm), NEW);
        }
    }
}
