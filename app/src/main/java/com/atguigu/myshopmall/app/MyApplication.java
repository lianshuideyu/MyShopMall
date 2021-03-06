package com.atguigu.myshopmall.app;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/6/12.
 */

public class MyApplication extends Application {

    public static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        initOkhttp();

        //扫一扫功能
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 得到全局的上下文
     * @return
     */
    public static MyApplication getContext(){
        return instance;
    }

    private void initOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(5000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
