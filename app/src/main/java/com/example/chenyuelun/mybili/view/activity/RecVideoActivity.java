package com.example.chenyuelun.mybili.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.chenyuelun.mybili.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class RecVideoActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    SurfaceView videoview;
    private String uri;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_video);
        ButterKnife.bind(this);
        uri = getIntent().getStringExtra("uri");
        Log.e("TAG", "uri" + uri);
        initVideo();
    }

    private void initVideo() {
        holder = videoview.getHolder();
        ijkMediaPlayer = new IjkMediaPlayer();
        playVideo();

    }

    private void playVideo() {
        try {
            ijkMediaPlayer.setDataSource(this, Uri.parse(uri));
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
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.setKeepInBackground(false);


    }



}
