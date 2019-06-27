package com.anfly.bigfly.distance.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.adapter.DistancePageAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class DistanceFragment extends BaseFragment {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_distance;
    }

    @Override
    protected void initView() {
        super.initView();
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new BroadcastReceiverFragment());
        fragments.add(new PictureFragment());
        fragments.add(new BitmapFactoryFragment());
        fragments.add(new MatrixFragment());
        fragments.add(new LruFragment());
        fragments.add(new UpLoadFragment());
        fragments.add(new DownLoadFragment());
        fragments.add(new VideoviewFragment());

        titles = new ArrayList<>();
        titles.add("新闻");//拦截器
        titles.add("收藏");//工具类、升级,懒加载
        titles.add("广播");//
        titles.add("图片");//glide、fresco、Picasso，圆形、圆角、变换
        titles.add("二次采样");//7BitmapFactory
        titles.add("矩阵");//Matrix、ColorMatrix
        titles.add("缓存");//lru原理、案例、三级缓存
        titles.add("上传");//ok、retrofit、原生（进度条），相机、相册
        titles.add("下载");//ok、retrofit、原生下载，进度条、进度值，安装apk，多线程断点下载，服务中下载
        titles.add("视频");//三种方式、第一帧获取

        DistancePageAdapter distancePageAdapter = new DistancePageAdapter(getActivity().getSupportFragmentManager(), fragments, titles);

        vp.setAdapter(distancePageAdapter);

        tab.setupWithViewPager(vp);

    }
}
