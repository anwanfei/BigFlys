package com.anfly.bigfly.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.Fragment.DistanceFragment;
import com.anfly.bigfly.home.Fragment.HomeFragment;
import com.anfly.bigfly.home.receiver.NetWorkReceiver;
import com.anfly.bigfly.my.Fragment.MyFragment;
import com.anfly.bigfly.my.view.VideoActivity;
import com.anfly.bigfly.utils.Constants;
import com.anfly.bigfly.wheel.Fragment.WheelFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.rg_bottom_tag)
    RadioGroup rgBottomTag;
    int position = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nv)
    NavigationView nv;
    @BindView(R.id.dl)
    DrawerLayout dl;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    //上次切换的Fragment
    private Fragment fromFragment;
    private MyFragment myFragment;
    private HomeFragment homeFragment;
    private WheelFragment wheelFragment;
    private DistanceFragment distanceFragment;
    private NetWorkReceiver netWorkReceiver;

    @Override
    protected void initData(Bundle savedInstanceState) {
        initFragment();
//        if (savedInstanceState == null) {
//            initFragment();
//        } else {
//            restoreFragments(savedInstanceState);
//        }
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        netWorkReceiver = new NetWorkReceiver();
        registerReceiver(netWorkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(netWorkReceiver);
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar.setTitle("");
        // Logo
//        toolbar.setLogo(R.drawable.iv_back);
        // 主标题
        toolbar.setTitle("首页");
        // 副标题
//        toolbar.setSubtitle("远方");
        //设置toolbar
        setSupportActionBar(toolbar);
        //左边的小箭头（注意某些版本api需要在setSupportActionBar(toolbar)之后才有效果）
        toolbar.setNavigationIcon(R.drawable.iv_back);
        //菜单点击事件（注意需要在setSupportActionBar(toolbar)之后才有效果）
//        toolbar.setOnMenuItemClickListener(this);

        //设置navigationview中menu菜单图片显示
        nv.setItemIconTintList(null);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.open, R.string.close);

        dl.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        nv.setNavigationItemSelectedListener(this);

        View view = nv.inflateHeaderView(R.layout.nv_header);

        ImageView imageView = view.findViewById(R.id.iv_header);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "侧滑菜单头部", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 设置RadioGroup的事件监听
     */
    private void initListener() {

        rgBottomTag.setOnCheckedChangeListener(this);
        //设置默认界面
        rgBottomTag.check(R.id.rb_home);
    }

    /**
     * 恢复保存的fragment
     *
     * @param savedInstanceState
     */
    private void restoreFragments(Bundle savedInstanceState) {
        FragmentManager sfm = getSupportFragmentManager();
        homeFragment = (HomeFragment) sfm.getFragment(savedInstanceState, Constants.HOME_FRAGMENT_KEY);
        myFragment = (MyFragment) sfm.getFragment(savedInstanceState, Constants.MY_FRAGMENT_KEY);
        distanceFragment = (DistanceFragment) sfm.getFragment(savedInstanceState, Constants.DISTANCD_FRAGMENT_KEY);
        wheelFragment = (WheelFragment) sfm.getFragment(savedInstanceState, Constants.WHEEL_FRAGMENT_KEY);

        if (!fragments.contains(homeFragment)) fragments.add(homeFragment);
        if (!fragments.contains(distanceFragment)) fragments.add(distanceFragment);
        if (!fragments.contains(wheelFragment)) fragments.add(wheelFragment);
        if (!fragments.contains(myFragment)) fragments.add(myFragment);
    }

    private void initFragment() {
        if (myFragment == null) myFragment = new MyFragment();
        if (homeFragment == null) homeFragment = new HomeFragment();
        if (wheelFragment == null) wheelFragment = new WheelFragment();
        if (distanceFragment == null) distanceFragment = new DistanceFragment();

        if (!homeFragment.isAdded()) fragments.add(homeFragment);
        if (!distanceFragment.isAdded()) fragments.add(distanceFragment);
        if (!wheelFragment.isAdded()) fragments.add(wheelFragment);
        if (!myFragment.isAdded()) fragments.add(myFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                toolbar.setTitle("首页");
                position = 0;
                break;
            case R.id.rb_distance:
                toolbar.setTitle("远方");
                position = 1;
                break;
            case R.id.rb_wheel:
                toolbar.setTitle("轮子");
                position = 2;
                break;
            case R.id.rb_my:
                toolbar.setTitle("我的");
                position = 3;
                break;
            default:
                position = 0;
                toolbar.setTitle("首页");
                break;
        }

        //根据位置获得对应的fragment
        Fragment toFragment = getFragment();
        //切换到相应的fragment
        switchFrament(fromFragment, toFragment);
    }


    /**
     * @param curFragment  刚显示的Fragment,马上就要被隐藏了
     * @param willFragment 马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment curFragment, Fragment willFragment) {
        if (curFragment != willFragment) {
            fromFragment = willFragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!willFragment.isAdded()) { //to没有被添加
                if (curFragment != null) {//from隐藏
                    ft.hide(curFragment);
                }
                if (willFragment != null) {//添加to
                    ft.add(R.id.fl_content, willFragment).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (curFragment != null) {
                    ft.hide(curFragment);
                }
                //显示to
                if (willFragment != null) {
                    ft.show(willFragment).commit();
                }
            }
        }
    }

    private Fragment getFragment() {
        BaseFragment baseFragment = null;
        if (fragments != null) {
            baseFragment = fragments.get(position);
        }
        return baseFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存fragment对象，以便改activity发生崩溃、被回收等，activity会被销毁并重新创建，
        // 并且在销毁之前执行了onSaveInstanceState(Bundle outState)这个方法。
        // 这个方法会保存activity的一些信息，其中就包括添加过的fragment，当activity被重新创建时，
        // 会初始化其中的变量，这个时候点击底部导航的话会重新去添加fragment，也就导致了重叠的问题。
        Log.e("TAG", "onSaveInstanceState");
        //fragment不为空时 保存
//        if (homeFragment != null) {
//            getSupportFragmentManager().putFragment(outState, Constants.HOME_FRAGMENT_KEY, homeFragment);
//        }
//
//        if (myFragment != null) {
//            getSupportFragmentManager().putFragment(outState, Constants.MY_FRAGMENT_KEY, myFragment);
//        }
//
//        if (wheelFragment != null) {
//            getSupportFragmentManager().putFragment(outState, Constants.WHEEL_FRAGMENT_KEY, wheelFragment);
//        }
//
//        if (distanceFragment != null) {
//            getSupportFragmentManager().putFragment(outState, Constants.DISTANCD_FRAGMENT_KEY, distanceFragment);
//        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item1:
                Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_collection:
                Intent intent = new Intent(this, VideoActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
