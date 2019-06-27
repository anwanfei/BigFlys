package com.anfly.bigfly.distance.Fragment;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.adapter.CollectionAdapter;
import com.anfly.bigfly.distance.bean.NewsDbBean;
import com.anfly.bigfly.distance.callback.OnCollectionItemClickListener;
import com.anfly.bigfly.distance.model.ImpCollectionModel;
import com.anfly.bigfly.distance.presenter.ImpCollectionPresenter;
import com.anfly.bigfly.distance.utils.DbUtil;
import com.anfly.bigfly.distance.view.CollectionView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class CollectionFragment extends BaseFragment implements CollectionView<List<NewsDbBean>, String>, OnRefreshLoadMoreListener, OnCollectionItemClickListener {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private ArrayList<NewsDbBean> newsListBeans;
    private CollectionAdapter collectionAdapter;
    private ImpCollectionPresenter impCollectionPresenter;
    private int positon;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            impCollectionPresenter.getNewsList();
        } else {
            if (newsListBeans != null && newsListBeans.size() > 0) {
                collectionAdapter.clearData();
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();

        impCollectionPresenter = new ImpCollectionPresenter(new ImpCollectionModel(), this);
        newsListBeans = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(mContext));

        collectionAdapter = new CollectionAdapter(mContext, newsListBeans);

        rv.setAdapter(collectionAdapter);

        smartRefresh.setOnRefreshLoadMoreListener(this);

        collectionAdapter.setCollectionItemClickListener(this);

        registerForContextMenu(rv);
    }

    @Override
    public void onSuccess(final List<NewsDbBean> newsDbBeans) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                collectionAdapter.loadMore(newsDbBeans);
            }
        });
    }

    @Override
    public void onFail(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        smartRefresh.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        smartRefresh.finishRefresh();
    }

    @Override
    public void onCollectionItemClickListener(int position, NewsDbBean newsDbBean) {
        this.positon = position;

    }

    @Override
    public void onCollectionItemLongClickListener(int position, NewsDbBean newsDbBean) {
        this.positon = position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 6:
                delete();
                break;
            case 7:
                updata();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void updata() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(R.layout.)

        NewsDbBean newsDbBean = newsListBeans.get(positon);
        newsDbBean.setTitle("吃饭");
        boolean updata = DbUtil.getDbUtil().updata(newsDbBean);
        if (updata) {
            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
            collectionAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {

        NewsDbBean newsDbBean = newsListBeans.get(positon);
        boolean delete = DbUtil.getDbUtil().delete(newsDbBean);
        if (delete) {
            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            newsListBeans.remove(positon);
            collectionAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 6, 0, "删除");
        menu.add(0, 7, 1, "修改");
    }
}
