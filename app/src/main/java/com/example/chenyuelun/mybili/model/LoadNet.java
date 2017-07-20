package com.example.chenyuelun.mybili.model;

import com.example.chenyuelun.mybili.model.bean.LiveBean;
import com.example.chenyuelun.mybili.model.bean.RecBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public interface LoadNet {
    @GET("AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=hdpi&ts=1490013188000&sign=92541a11ed62841120e786e637b9db3b")
    Observable<LiveBean> getLiveData();

    @GET("index?appkey=1d8b6e7d45233436&build=501000&idx=1490013261&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1490015599000&sign=af4edc66aef7e443c98c28de2b660aa4")
    Observable<RecBean> getRecData();
}
