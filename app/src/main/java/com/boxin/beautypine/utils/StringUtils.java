package com.boxin.beautypine.utils;

/**
 * 字符串工具
 * User: zouyu
 * Date: :2017/10/8
 * Version: 1.0
 */

public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(null==str||str.trim().isEmpty()
                ||"null".equalsIgnoreCase(str.trim()) || "undefined".equalsIgnoreCase(str.trim()) ){
            return true;
        }
        return false;
    }

}
