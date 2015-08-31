package com.programadorlga.materialdroid.annotation.form;

public @interface BooleanSwitch {
    String on() default "ON";

    String off() default "OFF";
}
