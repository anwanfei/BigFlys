package com.anfly.bigfly.distance.callback;

import com.anfly.bigfly.distance.bean.NewsDbBean;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface OnCollectionItemClickListener {
    void onCollectionItemClickListener(int position, NewsDbBean newsDbBean);

    void onCollectionItemLongClickListener(int position, NewsDbBean newsDbBean);
}
