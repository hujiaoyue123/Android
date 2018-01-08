package com.boxin.beautypine.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * GridViewAdapter
 * User: zouyu
 * Date: :2017/10/14 0014
 * Version: 1.0
 */

public abstract class GridViewAdapter<T> extends BaseAdapter {

    private List<T> data;

    public GridViewAdapter(List<T> data){
        this.data = data;
    }

    public abstract View OvrideGetView(int position, View convertView, ViewGroup parent);

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return OvrideGetView( position,  convertView,  parent);
    }

    public List<T> getList(){
        return this.data;
    }
}
