package com.anfly.bigfly.distance.utils;

import com.anfly.bigfly.common.BigFlyApplication;
import com.anfly.bigfly.dao.DaoMaster;
import com.anfly.bigfly.dao.DaoSession;
import com.anfly.bigfly.dao.NewsDbBeanDao;
import com.anfly.bigfly.distance.bean.NewsDbBean;

import java.util.List;

/**
 * @author Anfly
 * @date 2019/5/5
 * description：
 */
public class DbUtil {
    private static DbUtil dbUtil;
    private final NewsDbBeanDao newsDbBeanDao;

    public static DbUtil getDbUtil() {
        if (dbUtil == null) {
            synchronized (DbUtil.class) {
                if (dbUtil == null) {
                    dbUtil = new DbUtil();
                }
            }
        }
        return dbUtil;
    }

    public DbUtil() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BigFlyApplication.getApp(), "news.db");

        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        DaoSession daoSession = daoMaster.newSession();

        newsDbBeanDao = daoSession.getNewsDbBeanDao();
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public List<NewsDbBean> query() {
        return newsDbBeanDao.queryBuilder().list();
    }

    /**
     * 插入一条数据
     *
     * @param newsDbBean
     * @return
     */
    public long insert(NewsDbBean newsDbBean) {
        if (!isHash(newsDbBean)) {
            long l = newsDbBeanDao.insertOrReplace(newsDbBean);
            return l;
        }
        return -1;
    }

    /**
     * 删除一条数据
     *
     * @param newsDbBean
     * @return
     */
    public boolean delete(NewsDbBean newsDbBean) {
        if (isHash(newsDbBean)) {
            newsDbBeanDao.delete(newsDbBean);
            return true;
        }
        return false;
    }

    /**
     * 修改一条数据
     *
     * @param newsDbBean
     * @return
     */
    public boolean updata(NewsDbBean newsDbBean) {
        if (isHash(newsDbBean)) {
            newsDbBeanDao.update(newsDbBean);
            return true;
        }
        return false;
    }

    /**
     * 判断是否存在
     *
     * @param newsDbBean
     * @return
     */
    public boolean isHash(NewsDbBean newsDbBean) {
        List<NewsDbBean> list = newsDbBeanDao.queryBuilder().where(NewsDbBeanDao.Properties.Id.eq(newsDbBean.getId())).list();
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
