package com.example.chenyuelun.mybili.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if(layoutId == 0) {
            TextView textView = new TextView(getActivity());
            textView.setText("布局ID请不要返回0");
            return textView;
        }
        View view = View.inflate(getActivity(), layoutId, null);
        bind = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initListener() {

    }

    public abstract void initData();

    public void initView() {

    }

    public abstract int getLayoutId();


}
