package com.programadorlga.materialdroid.annotation.form;

/**
 * Created by programadorlga on 8/15/15.
 */
public @interface InputType {
    int value() default android.text.InputType.TYPE_CLASS_TEXT;
}
