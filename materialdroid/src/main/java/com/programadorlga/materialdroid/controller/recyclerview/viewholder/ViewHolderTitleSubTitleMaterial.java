package com.programadorlga.materialdroid.controller.recyclerview.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.programadorlga.materialdroid.R;

public class ViewHolderTitleSubTitleMaterial extends ViewHolderOnlyTitleTextMaterial {
    private TextView tvSubTitle;

    public ViewHolderTitleSubTitleMaterial(View view, Context context) {
        super(view, context);
        tvSubTitle = (TextView) view.findViewById(R.id.tv_sub_title_material);
    }

    public void setSubTitle(String subTitle) {
        tvSubTitle.setText(subTitle);
    }
}
