package com.programadorlga.materialdroid.controller.recyclerview.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.programadorlga.materialdroid.R;
import com.programadorlga.materialdroid.annotation.recyclerview.AddButtonRecyclerView;
import com.programadorlga.materialdroid.controller.recyclerview.RecyclerViewMaterialActivity;

public class ViewHolderOnlyTitleTextMaterial extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvTitle;
    private View view;
    private long id;

    public ViewHolderOnlyTitleTextMaterial(View view, Context context) {
        super(view);
        this.view = view;
        tvTitle = (TextView) view.findViewById(R.id.tv_only_title_material);
        view.setOnClickListener(this);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.view.getContext(), this.view.getContext().getClass().getAnnotation(AddButtonRecyclerView.class).value());
        intent.putExtra("id", this.id);
        ((Activity) this.view.getContext()).startActivityForResult(intent, RecyclerViewMaterialActivity.UPDATE);

    }
}
