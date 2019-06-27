package com.anfly.bigfly.home.callback;

import com.anfly.bigfly.home.bean.HomeListBean;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public interface OnHomeItemClickListener {
    void onHomeItemClickListener(int position, HomeListBean.DataBean.DatasBean dataBean);
}
