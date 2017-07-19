package com.example.chenyuelun.mybili.view.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.utils.UiUtils;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public abstract class LoadingView extends FrameLayout {
    private View errorView;
    private View loadingView;
    private View successView;

    private static final int STATE_ERROR = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_SUCCESS = 2;

    private int current_state = STATE_LOADING;
    private LayoutParams params;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        if (errorView == null) {
            errorView = UiUtils.inflate(R.layout.pager_error);
            this.addView(errorView, params);
        }
        if (loadingView == null) {
            loadingView = UiUtils.inflate(R.layout.pager_loading);
            this.addView(loadingView, params);
        }

        showSafeView();
    }


    private void showSafeView() {
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startShowView();
            }
        });
    }

    private void startShowView() {
        errorView.setVisibility(current_state == STATE_ERROR? View.VISIBLE:View.GONE);
        loadingView.setVisibility(current_state == STATE_LOADING ? View.VISIBLE : View.GONE);
        if (successView == null) {
            successView = getShowView();
            this.addView(successView, params);
        }
        successView.setVisibility(current_state == STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }

    //用于fragment第一次加载数据，并根据数据状态显示相应的界面
    public void getData(){
        String url = getUrl();
        if (TextUtils.isEmpty(url)) {
            current_state = STATE_SUCCESS;
            showSafeView();
            return;
        }



    }


    //用于子fragment非第一次请求网络部加载数据
    public void getDataFromNet(String url){


    }

    //抽象方法 用于联网获取数据后将数据返还子fragment进行下一步操作
    protected abstract void setData(String response);

    //抽象方法 用于页面第一次加载时 获取联网Url联网获取数据
    protected abstract String getUrl();

    //获取状态成功时要显示的view 各个子类根据显示必须实现
    protected abstract View getShowView();

    //触摸事件处理 用于显示错误界面时刷新处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() ==MotionEvent.ACTION_DOWN) {
            if(current_state == STATE_ERROR) {
                current_state = STATE_LOADING;
                startShowView();
                getData();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
