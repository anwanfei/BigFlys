package com.anfly.bigfly.common;

import android.app.Application;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class BigFlyApplication extends Application {
    private static BigFlyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static BigFlyApplication getApp() {
        return app;
    }
}
