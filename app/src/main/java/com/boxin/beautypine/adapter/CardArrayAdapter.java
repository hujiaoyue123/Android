package com.boxin.beautypine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片适配器
 * User: zouyu
 * Date: :2017/10/17 0017
 * Version: 1.0
 */

public abstract class CardArrayAdapter<T>  extends ArrayAdapter<T> {
    private static final String TAG = "CardArrayAdapter";
    private List<T> cardList = new ArrayList<T>();

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(T object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public T getItem(int index) {
        return this.cardList.get(index);
    }

    public abstract View OvrideGetView(int position, View convertView, ViewGroup parent);


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return OvrideGetView( position,  convertView,  parent);

    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}