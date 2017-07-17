package com.example.chenyuelun.mybili.model;

import com.example.chenyuelun.mybili.model.bean.LiveBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public interface LoadNet {
    @GET("AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=hdpi&ts=1490013188000&sign=92541a11ed62841120e786e637b9db3b")
    Observable<LiveBean> getLiveData();
}
