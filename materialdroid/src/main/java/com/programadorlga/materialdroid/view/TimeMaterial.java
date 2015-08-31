package com.programadorlga.materialdroid.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TimePicker;

import com.programadorlga.materialdroid.annotation.form.DateTimeFormat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeMaterial extends TextViewMaterial implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private Field field;
    private Calendar calendar;
    private String format = null;

    public TimeMaterial(Context context, Field field) {
        this(context, field, new Date());
    }

    public TimeMaterial(Context context, Field field, Date date) {
        super(context);
        this.field = field;
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.setTime();
        this.setOnClickListener(this);
    }

    private void setTime() {
        format = "hh:mm";
        if (format != null && this.field.isAnnotationPresent(DateTimeFormat.class) && !(this.field.getAnnotation(DateTimeFormat.class)).time().isEmpty()) {
            format = (this.field.getAnnotation(DateTimeFormat.class)).time();
        }
        this.setText(new SimpleDateFormat(format).format(this.calendar.getTime()));
    }

    public String getFormat() {
        return format;
    }

    @Override
    public void onClick(View v) {
        new TimePickerDialog(this.getContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.calendar.set(Calendar.MINUTE, minute);
        this.setTime();
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(format).format(calendar.getTime());
    }
}
