package com.example.chenyuelun.mybili.view.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.base.BaseFragment;
import com.example.chenyuelun.mybili.model.AppNetManager;
import com.example.chenyuelun.mybili.model.LoadNet;
import com.example.chenyuelun.mybili.model.bean.RecBean;
import com.example.chenyuelun.mybili.view.activity.RecVideoActivity;
import com.example.chenyuelun.mybili.view.adapter.RecRvAdapter;

import java.util.ArrayList;
import java.util.List;

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

public class RecommendFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView rvRec;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private RecRvAdapter recRvAdapter;
    private GridLayoutManager gridLayoutManager;
    private boolean isMore = false;
    private List<RecBean.DataBean> datas = new ArrayList<>();

    @Override
    public void initData() {
        recRvAdapter = new RecRvAdapter(getActivity());
        rvRec.setAdapter(recRvAdapter);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvRec.setLayoutManager(gridLayoutManager);
        getDataFromNet();

    }

    @Override
    public void initView() {
        super.initView();
        refresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorRefresh));
    }

    private void getDataFromNet() {
        new Retrofit
                .Builder()
                .baseUrl(AppNetManager.RECOMMEND_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoadNet.class)
                .getRecData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RecBean recBean) {
                        setData(recBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        refresh.setRefreshing(false);
    }

    private void setData(RecBean recBean) {
        List<RecBean.DataBean> data = recBean.getData();
        if(!isMore) {
            datas.addAll(0,data);
        }else {
            datas.addAll(data);
        }
        recRvAdapter.refresh(datas);
    }


    @Override
    public void initListener() {
        super.initListener();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isMore = false;
                getDataFromNet();
            }
        });

        recRvAdapter.setOnItemClickListener(new RecRvAdapter.OnItemClickListener() {
            @Override
            public void onItemVideoClicked(RecBean.DataBean dataBean) {
                String uri = dataBean.getUri();
                Intent intent = new Intent(getActivity(),RecVideoActivity.class);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }

            @Override
            public void onItemMoreClicked(RecBean.DataBean dataBean) {
                showMoreDialog(dataBean);
            }
        });


        rvRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition == datas.size() -1) {
                    isMore =true;
                    getDataFromNet();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void showMoreDialog(RecBean.DataBean dataBean) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rec;
    }
}
