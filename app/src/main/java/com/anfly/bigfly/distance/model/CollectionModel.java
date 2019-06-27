package com.anfly.bigfly.distance.model;

import com.anfly.bigfly.distance.callback.NewsDbCallback;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface CollectionModel {
    void getNewsList(NewsDbCallback newsDbCallback);
}
