package com.example.chenyuelun.mybili.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.base.BaseFragment;
import com.example.chenyuelun.mybili.model.AppNetManager;
import com.example.chenyuelun.mybili.model.LoadNet;
import com.example.chenyuelun.mybili.model.bean.LiveBean;
import com.example.chenyuelun.mybili.view.adapter.LiveRvAdapter;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public class LiveFragment extends BaseFragment {

    @BindView(R.id.rv_live)
    RecyclerView rvLive;
    Unbinder unbinder;
    private LiveRvAdapter liveRvAdapter;

    @Override
    public void initData() {
        liveRvAdapter = new LiveRvAdapter(getActivity());
        rvLive.setAdapter(liveRvAdapter);
        rvLive.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        getDataFromNet();
    }

    private void getDataFromNet() {
        new Retrofit
                .Builder()
                .baseUrl(AppNetManager.LIVE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoadNet.class)
                .getLiveData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LiveBean>() {
                    @Override
                    public void accept(@NonNull LiveBean liveBean) throws Exception {
                        Log.e("TAG", "liveBean==" + liveBean.getMessage());
                        setData(liveBean);
                    }
                });
    }

    private void setData(LiveBean liveBean) {

        liveRvAdapter.refesh(liveBean.getData());

    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_live;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        //开始轮播
//        banner.startAutoPlay();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        //结束轮播
//        banner.stopAutoPlay();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
