package com.anfly.bigfly.distance.model;

import com.anfly.bigfly.distance.bean.NewsDbBean;
import com.anfly.bigfly.distance.callback.NewsDbCallback;
import com.anfly.bigfly.distance.utils.DbUtil;
import com.anfly.bigfly.utils.ThreadPoolUtils;

import java.util.List;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class ImpCollectionModel implements CollectionModel {

    @Override
    public void getNewsList(final NewsDbCallback newsDbCallback) {
        ThreadPoolUtils.getThreadPoolUtils().excecute(new Runnable() {
            @Override
            public void run() {
                List<NewsDbBean> dbBeanList = DbUtil.getDbUtil().query();

                if (dbBeanList != null && dbBeanList.size() > 0) {
                    newsDbCallback.onSuccess(dbBeanList);
                } else {
                    newsDbCallback.onFail("数据库查询失败");
                }
            }
        });
    }
}
