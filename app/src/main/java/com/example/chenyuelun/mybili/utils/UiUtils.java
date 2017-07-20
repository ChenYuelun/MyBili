package com.example.chenyuelun.mybili.utils;

import android.content.Context;
import android.os.Process;
import android.view.View;
import android.widget.Toast;

import com.example.chenyuelun.mybili.common.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;


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


    //拼接字符串
    public static String getString(Context context, String str, int stringId){
        String s = String.format(context.getResources().getString(stringId), str);
        return s;
    }


    //吐司
    public static void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static Context getContext(){
        return MyApplication.getContext();
    }

    public static String getFormatDateTime(String pattern, int dateTime){
        Date d = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }
}
