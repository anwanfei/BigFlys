package com.anfly.bigfly.distance.model;

import com.anfly.bigfly.distance.callback.NewsCallback;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface NewsModel {
    void getNewsList(String key, String type, NewsCallback newsCallback);
}
