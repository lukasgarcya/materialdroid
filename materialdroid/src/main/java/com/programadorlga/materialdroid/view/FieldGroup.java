package com.programadorlga.materialdroid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.programadorlga.materialdroid.model.FieldMetaData;

import java.lang.reflect.Field;


public class FieldGroup extends LinearLayoutCompat {
    private Field field;
    private View view;
    private FieldMetaData meta;
    private TextViewMaterial label;

    public FieldGroup(Context context, Field field, View view) {
        this(context, field, view, LinearLayoutCompat.VERTICAL);
    }

    public FieldGroup(Context context, Field field, View view, int orientation) {
        super(context);
        this.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        this.setOrientation(orientation);
        this.field = field;
        this.meta = new FieldMetaData(field);
        this.view = view;
        this.setLabel();
        this.addView(label);
        this.addView(this.view);
    }

    private void setLabel() {
        int verboseNameSingular = this.meta.getVerboseNameSingular();
        if (verboseNameSingular > 0) {
            this.label = new TextViewMaterial(this.getContext(), getContext().getString(verboseNameSingular));
        } else {
            this.label = new TextViewMaterial(this.getContext(), field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1)));
        }
    }

    public View getView() {
        return view;
    }
}
