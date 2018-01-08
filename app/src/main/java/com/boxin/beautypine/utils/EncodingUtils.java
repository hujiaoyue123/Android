package com.boxin.beautypine.utils;

import java.io.UnsupportedEncodingException;

/**
 * 编码工具
 * User: zouyu
 * Date: :2017/9/27
 * Version: 1.0
 */

public class EncodingUtils {

    /**
     * byte to string
     * @param buffer
     * @param chartSet
     * @return
     */
    public static String getString(byte[] buffer, String chartSet){
        try {
            return new String(buffer,chartSet);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(e);
        }
        return null;
    }
}
