package com.example.chenyuelun.mybili.utils;

import android.os.Process;
import android.view.View;

import com.example.chenyuelun.mybili.common.MyApplication;

import static com.example.chenyuelun.mybili.common.MyApplication.getContext;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public class UiUtils {

    //加载布局
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }


    //确保程序运行在主线程（需要的时候）
    public static void runOnUiThread(Runnable runnable) {
        if (Process.myTid() == Process.myPid()) {
            runnable.run();
        } else {
            MyApplication.getHandler().post(runnable);
        }

    }
}
