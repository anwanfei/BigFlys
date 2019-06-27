package com.anfly.bigfly.distance.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.activity.WebViewActivity;
import com.anfly.bigfly.distance.adapter.NewsAdapter;
import com.anfly.bigfly.distance.bean.NewsBean;
import com.anfly.bigfly.distance.bean.NewsDbBean;
import com.anfly.bigfly.distance.callback.OnNewsItemClickListener;
import com.anfly.bigfly.distance.model.ImpNewsModel;
import com.anfly.bigfly.distance.presenter.ImpNewsPresenter;
import com.anfly.bigfly.distance.utils.DbUtil;
import com.anfly.bigfly.distance.view.NewsView;
import com.anfly.bigfly.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class NewsFragment extends BaseFragment implements NewsView<List<NewsBean.ResultBean.DataBean>, String>, OnRefreshListener, OnRefreshLoadMoreListener, OnNewsItemClickListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private ImpNewsPresenter impNewsPresenter;
    private List<NewsBean.ResultBean.DataBean> newsListBeans;
    private NewsAdapter newsAdapter;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        super.initView();
        impNewsPresenter = new ImpNewsPresenter(new ImpNewsModel(), this);
        newsListBeans = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsAdapter = new NewsAdapter(getActivity(), newsListBeans);
        rv.setAdapter(newsAdapter);
        newsAdapter.setOnNewsItemClickListener(this);

        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnRefreshLoadMoreListener(this);

        initData();

        registerForContextMenu(rv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 0, 0, Constants.COLLECTON);
        menu.add(0, 1, 1, Constants.DETAIL);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                insert();
                break;
            case 1:
                goWebViewActivity(newsListBeans.get(position).getUrl());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void insert() {
        NewsBean.ResultBean.DataBean dataBean = newsListBeans.get(position);
        NewsDbBean newsDbBean = new NewsDbBean();
        newsDbBean.setId(Long.valueOf(position));
        newsDbBean.setThumbnailUrl(dataBean.getThumbnail_pic_s());
        newsDbBean.setTitle(dataBean.getTitle());
        newsDbBean.setDate(dataBean.getDate());
        newsDbBean.setAuthorName(dataBean.getAuthor_name());
        newsDbBean.setCategory(dataBean.getCategory());

        long insert = DbUtil.getDbUtil().insert(newsDbBean);

        if (insert >= 0) {
            Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void goWebViewActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        goToActivity(WebViewActivity.class, bundle);
    }

    @Override
    protected void initData() {
        super.initData();
        impNewsPresenter.getNewsList("315a54463602e8f87c8aa4b3a817bf84", "top");
    }

    @Override
    public void onSuccess(List<NewsBean.ResultBean.DataBean> dataBeans) {
        this.newsListBeans = dataBeans;
        newsAdapter.loadMore(dataBeans);
        smartRefresh.finishRefresh();
        smartRefresh.finishLoadMore();
    }

    @Override
    public void onFail(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        newsAdapter.clearData();
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        initData();
    }

    @Override
    public void onNewsItemClickListener(int position, NewsBean.ResultBean.DataBean dataBean) {
        goWebViewActivity(dataBean.getUrl());
    }

    @Override
    public void onNewsItemLongClickListener(int position, NewsBean.ResultBean.DataBean dataBean) {
        this.position = position;
    }
}
