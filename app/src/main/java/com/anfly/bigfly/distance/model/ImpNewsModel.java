package com.anfly.bigfly.distance.model;

import com.anfly.bigfly.distance.api.NewsServer;
import com.anfly.bigfly.distance.bean.NewsBean;
import com.anfly.bigfly.distance.callback.NewsCallback;
import com.anfly.bigfly.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class ImpNewsModel implements NewsModel {
    @Override
    public void getNewsList(String key, String type, final NewsCallback newsCallback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.NEWS_BASE_URL)
                .build();

        NewsServer newsServer = retrofit.create(NewsServer.class);

        Observable<NewsBean> observable = newsServer.getNews(key, type);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (newsBean != null) {
                            if (newsBean.getError_code() == 0) {
                                newsCallback.onSuccess(newsBean.getResult().getData());
                            } else {
                                newsCallback.onFail("请求失败");
                            }
                        } else {
                            newsCallback.onFail("请求数据为空");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsCallback.onFail("网络失败：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
