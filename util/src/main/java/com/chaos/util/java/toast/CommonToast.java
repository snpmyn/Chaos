package com.chaos.util.java.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;
import value.UtilMagic;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc 普通吐司
 */
public class CommonToast extends Toast {
    private CommonToast(Context context) {
        super(context);
    }

    public static @NotNull CommonToast makeText(Context context, CharSequence charSequence, int duration) throws Resources.NotFoundException {
        // 通知权限未开启用 ContextWrapper
        if (!areNotificationEnabled(context)) {
            context = new MyContextWrapper(context);
        }
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, charSequence, duration);
        return (CommonToast) toast;
    }

    private static boolean areNotificationEnabled(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private static class MyContextWrapper extends ContextWrapper {
        public MyContextWrapper(Context base) {
            super(base);
        }

        @Override
        public String getPackageName() {
            return super.getPackageName();
        }

        @Override
        public String getOpPackageName() {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            if (stackTraceElements[UtilMagic.INT_THREE].getClassName().equals(Toast.class.getName()) && UtilMagic.STRING_SHOW.equals(stackTraceElements[UtilMagic.INT_THREE].getMethodName())) {
                return "android";
            }
            return getPackageName();
        }
    }
}


