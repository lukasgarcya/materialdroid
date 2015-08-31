package com.programadorlga.materialdroid.controller.form;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.programadorlga.materialdroid.R;
import com.programadorlga.materialdroid.annotation.form.OrderFieldForm;
import com.programadorlga.materialdroid.annotation.model.EntityMaterial;
import com.programadorlga.materialdroid.view.AppCompatFormActivityToolBar;
import com.programadorlga.materialdroid.view.BooleanMaterial;
import com.programadorlga.materialdroid.view.DateTimeMaterial;
import com.programadorlga.materialdroid.view.EditTextMaterial;
import com.programadorlga.materialdroid.view.FieldGroup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;

public class FormActivity extends AppCompatFormActivityToolBar {
    private LinearLayout layout;
    private Class entity;
    private RealmObject entityInstance;
    private Realm realm;

    public FormActivity() {
        super(R.layout.form);
        if (getClass().isAnnotationPresent(EntityMaterial.class)) {
            this.entity = this.getClass().getAnnotation(EntityMaterial.class).value();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layout = (LinearLayout) this.findViewById(R.id.form_ormlite);
        this.addField();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    private void addField() {
        try {
            for (String field : ((OrderFieldForm) this.getClass().getAnnotation(OrderFieldForm.class)).value()) {
                Field fieldEntity = entity.getDeclaredField(field);
                if (fieldEntity.getType().isAssignableFrom(String.class)) {
                    layout.addView(new FieldGroup(this, fieldEntity, new EditTextMaterial(this)));
                } else if (fieldEntity.getType().isAssignableFrom(Integer.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Short.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Long.TYPE)) {
                    layout.addView(new FieldGroup(this, fieldEntity, new EditTextMaterial(this, InputType.TYPE_CLASS_NUMBER)));
                } else if (fieldEntity.getType().isAssignableFrom(Float.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Double.TYPE)) {
                    layout.addView(new FieldGroup(this, fieldEntity, new EditTextMaterial(this, InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED)));
                } else if (fieldEntity.getType().isAssignableFrom(Date.class)) {
                    layout.addView(new FieldGroup(this, fieldEntity, new DateTimeMaterial(this, fieldEntity, new Date())));
                } else if (fieldEntity.getType().isAssignableFrom(Boolean.TYPE)) {
                    layout.addView(new FieldGroup(this, fieldEntity, new BooleanMaterial(this, fieldEntity, true, false), LinearLayoutCompat.HORIZONTAL));
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (entityInstance == null) {
                this.save();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        try {
            realm = Realm.getInstance(this);
            realm.beginTransaction();
            entityInstance = realm.createObject(entity);
            for (int i = 0; i < ((OrderFieldForm) this.getClass().getAnnotation(OrderFieldForm.class)).value().length; i++) {
                Field fieldEntity = entity.getDeclaredField(((OrderFieldForm) this.getClass().getAnnotation(OrderFieldForm.class)).value()[i]);
                Method set = entityInstance.getClass().getMethod("set" + fieldEntity.getName().substring(0, 1).toUpperCase() + fieldEntity.getName().substring(1),
                        fieldEntity.getType());
                if (fieldEntity.getType().isAssignableFrom(String.class)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    set.invoke(entityInstance, editText.getText().toString());
                } else if (fieldEntity.getType().isAssignableFrom(Integer.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    int integer = editText.getText().toString().length() > 0 ? Integer.parseInt(editText.getText().toString()) : 0;
                    set.invoke(entityInstance, integer);
                } else if (fieldEntity.getType().isAssignableFrom(Short.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    short shortValue = editText.getText().toString().length() > 0 ? Short.parseShort(editText.getText().toString()) : 0;
                    set.invoke(entityInstance, shortValue);
                } else if (fieldEntity.getType().isAssignableFrom(Long.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    long longValue = editText.getText().toString().length() > 0 ? Long.parseLong(editText.getText().toString()) : 0;
                    set.invoke(entityInstance, longValue);
                } else if (fieldEntity.getType().isAssignableFrom(Float.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    float floatValue = editText.getText().toString().length() > 0 ? Float.parseFloat(editText.getText().toString()) : 0;
                    set.invoke(entityInstance, floatValue);
                } else if (fieldEntity.getType().isAssignableFrom(Double.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    EditText editText = (EditText) fieldGroup.getView();
                    double doubleValue = editText.getText().toString().length() > 0 ? Double.parseDouble(editText.getText().toString()) : 0;
                    set.invoke(entityInstance, doubleValue);
                } else if (fieldEntity.getType().isAssignableFrom(Date.class)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    DateTimeMaterial dateTimeMaterial = (DateTimeMaterial) fieldGroup.getView();
                    set.invoke(entityInstance, dateTimeMaterial.getDate());
                }
            }
            Method set = entityInstance.getClass().getMethod("setId", Long.TYPE);
            set.invoke(entityInstance, realm.where(entity).maximumInt("id") + 1);
            realm.commitTransaction();
            setResult(RESULT_OK);
            finish();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}