package com.programadorlga.materialdroid.controller.recyclerview.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.programadorlga.materialdroid.R;

public class ViewHolderTitleTwoSubTitleMaterial extends ViewHolderTitleSubTitleMaterial {
    private TextView tvSubTitle2;

    public ViewHolderTitleTwoSubTitleMaterial(View view, Context context) {
        super(view, context);
        tvSubTitle2 = (TextView) view.findViewById(R.id.tv_sub_title2_material);
    }

    public void setSubTitle2(String subTitle2) {
        tvSubTitle2.setText(subTitle2);
    }
}
