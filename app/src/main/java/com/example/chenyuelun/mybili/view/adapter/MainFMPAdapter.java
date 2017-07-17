package com.example.chenyuelun.mybili.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.chenyuelun.mybili.base.BaseFragment;

import java.util.List;

/**
 * Created by chenyuelun on 2017/7/17.
 */

public class MainFMPAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> fragments;
    private String[] titles = {"直播","推荐","追番","分区","动态"};

    public MainFMPAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();

    }
}
