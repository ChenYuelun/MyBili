package com.example.chenyuelun.mybili.view.fragment;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.base.BaseFragment;
import com.example.chenyuelun.mybili.model.AppNetManager;
import com.example.chenyuelun.mybili.model.LoadNet;
import com.example.chenyuelun.mybili.model.bean.LiveBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.banner)
    Banner banner;

    @Override
    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        new Retrofit.Builder()
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
                        initBanner(liveBean.getData().getBanner());
                    }
                });
    }

    //设置Banner
    private void initBanner(List<LiveBean.DataBean.BannerBean> banner) {
        List<String> images = new ArrayList<>();
        for(int i = 0; i < banner.size(); i++) {
            images.add(banner.get(i).getImg());
        }
        //设置图片加载器
        this.banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        this.banner.setImages(images);
        //设置指示器位置
        this.banner.setIndicatorGravity(BannerConfig.RIGHT);

        

        //banner设置方法全部调用完毕时最后调用
        this.banner.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            Picasso.with(context).load((String) path).into(imageView);
            Glide.with(context).load(path).into(imageView);
        }
        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(params);
            return imageView;
        }



    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_live;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


}
