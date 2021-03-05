package com.chaos.fairy.widget.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.chaos.fairy.utils.log.LogUtils;
import com.chaos.fairy.value.FairyMagic;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2020-09-09
 *
 * @author zsp
 * @desc 普通吐司
 */
public class CommonToast extends Toast {
    private final static String TAG = "CommonToast";

    private CommonToast(Context context) {
        super(context);
    }

    public static @NotNull CommonToast makeText(Context context, CharSequence charSequence, int duration) throws Resources.NotFoundException {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(context, charSequence, duration);
        // 通知权限未开启用 ContextWrapper
        if (!areNotificationEnabled(context)) {
            context = new MyContextWrapper(context);
        }
        CommonToast commonToast = new CommonToast(context);
        commonToast.setDuration(toast.getDuration());
        commonToast.setView(toast.getView());
        return commonToast;
    }

    private static boolean areNotificationEnabled(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();
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
            if (stackTraceElements[FairyMagic.INT_THREE].getClassName().equals(Toast.class.getName()) && FairyMagic.STRING_SHOW.equals(stackTraceElements[FairyMagic.INT_THREE].getMethodName())) {
                return "android";
            }
            return getPackageName();
        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            LogUtils.e(TAG, " toast 显示异常 ", e);
        }
    }
}


