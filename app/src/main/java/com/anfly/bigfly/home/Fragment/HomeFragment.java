package com.anfly.bigfly.home.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.activity.WebViewActivity;
import com.anfly.bigfly.home.adapter.HomeListsAdapter;
import com.anfly.bigfly.home.api.HomeServer;
import com.anfly.bigfly.home.bean.BannerBean;
import com.anfly.bigfly.home.bean.HomeListBean;
import com.anfly.bigfly.home.callback.OnHomeItemClickListener;
import com.anfly.bigfly.home.view.BroadcastReceiverActivity;
import com.anfly.bigfly.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private int page = 0;
    private ArrayList<HomeListBean.DataBean.DatasBean> homeLists;
    private ArrayList<BannerBean.DataBean> homeBanners;
    private HomeListsAdapter homeListsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        setHasOptionsMenu(true);

        homeLists = new ArrayList<>();
        homeBanners = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeListsAdapter = new HomeListsAdapter(getActivity(), homeLists, homeBanners);
        rv.setAdapter(homeListsAdapter);

        homeListsAdapter.setOnHmeItemClickListener(new OnHomeItemClickListener() {
            @Override
            public void onHomeItemClickListener(int position, HomeListBean.DataBean.DatasBean dataBean) {
                goWebViewActivity(dataBean.getLink());
            }
        });

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initListData();
                smartRefresh.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeLists.clear();
                initListData();
                smartRefresh.finishRefresh();
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        initListData();
        initBanner();
    }

    private void initListData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HomeServer.baseHomeListUrl)
                .build();

        HomeServer homeServer = retrofit.create(HomeServer.class);
        Observable<HomeListBean> observable = homeServer.getHomeList(String.valueOf(page));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeListBean homeListBean) {
                        if (homeListBean != null) {
                            if (homeListBean.getErrorCode() == 0) {
                                List<HomeListBean.DataBean.DatasBean> datas = homeListBean.getData().getDatas();
                                homeLists.addAll(datas);
                                homeListsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, Constants.CODE_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, Constants.RESULT_EMPTY, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", Constants.NET_ERROR + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void goWebViewActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        goToActivity(WebViewActivity.class, bundle);
    }

    private void initBanner() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HomeServer.baseBannerUrl)
                .build();

        HomeServer homeServer = retrofit.create(HomeServer.class);

        Observable<BannerBean> observable = homeServer.getBanner();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (bannerBean != null) {
                            if (bannerBean.getErrorCode() == 0) {
                                final List<BannerBean.DataBean> data = bannerBean.getData();
                                homeBanners.addAll(data);
                                homeListsAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(mContext, Constants.CODE_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, Constants.RESULT_EMPTY, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", Constants.NET_ERROR + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 0, 0, "广播");
        menu.add(0, 1, 1, "求真");
        menu.add(0, 2, 2, "务实");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(mContext, BroadcastReceiverActivity.class);
                startActivity(intent);
                break;
            case 1:
                Toast.makeText(mContext, "杜甫", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(mContext, "李煜", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
