package com.boxin.beautypine.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoaderInterface;

/**
 * 图片加载抽象类
 * User: zouyu
 * Date: :2017/9/29
 * Version: 1.0
 */

public abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

}