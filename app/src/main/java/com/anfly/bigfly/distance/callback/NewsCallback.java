package com.anfly.bigfly.distance.callback;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface NewsCallback<T, V> {
    void onSuccess(T t);

    void onFail(V v);

    void onInsertSuccess(String string);

    void onInsertFail(String error);
}
