package com.anfly.bigfly.distance.Fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Anfly
 * @date 2019/6/20
 * description：
 */
public class VideoviewFragment extends BaseFragment {
    @BindView(R.id.tv_vedio_system)
    TextView tvVedioSystem;
    @BindView(R.id.tv_vedio_videoview)
    TextView tvVedioVideoview;
    @BindView(R.id.vv_vv)
    VideoView vvVv;
    @BindView(R.id.tv_vedio_mediaplayer)
    TextView tvVedioMediaplayer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @OnClick({R.id.tv_vedio_system, R.id.tv_vedio_videoview, R.id.tv_vedio_mediaplayer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_vedio_system:
                break;
            case R.id.tv_vedio_videoview:
                break;
            case R.id.tv_vedio_mediaplayer:
                break;
        }
    }
}
