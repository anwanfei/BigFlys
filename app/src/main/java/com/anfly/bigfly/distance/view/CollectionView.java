package com.anfly.bigfly.distance.view;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface CollectionView<T, V> {
    void onSuccess(T t);

    void onFail(V v);

}
