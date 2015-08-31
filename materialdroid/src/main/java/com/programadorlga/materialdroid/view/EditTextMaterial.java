package com.programadorlga.materialdroid.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;

import com.programadorlga.materialdroid.R;

public class EditTextMaterial extends AppCompatEditText {


    public EditTextMaterial(Context context) {
        this(context, InputType.TYPE_CLASS_TEXT);
    }

    public EditTextMaterial(Context context, int inputType) {
        super(context);
        this.setTextAppearance(context, R.style.EditTextMaterial);
        this.setInputType(inputType);
    }
}
