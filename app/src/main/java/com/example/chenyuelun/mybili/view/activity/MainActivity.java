package com.example.chenyuelun.mybili.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.base.BaseFragment;
import com.example.chenyuelun.mybili.utils.DensityUtils;
import com.example.chenyuelun.mybili.utils.UiUtils;
import com.example.chenyuelun.mybili.view.adapter.MainFMPAdapter;
import com.example.chenyuelun.mybili.view.fragment.DynamicFragment;
import com.example.chenyuelun.mybili.view.fragment.LiveFragment;
import com.example.chenyuelun.mybili.view.fragment.RecommendFragment;
import com.example.chenyuelun.mybili.view.fragment.ZFFragment;
import com.example.chenyuelun.mybili.view.fragment.Zoneragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;
    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.iv_title_userimage)
    ImageView ivTitleUserimage;
    @BindView(R.id.tv_title_username)
    TextView tvTitleUsername;
    private Unbinder bind;
    private List<BaseFragment> fragments;
    private MainFMPAdapter mainFMPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        setSupportActionBar(homeToolbar);

        initData();
        initListener();
    }

    private void initListener() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("这是小按钮");
            }
        });

        ivTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initData() {
        initFragment();
        mainFMPAdapter = new MainFMPAdapter(getSupportFragmentManager(), fragments);
        mainViewpager.setAdapter(mainFMPAdapter);
        mainTablayout.setupWithViewPager(mainViewpager);
        setIndicator(mainTablayout, DensityUtils.px2dip(MainActivity.this, 20), DensityUtils.px2dip(MainActivity.this, 20));


    }

    //添加要显示的5个fragment
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LiveFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new ZFFragment());
        fragments.add(new Zoneragment());
        fragments.add(new DynamicFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    //通过反射更改tablyout下划线宽度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    //监听返回键 如果菜单是打开就关闭
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                UiUtils.showToast("再按一次退出软件");
                isExit = true;
                Log.e("TAG", "isExit==" + isExit);

                new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        isExit = false;
                        Log.e("TAG", "isExit==" + isExit);
                    }
                }.start();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
