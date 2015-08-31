package com.programadorlga.materialdroid.view;

import android.content.Context;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeMaterial extends LinearLayout {

    private DateMaterial dateMaterial;
    private TimeMaterial timeMaterial;

    public DateTimeMaterial(Context context, Field field, Date dateTime) {
        super(context);
        this.setOrientation(VERTICAL);
        this.dateMaterial = new DateMaterial(context, field, dateTime);
        this.addView(this.dateMaterial);
        this.timeMaterial = new TimeMaterial(context, field, dateTime);
        this.addView(this.timeMaterial);
    }

    public Date getDate() throws ParseException {
        return new SimpleDateFormat(dateMaterial.getFormat().concat(" ").concat(timeMaterial.getFormat()))
                .parse(dateMaterial.toString().concat(" ").concat(timeMaterial.toString()));

//        new SimpleDateFormat(field)
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(this.dateMaterial.getDate());
//        Date time = timeMaterial.getDate();
//
//        return calendar.getTime();
    }

//    public TextViewMaterial getDate(){
//        return this.dateMaterial;
//    }
//
//    public TextViewMaterial getTime(){
//        return this.timeMaterial;
//    }

}
