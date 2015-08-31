package com.programadorlga.materialdroid.controller.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.programadorlga.materialdroid.R;
import com.programadorlga.materialdroid.annotation.recyclerview.RecyclerViewList;
import com.programadorlga.materialdroid.controller.recyclerview.viewholder.ViewHolderOnlyTitleTextMaterial;
import com.programadorlga.materialdroid.controller.recyclerview.viewholder.ViewHolderTitleSubTitleMaterial;
import com.programadorlga.materialdroid.controller.recyclerview.viewholder.ViewHolderTitleTwoSubTitleMaterial;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class AdapterMaterial extends RecyclerView.Adapter {
    private Class entity;
    private Context context;
    private List items;
    private Realm realm;

    public AdapterMaterial(Context context, Class entity) {
        this.entity = entity;
        this.context = context;
        items = new ArrayList();
        realm = Realm.getInstance(context);
        items.addAll(realm.where(entity).findAll());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (((RecyclerViewList) this.context.getClass().getAnnotation(RecyclerViewList.class)).value().length == 1) {
            return new ViewHolderOnlyTitleTextMaterial(LayoutInflater.from(context).inflate(R.layout.row_recycler_view_only_title, parent, false), this.context);
        } else if (((RecyclerViewList) this.context.getClass().getAnnotation(RecyclerViewList.class)).value().length == 2) {
            return new ViewHolderTitleSubTitleMaterial(LayoutInflater.from(context).inflate(R.layout.row_recycler_view_title_sub_title, parent, false), this.context);
        } else if (((RecyclerViewList) this.context.getClass().getAnnotation(RecyclerViewList.class)).value().length == 3) {
            return new ViewHolderTitleTwoSubTitleMaterial(LayoutInflater.from(context).inflate(R.layout.row_recycler_view_title_two_subtitle, parent, false), this.context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            String list[] = ((RecyclerViewList) this.context.getClass().getAnnotation(RecyclerViewList.class)).value();
            for (int i = 0; i < list.length; i++) {
                Field fieldEntity = entity.getDeclaredField(list[i]);
                Method get = items.get(position).getClass().getMethod("get" + fieldEntity.getName().substring(0, 1).toUpperCase() + fieldEntity.getName().substring(1));
                String value = get.invoke(items.get(position)).toString();
                if (i == 0) {
                    ((ViewHolderOnlyTitleTextMaterial) holder).setTitle(value.toString());
                } else if (i == 1) {
                    ((ViewHolderTitleSubTitleMaterial) holder).setSubTitle(value.toString());
                } else if (i == 2) {
                    ((ViewHolderTitleTwoSubTitleMaterial) holder).setSubTitle2(value.toString());
                }
            }
            Method get = items.get(position).getClass().getMethod("getId");
            long id = (Long) get.invoke(items.get(position));
            ((ViewHolderOnlyTitleTextMaterial) holder).setId(id);
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
    public int getItemCount() {
        return items.size();
    }
}
