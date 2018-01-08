package com.boxin.beautypine;

import android.app.Application;

import com.boxin.beautypine.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 扩展Application类
 * User: zouyu
 * Date: :2017/9/27
 * Version: 1.0
 */

public class BPApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        //配置okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))       //配置log
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)      //配置连接超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)         //配置读取超时
                .cookieJar(cookieJar)                               //配置cookie
                .build();
        OkHttpUtils.initClient(okHttpClient);
        LogUtils.d("set okhttp");
    }

}
