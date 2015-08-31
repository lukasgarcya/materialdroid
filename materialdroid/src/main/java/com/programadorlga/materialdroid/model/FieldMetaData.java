package com.programadorlga.materialdroid.model;

import com.programadorlga.materialdroid.annotation.model.VerboseName;

import java.lang.reflect.Field;

public class FieldMetaData {
    private Field field;
    private int verboseNameSingular;
    private int verboseNamePlural;

    public FieldMetaData(Field field) {
        this.field = field;
    }

    public int getVerboseNameSingular() {
        if (verboseNameSingular == 0) {
            if (field.isAnnotationPresent(VerboseName.class)) {
                verboseNameSingular = field.getAnnotation(VerboseName.class).singular();
//            } else {
//                verboseNameSingular = field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1));
            } else {
                verboseNameSingular = -1;
            }
        }
        return verboseNameSingular;
    }

    public int getVerboseNamePlural() {
        if (verboseNamePlural == 0) {
            if (field.isAnnotationPresent(VerboseName.class)) {
                verboseNamePlural = field.getAnnotation(VerboseName.class).plural();
            } else {
                verboseNamePlural = -1;//field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1));
            }
        }
        return verboseNamePlural;
    }
}
