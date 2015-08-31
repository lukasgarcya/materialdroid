package com.programadorlga.materialdroid.view;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;

import com.programadorlga.materialdroid.annotation.form.BooleanSwitch;

import java.lang.reflect.Field;

public class BooleanMaterial extends SwitchCompat {
    private Field field;

    public BooleanMaterial(Context context, Field field, boolean checked, boolean showText) {
        super(context);
        this.field = field;
        this.setChecked(checked);
        this.setShowText(showText);
        if (showText) {
            this.setTextButton();
        }
    }

    public BooleanMaterial(Context context, Field field, boolean checked) {
        this(context, field, checked, false);
    }

    private void setTextButton() {
        String on = "ON";
        String off = "OFF";
        if (field.isAnnotationPresent(BooleanSwitch.class)) {
            BooleanSwitch booleanSwitch = field.getAnnotation(BooleanSwitch.class);
            on = booleanSwitch.on();
            off = booleanSwitch.off();
        }
        this.setTextOn(on);
        this.setTextOff(off);
    }
}
