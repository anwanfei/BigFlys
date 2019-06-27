package com.anfly.bigfly.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Anfly
 * @date 2019/5/8
 * description：
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
//            Toast.makeText(context, "网络连接成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
//        }
    }
}
