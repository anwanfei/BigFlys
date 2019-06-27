package com.anfly.bigfly.distance.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.common.MainActivity;

/**
 * @author Anfly
 * @date 2019/5/22
 * description：
 */
public class DownLoadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String download = intent.getStringExtra("download");
        Toast.makeText(context, download, Toast.LENGTH_SHORT).show();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "1";
        String channelName = "download";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//开启指示灯,如果设备有的话。
            channel.setLightColor(Color.RED);//设置指示灯颜色
            channel.setShowBadge(true);//检测是否显示角标
            nm.createNotificationChannel(channel);

            PendingIntent activity = PendingIntent.getActivity(context, 100, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Notification build = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)//设置小图
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_round))//设置大图
                    .setContentText(download)//设置内容
                    .setContentTitle("下载提示")//设置标题
                    .setContentIntent(activity)//设置延时意图
                    .setDefaults(Notification.DEFAULT_ALL)//设置提示效果
                    .setBadgeIconType(R.mipmap.ic_launcher)//设置设置角标样式
                    .setAutoCancel(true)//设置点击自动消失
                    .setNumber(3)//设置角标计数
                    .build();

            nm.notify(100, build);
        }
    }
}
