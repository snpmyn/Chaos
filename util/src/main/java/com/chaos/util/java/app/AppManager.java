package com.chaos.util.java.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import timber.log.Timber;

/**
 * Created on 2019/4/19.
 *
 * @author 郑少鹏
 * @desc 应用管理器
 */
public class AppManager {
    /**
     * 版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int versionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            Timber.e(e);
        }
        return versionCode;
    }

    /**
     * 版本名
     *
     * @param context 上下文
     * @return 版本名
     */
    public static String versionName(Context context) {
        String versionName = null;
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            Timber.e(e);
        }
        return versionName;
    }

    /**
     * 包信息
     * <p>
     * 据包名获。
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 包信息
     */
    @SuppressLint("WrongConstant")
    public static PackageInfo packageInfo(Context context, String packageName) {
        if (null == context) {
            return null;
        }
        if (TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
        }
        return packageInfo;
    }
}
