package com.chaos.jpush.kit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;

import com.chaos.jpush.value.JpushMagic;
import com.chaos.util.java.thread.ThreadManager;
import com.chaos.util.java.toast.ToastKit;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

/**
 * @decs: ExampleKit
 * @author: 郑少鹏
 * @date: 2019/5/31 15:05
 */
public class ExampleKit {
    private static final String JPUSH_APP_KEY = "JPUSH_APP_KEY";
    /**
     * "+" 或数字开头
     * 后面内容仅含 "-" 和数字
     */
    private final static String MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]+$";
    /**
     * Pattern
     */
    private static final Pattern P1 = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
    private static final Pattern P2 = Pattern.compile("[\\x20-\\x7E]+");

    /**
     * 手机号有效否
     *
     * @param string 字符串
     * @return 手机号有效否
     */
    public static boolean areValidMobileNumber(String string) {
        if (TextUtils.isEmpty(string)) {
            return true;
        }
        Pattern pattern = Pattern.compile(MOBILE_NUMBER_CHARS);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Tag 和 Alias 有效否
     * <p>
     * Tag 和 Alias 仅数字、英文和中文。
     *
     * @param string 字符串
     * @return Tag 和 Alias 有效否
     */
    public static boolean areValidTagAndAlias(String string) {
        Matcher matcher = P1.matcher(string);
        return matcher.matches();
    }

    /**
     * AppKey
     *
     * @param context 上下文
     * @return AppKey
     */
    public static String appKey(@NotNull Context context) {
        Bundle metaData;
        String appKey = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            metaData = applicationInfo.metaData;
            if (null != metaData) {
                appKey = metaData.getString(JPUSH_APP_KEY);
                if ((null == appKey) || (appKey.length() != JpushMagic.INT_TWENTY_FOUR)) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {
            Timber.e(e);
        }
        return appKey;
    }

    static void showToast(final String toast) {
        ThreadManager.stepScheduledExecutorService().execute(() -> {
            if (null == Looper.myLooper()) {
                Looper.prepare();
            }
            ToastKit.showShort(toast);
            Looper.loop();
        });
    }

    static boolean areConnected(@NotNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((null != networkInfo) && networkInfo.isConnected());
    }

    private static boolean areReadableAscii(CharSequence string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        try {
            return P2.matcher(string).matches();
        } catch (Throwable e) {
            return true;
        }
    }

    public static String getDeviceId(Context context) {
        return JPushInterface.getUdid(context);
    }
}
