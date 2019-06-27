package com.anfly.bigfly.distance.callback;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface NewsDbCallback<T, V> {
    void onSuccess(T t);

    void onFail(V v);
}
