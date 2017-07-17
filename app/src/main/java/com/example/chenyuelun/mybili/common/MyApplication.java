package com.example.chenyuelun.mybili.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public class MyApplication extends Application {

    //全局上下文
    private static Context context;

    //切换线程用到
    private static Handler handler;

    public static Handler getHandler() {
        return handler;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        handler = new Handler();
    }

    public static Context getContext() {
        return context;
    }
}
