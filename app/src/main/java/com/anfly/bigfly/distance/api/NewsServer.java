package com.anfly.bigfly.distance.api;

import com.anfly.bigfly.distance.bean.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface NewsServer {
    @FormUrlEncoded
    @POST("index")
    Observable<NewsBean> getNews(@Field("key") String key, @Field("type") String type);
}
