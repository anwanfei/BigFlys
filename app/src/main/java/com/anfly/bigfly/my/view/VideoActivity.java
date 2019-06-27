package com.anfly.bigfly.my.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {
    File rootFile = Environment.getExternalStorageDirectory();
    String[] vedios = {rootFile + "/360/a.mp4", rootFile + "/360/b.mp4", rootFile + "/360/c.mp4"};
    @BindView(R.id.tv_vedio_system)
    TextView tvVedioSystem;
    @BindView(R.id.tv_vedio_videoview)
    TextView tvVedioVideoview;
    @BindView(R.id.tv_vedio_mediaplayer)
    TextView tvVedioMediaplayer;
    @BindView(R.id.vv_vv)
    VideoView vvVv;
    private int i;

    @Override
    protected void initData(Bundle savedInstanceState) {
        checkPermission();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @OnClick({R.id.tv_vedio_system, R.id.tv_vedio_videoview, R.id.tv_vedio_mediaplayer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_vedio_system:
                playSystem();
                break;
            case R.id.tv_vedio_videoview:
                videoViewPlay();
                break;
            case R.id.tv_vedio_mediaplayer:
                mediaplayerPlay();
                break;
        }
    }

    private void mediaplayerPlay() {
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra("data", vedios[1]);
        startActivity(intent);
    }

    private void videoViewPlay() {
        MediaController mediaController = new MediaController(this);
        vvVv.setMediaController(mediaController);
        vvVv.setVideoPath(vedios[i]);
        vvVv.start();

        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i < vedios.length - 1) {
                    i++;
                } else {
                    i = 0;
                }

                vvVv.setVideoPath(vedios[i]);
                vvVv.start();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i < 0) {
                    i = vedios.length - 1;
                }

                vvVv.setVideoPath(vedios[i]);
                vvVv.start();
            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(VideoActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(VideoActivity.this, "授权失败，无法播放", Toast.LENGTH_SHORT).show();
        }
    }

    private void playSystem() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(vedios[0]), "video/mp4");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
