package com.anfly.bigfly.my.Fragment;

import android.widget.TextView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.my.view.VideoActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_collection)
    TextView tvCollection;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.tv_collection)
    public void onClick() {
        goToActivity(VideoActivity.class, null);
    }
}
