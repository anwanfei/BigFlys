package com.anfly.bigfly.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Anfly
 * @date 2019/4/18
 * description：
 */
public class ThreadPoolUtils {
    private static ThreadPoolUtils myThreadUtils;
    private final ThreadPoolExecutor executor;

    private ThreadPoolUtils() {
        executor = new ThreadPoolExecutor(5,//核心线程数量,核心池的大小
                20,//线程池最大线程数
                30,//表示线程没有任务执行时最多保持多久时间会终止
                TimeUnit.SECONDS,//时间单位
                new LinkedBlockingQueue<Runnable>(),//任务队列,用来存储等待执行的任务
                Executors.defaultThreadFactory(),//线程工厂,如何去创建线程的
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolUtils getThreadPoolUtils() {
        if (myThreadUtils == null) {
            synchronized (ThreadPoolUtils.class) {
                if (myThreadUtils == null) {
                    myThreadUtils = new ThreadPoolUtils();
                }
            }
        }
        return myThreadUtils;
    }

    public void excecute(Runnable runnable) {
        if (runnable != null) {
            executor.execute(runnable);
        }
    }

    public void remove(Runnable runnable) {
        if (runnable != null) {
            executor.remove(runnable);
        }
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
