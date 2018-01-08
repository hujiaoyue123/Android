package com.boxin.beautypine.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * User: zouyu
 * Date: :2017/9/29
 * Version: 1.0
 */

public class PicassoImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Picasso 加载图片简单用法
        Picasso.with(context).load(path.toString()).into(imageView);

        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }
}