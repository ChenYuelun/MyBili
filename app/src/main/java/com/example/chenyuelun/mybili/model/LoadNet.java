package com.example.chenyuelun.mybili.model;

import com.example.chenyuelun.mybili.model.bean.LiveBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public interface LoadNet {
    @GET
    Observable<LiveBean> getLiveData();
}
