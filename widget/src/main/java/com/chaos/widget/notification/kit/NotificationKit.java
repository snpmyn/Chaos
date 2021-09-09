package com.chaos.widget.notification.kit;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

/**
 * Created on 2019/8/8.
 *
 * @author 郑少鹏
 * @desc NotificationKit
 */
public class NotificationKit {
    /**
     * 通知允
     *
     * @param context 上下文
     * @return 通知允
     */
    public boolean notificationEnable(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // areNotificationsEnabled 于 API 19+ 有效
        // API 19- 返 true（默开）
        return manager.areNotificationsEnabled();
    }

    /**
     * 设置通知
     *
     * @param context 上下文
     */
    public void setNotification(Context context) {
        Intent intent = new Intent();
        // 跳通知设置页
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
        context.startActivity(intent);
    }
}
