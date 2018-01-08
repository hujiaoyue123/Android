package com.boxin.beautypine.utils;

import android.util.Log;

/**
 * 日志工具类
 * User: zouyu
 * Date: :2017/9/27
 * Version: 1.0
 */

public class LogUtils {

    public static Integer LOG_LEVEL   = 1;                              //应用使用日志级别

    public static String TAG          = "com.boxin.beautypine";         //应用TAG
    public static Integer Verbose     = 0;
    public static Integer Debug       = 1;
    public static Integer Info        = 2;
    public static Integer Warning     = 3;
    public static Integer Error       = 4;

    public static void v(String msg){
        if (LOG_LEVEL >= Verbose){
            Log.v(TAG,msg);
        }
    }
    public static void d(String msg){
        if (LOG_LEVEL >= Debug){
            Log.d(TAG,msg);
        }
    }
    public static void i(String msg){
        if (LOG_LEVEL >= Info){
            Log.i(TAG,msg);
        }
    }
    public static void w(String msg){
        if (LOG_LEVEL >= Warning){
            Log.w(TAG,msg);
        }
    }
    public static void e(String msg){
        if (LOG_LEVEL >= Error){
            Log.e(TAG,msg);
        }
    }
    public static void e(Exception e){
        if (LOG_LEVEL >= Error){
            Log.e(TAG,e.getMessage());
        }
    }
    public static void e(Error e){
        if (LOG_LEVEL >= Error){
            Log.e(TAG,e.getMessage());
        }
    }

}
