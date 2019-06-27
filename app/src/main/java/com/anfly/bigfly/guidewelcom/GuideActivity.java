package com.anfly.bigfly.guidewelcom;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;
import com.anfly.bigfly.common.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    private ArrayList<Integer> images;

    @Override
    protected void initData(Bundle savedInstanceState) {
        images = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            images.add(R.drawable.bg);
        }
        GuideAdapter guideAdapter = new GuideAdapter(this, images);
        vpGuide.setAdapter(guideAdapter);
        guideAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickListener(View view) {
                goToActivity(MainActivity.class, null);
                closeCurActivity();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

}
