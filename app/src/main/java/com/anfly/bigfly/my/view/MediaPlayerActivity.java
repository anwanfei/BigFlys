package com.anfly.bigfly.my.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class MediaPlayerActivity extends BaseActivity {


    @BindView(R.id.sv)
    SurfaceView sv;
    @BindView(R.id.tv_pause)
    TextView tvPause;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_replay)
    TextView tvReplay;
    private MediaPlayer mediaPlayer;
    private int mWidth;
    private int mHeight;

    @Override
    protected void initData(Bundle savedInstanceState) {

        getScreen();

        String path = getIntent().getStringExtra("data");

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            SurfaceHolder surfaceHolder = sv.getHolder();

            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mediaPlayer.setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //获取视频的宽高
                    int videoHeight = mediaPlayer.getVideoHeight();

                    int videoWidth = mediaPlayer.getVideoWidth();

                    if (videoHeight > mHeight || videoWidth > mWidth) {

                        //如果视频的宽或者高超出屏幕,要缩放

                        float widthRatio = (float) videoWidth / (float) mWidth;

                        float heightRatio = (float) videoHeight / (float) mHeight;

                        //选择大的进行缩放
                        float max = Math.max(widthRatio, heightRatio);

                        videoWidth = (int) Math.ceil(videoWidth / max);

                        videoHeight = (int) Math.ceil(videoHeight / max);

                        //设置surfaceview的布局参数
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(videoWidth, videoHeight);

                        //设置垂直居中
                        layoutParams.gravity = Gravity.CENTER_VERTICAL;

                        sv.setLayoutParams(layoutParams);

                    }
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_media_player;
    }

    @OnClick({R.id.tv_pause, R.id.tv_stop, R.id.tv_replay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pause:
                mediaPlayer.pause();
                break;
            case R.id.tv_stop:
                mediaPlayer.stop();
                finish();
                break;
            case R.id.tv_replay:
                mediaPlayer.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void getScreen() {
        WindowManager systemService = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        Display defaultDisplay = systemService.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();

        defaultDisplay.getMetrics(metrics);


        mWidth = metrics.widthPixels;

        mHeight = metrics.heightPixels;
    }
}
