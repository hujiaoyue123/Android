package com.boxin.ijkplayer.listener;

import android.widget.ImageView;
/**
 * ========================================
 * <p>
 * <p>
 * 描 述：显示视频缩列图监听
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public interface OnShowThumbnailListener {

    /**回传封面的view，让用户自主设置*/
    void onShowThumbnail(ImageView ivThumbnail);
}