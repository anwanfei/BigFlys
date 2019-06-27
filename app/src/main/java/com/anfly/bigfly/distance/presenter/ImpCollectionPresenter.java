package com.anfly.bigfly.distance.presenter;

import com.anfly.bigfly.distance.bean.NewsDbBean;
import com.anfly.bigfly.distance.callback.NewsDbCallback;
import com.anfly.bigfly.distance.model.CollectionModel;
import com.anfly.bigfly.distance.view.CollectionView;

import java.util.List;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class ImpCollectionPresenter implements CollectionPresenter, NewsDbCallback<List<NewsDbBean>, String> {

    private CollectionModel collectionModel;
    private CollectionView collectionView;

    public ImpCollectionPresenter(CollectionModel collectionModel, CollectionView collectionView) {
        this.collectionModel = collectionModel;
        this.collectionView = collectionView;
    }

    @Override
    public void getNewsList() {
        if (collectionModel != null) {
            collectionModel.getNewsList(this);
        }
    }

    @Override
    public void onSuccess(List<NewsDbBean> newsDbBeans) {
        if (collectionView != null) {
            collectionView.onSuccess(newsDbBeans);
        }
    }

    @Override
    public void onFail(String s) {
        if (collectionView != null) {
            collectionView.onFail(s);
        }
    }
}
