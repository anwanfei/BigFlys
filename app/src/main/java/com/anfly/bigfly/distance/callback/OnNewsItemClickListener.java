package com.anfly.bigfly.distance.callback;

import com.anfly.bigfly.distance.bean.NewsBean;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface OnNewsItemClickListener {
    void onNewsItemClickListener(int position, NewsBean.ResultBean.DataBean dataBean);

    void onNewsItemLongClickListener(int position, NewsBean.ResultBean.DataBean dataBean);
}
