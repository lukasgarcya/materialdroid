package com.programadorlga.materialdroid.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.programadorlga.materialdroid.annotation.form.DateTimeFormat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateMaterial extends TextViewMaterial implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Field field;
    private Calendar calendar;
    private String format;

    public DateMaterial(Context context, Field field) {
        this(context, field, new Date());
    }

    public DateMaterial(Context context, Field field, Date date) {
        super(context);
        this.field = field;
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.setDate();
        this.setOnClickListener(this);
    }

    private void setDate() {
        format = "MMMM dd,yyyy";
        if (this.field.isAnnotationPresent(DateTimeFormat.class) && !(this.field.getAnnotation(DateTimeFormat.class)).date().isEmpty()) {
            format = (this.field.getAnnotation(DateTimeFormat.class)).date();
        }
        this.setText(new SimpleDateFormat(format).format(this.calendar.getTime()));
    }

    public String getFormat() {
        return format;
    }

    @Override
    public void onClick(View v) {
        new DatePickerDialog(this.getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        this.setDate();
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(format).format(calendar.getTime());
    }
}
