package com.anfly.bigfly.distance.presenter;

import com.anfly.bigfly.distance.bean.NewsBean;
import com.anfly.bigfly.distance.callback.NewsCallback;
import com.anfly.bigfly.distance.model.NewsModel;
import com.anfly.bigfly.distance.view.NewsView;

import java.util.List;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class ImpNewsPresenter implements NewsPresenter, NewsCallback<List<NewsBean.ResultBean.DataBean>, String> {

    private NewsModel newsModel;
    private NewsView newsView;

    public ImpNewsPresenter(NewsModel newsModel, NewsView newsView) {
        this.newsModel = newsModel;
        this.newsView = newsView;
    }

    @Override
    public void getNewsList(String key, String type) {
        if (newsModel != null) {
            newsModel.getNewsList(key, type, this);
        }

    }

    @Override
    public void onSuccess(List<NewsBean.ResultBean.DataBean> dataBeans) {
        if (newsView != null) {
            newsView.onSuccess(dataBeans);
        }
    }

    @Override
    public void onFail(String s) {
        if (newsView != null) {
            newsView.onFail(s);
        }
    }

    @Override
    public void onInsertSuccess(String string) {

    }

    @Override
    public void onInsertFail(String error) {

    }
}
