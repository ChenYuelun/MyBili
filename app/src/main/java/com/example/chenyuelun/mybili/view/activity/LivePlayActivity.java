package com.example.chenyuelun.mybili.view.activity;

import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.utils.UiUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LivePlayActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    SurfaceView videoview;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_livername)
    TextView tvLivername;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;
    @BindView(R.id.iv_full_screen)
    ImageView ivFullScreen;
    @BindView(R.id.rl_controler)
    RelativeLayout rlControler;
    @BindView(R.id.fl_video)
    FrameLayout flVideo;
    private SurfaceHolder holder;
    private IjkMediaPlayer ijkMediaPlayer;
    private String url;
    private AnimationDrawable anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        startAnim();
        initVideo();
        initListener();
    }

    private void initListener() {

    }


    private void startAnim() {
        anim = (AnimationDrawable) ivLoading.getBackground();
        anim.start();
    }

    private void stopAnim() {
        anim.stop();
        ivLoading.setVisibility(View.GONE);
    }

    private void initVideo() {
        holder = videoview.getHolder();
        ijkMediaPlayer = new IjkMediaPlayer();
        playVideo();
    }

    private void playVideo() {
        try {
            ijkMediaPlayer.setDataSource(this, Uri.parse(url));
            ijkMediaPlayer.setDisplay(holder);
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    ijkMediaPlayer.setDisplay(holder);

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
            ijkMediaPlayer.prepareAsync();
            stopAnim();
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.setKeepInBackground(false);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.release();
    }


    @Override
    protected void onStop() {
        super.onStop();
        ijkMediaPlayer.stop();
    }


    @OnClick({R.id.iv_back, R.id.iv_more, R.id.iv_play_pause, R.id.iv_full_screen, R.id.rl_controler,R.id.fl_video,R.id.videoview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                UiUtils.showToast("分享popuwindow");
                break;
            case R.id.iv_play_pause:
                playOrPause();
                break;
            case R.id.iv_full_screen:
                UiUtils.showToast("全屏");
                break;
            case R.id.rl_controler:
                showOrHideControler();
                break;
            case R.id.fl_video:
//                showOrHideControler();
                break;
            case R.id.videoview:
                showOrHideControler();
                break;
        }
    }

    private boolean ControlerShow = true;
    private void showOrHideControler() {
//        int visibility = rlControler.getSystemUiVisibility();
//        if (visibility == View.VISIBLE) {
//            rlControler.setVisibility(View.INVISIBLE);
//        } else if (visibility == View.INVISIBLE) {
//            rlControler.setVisibility(View.VISIBLE);
//        }
        if(ControlerShow) {
            rlControler.setVisibility(View.INVISIBLE);
            ControlerShow =false;
        }else {
            rlControler.setVisibility(View.VISIBLE);
            ControlerShow =true;
        }

    }

    private void playOrPause() {
        if (ijkMediaPlayer.isPlaying()) {
            ijkMediaPlayer.pause();
            ivPlayPause.setBackgroundResource(R.drawable.bili_player_play_can_play);
        } else {
            ijkMediaPlayer.start();
            ivPlayPause.setBackgroundResource(R.drawable.bili_player_play_can_pause);
        }
    }


}
