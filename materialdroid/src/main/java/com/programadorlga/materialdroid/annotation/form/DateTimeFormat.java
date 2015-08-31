package com.programadorlga.materialdroid.annotation.form;

public @interface DateTimeFormat {
    String time() default "";
    String date() default "";
}
