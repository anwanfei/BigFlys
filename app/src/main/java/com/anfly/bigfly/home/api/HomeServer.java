package com.anfly.bigfly.home.api;

import com.anfly.bigfly.home.bean.BannerBean;
import com.anfly.bigfly.home.bean.HomeListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Anfly
 * @date 2019/5/4
 * description：
 */
public interface HomeServer {
    String baseBannerUrl = "https://www.wanandroid.com/";
    String baseHomeListUrl = "https://www.wanandroid.com/";


    @GET("banner/json")
    Observable<BannerBean> getBanner();

    @GET("article/list/{page}/json")
    Observable<HomeListBean> getHomeList(@Path("page") String page);


}
