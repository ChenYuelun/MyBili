package com.example.chenyuelun.mybili.view.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.base.BaseFragment;
import com.example.chenyuelun.mybili.model.AppNetManager;
import com.example.chenyuelun.mybili.model.LoadNet;
import com.example.chenyuelun.mybili.model.bean.LiveBean;
import com.example.chenyuelun.mybili.utils.UiUtils;
import com.example.chenyuelun.mybili.view.activity.LivePlayActivity;
import com.example.chenyuelun.mybili.view.adapter.LiveRvAdapter;
import com.youth.banner.Banner;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private LiveRvAdapter liveRvAdapter;
    private Banner banner;


    @Override
    public void initView() {
        super.initView();
        refresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorRefresh));
    }

    @Override
    public void initData() {
        liveRvAdapter = new LiveRvAdapter(getActivity());
        rvLive.setAdapter(liveRvAdapter);
        rvLive.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
                .subscribe(new Observer<LiveBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LiveBean liveBean) {
                        setData(liveBean);
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "联网获取数据失败==" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet();
            }
        });
    }

    private void setData(LiveBean liveBean) {

        liveRvAdapter.refesh(liveBean.getData());

    }

    @Override
    public void initListener() {
        super.initListener();

        liveRvAdapter.setOnItemClicklistener(new LiveRvAdapter.OnItemClicklistener() {


            @Override
            public void onLiveClicked(LiveBean.DataBean.PartitionsBean.LivesBean livesBean) {
                UiUtils.showToast(livesBean.getTitle());
                Intent intent = new Intent(getActivity(),LivePlayActivity.class);
                intent.putExtra("url",livesBean.getPlayurl());
                startActivity(intent);

            }

            @Override
            public void onBannerStart(Banner banner) {
                LiveFragment.this.banner = banner;
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_live;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if(banner !=null) {
            banner.startAutoPlay();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if(banner !=null) {
            banner.stopAutoPlay();
        }

    }

}
