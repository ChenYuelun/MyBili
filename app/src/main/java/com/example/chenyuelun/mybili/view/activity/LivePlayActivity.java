package com.example.chenyuelun.mybili.view.activity;

import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.example.chenyuelun.mybili.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LivePlayActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    SurfaceView videoview;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
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
}
