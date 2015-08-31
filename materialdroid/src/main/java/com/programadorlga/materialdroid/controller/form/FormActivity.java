package com.programadorlga.materialdroid.controller.form;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.programadorlga.materialdroid.R;
import com.programadorlga.materialdroid.annotation.form.BooleanSwitch;
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
        long id = this.getIntent().getLongExtra("id", 0);
        realm = Realm.getInstance(this);
        if (id > 0) {
            entityInstance = realm.where(entity).equalTo("id", id).findFirst();
        }
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
                Method get = null;
                if (entityInstance != null) {
                    get = entityInstance.getClass().getMethod("get" + fieldEntity.getName().substring(0, 1).toUpperCase() + fieldEntity.getName().substring(1));
                }
                if (fieldEntity.getType().isAssignableFrom(String.class)) {
                    EditTextMaterial editTextMaterial = new EditTextMaterial(this);
                    if (entityInstance != null) {
                        editTextMaterial.setText(get.invoke(entityInstance).toString());
                    }
                    layout.addView(new FieldGroup(this, fieldEntity, editTextMaterial));
                } else if (fieldEntity.getType().isAssignableFrom(Integer.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Short.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Long.TYPE)) {
                    EditTextMaterial editTextMaterial = new EditTextMaterial(this, InputType.TYPE_CLASS_NUMBER);
                    if (entityInstance != null) {
                        editTextMaterial.setText(String.valueOf(get.invoke(entityInstance)));
                    }
                    layout.addView(new FieldGroup(this, fieldEntity, editTextMaterial));
                } else if (fieldEntity.getType().isAssignableFrom(Float.TYPE)
                        || fieldEntity.getType().isAssignableFrom(Double.TYPE)) {
                    EditTextMaterial editTextMaterial = new EditTextMaterial(this, InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    if (entityInstance != null) {
                        editTextMaterial.setText(String.valueOf(get.invoke(entityInstance)));
                    }
                    layout.addView(new FieldGroup(this, fieldEntity, editTextMaterial));
                } else if (fieldEntity.getType().isAssignableFrom(Date.class)) {
                    DateTimeMaterial dateTimeMaterial;
                    if (entityInstance == null) {
                        dateTimeMaterial = new DateTimeMaterial(this, fieldEntity, new Date());
                    } else {
                        dateTimeMaterial = new DateTimeMaterial(this, fieldEntity, (Date) get.invoke(entityInstance));
                    }
                    layout.addView(new FieldGroup(this, fieldEntity, dateTimeMaterial));
                } else if (fieldEntity.getType().isAssignableFrom(Boolean.TYPE)) {
                    BooleanMaterial booleanMaterial;
                    if (entityInstance == null) {
                        booleanMaterial = new BooleanMaterial(this, fieldEntity, false , false);
                    } else {
                        booleanMaterial = new BooleanMaterial(this, fieldEntity, (Boolean) get.invoke(entityInstance), false);
                    }
                    layout.addView(new FieldGroup(this, fieldEntity, booleanMaterial, LinearLayoutCompat.HORIZONTAL));
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            this.save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        try {
            realm.beginTransaction();
            if (entityInstance == null) {
                entityInstance = realm.createObject(entity);
            }
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
                }else if (fieldEntity.getType().isAssignableFrom(Boolean.TYPE)) {
                    FieldGroup fieldGroup = (FieldGroup) layout.getChildAt(i);
                    BooleanMaterial booleanMaterial = (BooleanMaterial) fieldGroup.getView();
                    set.invoke(entityInstance, booleanMaterial.isChecked());
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