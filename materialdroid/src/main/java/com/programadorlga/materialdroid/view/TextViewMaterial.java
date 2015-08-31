package com.programadorlga.materialdroid.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.programadorlga.materialdroid.R;

public class TextViewMaterial extends AppCompatTextView {
    public TextViewMaterial(Context context, String text) {
        this(context);
        this.setText(text);
    }

    protected TextViewMaterial(Context context) {
        super(context, null, R.style.TextViewMaterial);
    }
}
