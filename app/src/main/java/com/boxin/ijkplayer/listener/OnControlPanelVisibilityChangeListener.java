package com.boxin.ijkplayer.listener;
/**
 * ========================================
 * <p>
 * 描 述：操作面板显示或者隐藏改变监听
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public interface OnControlPanelVisibilityChangeListener {

    /**true 为显示 false为隐藏*/
    void change(boolean isShowing);
}