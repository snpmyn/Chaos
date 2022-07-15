package com.chaos.util.java.navigationbar;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chaos.util.R;
import com.chaos.util.java.activity.ActivitySuperviseManager;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import timber.log.Timber;
import value.UtilMagic;

/**
 * Created on 2022/7/8
 *
 * @author zsp
 * @desc 导航栏工具类
 */
public class NavigationBarUtils {
    private static final Set<String> NO_NAVIGATION_BAR_MODEL_SET = new HashSet<>();

    static {
        NO_NAVIGATION_BAR_MODEL_SET.add("Nexus 4");
        NO_NAVIGATION_BAR_MODEL_SET.add("H60-L01");
        NO_NAVIGATION_BAR_MODEL_SET.add("P7-L07");
        NO_NAVIGATION_BAR_MODEL_SET.add("MT7-UL00");
    }

    /**
     * 基础活动设导航栏色
     *
     * @param appCompatActivity 活动
     */
    public static void setNavigationBarColorInBaseActivity(AppCompatActivity appCompatActivity) {
        String splashActivityName = "com.chaos.pool.module.splash.SplashActivity";
        String splashTwoActivityName = "com.chaos.pool.module.splash.SplashTwoActivity";
        boolean flag = !TextUtils.equals(splashActivityName, ActivitySuperviseManager.getInstance().getCurrentRunningActivityName(appCompatActivity)) &&
                !TextUtils.equals(splashTwoActivityName, ActivitySuperviseManager.getInstance().getCurrentRunningActivityName(appCompatActivity));
        if (flag) {
            NavigationBarUtils.setNavigationBarColor(appCompatActivity, ContextCompat.getColor(appCompatActivity, R.color.navigationBarBackground));
        }
    }

    /**
     * 设导航栏色
     *
     * @param appCompatActivity 活动
     * @param color             色
     */
    public static void setNavigationBarColor(@NonNull AppCompatActivity appCompatActivity, int color) {
        appCompatActivity.getWindow().setNavigationBarColor(color);
    }

    /**
     * 获底部导航栏高
     *
     * @param appCompatActivity 活动
     * @return 底部导航栏高
     */
    public static int getNavigationBarHeight(@NonNull AppCompatActivity appCompatActivity) {
        int navigationBarHeight = 0;
        Resources resources = appCompatActivity.getResources();
        int resourceId = resources.getIdentifier(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(appCompatActivity)) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    /**
     * 检测有无底部导航栏
     *
     * @param appCompatActivity 活动
     * @return 有无底部导航栏
     */
    public static boolean checkDeviceHasNavigationBar(AppCompatActivity appCompatActivity) {
        boolean hasNavigationBar;
        if (NO_NAVIGATION_BAR_MODEL_SET.contains(Build.MODEL)) {
            hasNavigationBar = false;
        } else {
            hasNavigationBar = (oldCheckDeviceHasNavigationBar(appCompatActivity) || newCheckDeviceHasNavigationBar(appCompatActivity));
        }
        return hasNavigationBar;
    }

    /**
     * 旧检测有无底部导航栏
     *
     * @param appCompatActivity 活动
     * @return 有无底部导航栏
     */
    private static boolean oldCheckDeviceHasNavigationBar(@NonNull AppCompatActivity appCompatActivity) {
        boolean hasNavigationBar = false;
        Resources resources = appCompatActivity.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            @SuppressLint("PrivateApi") Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if (UtilMagic.STRING_ZERO.equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if (UtilMagic.STRING_ONE.equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return hasNavigationBar;
    }

    /**
     * 新检测有无底部导航栏
     *
     * @param appCompatActivity 活动
     * @return 有无底部导航栏
     */
    private static boolean newCheckDeviceHasNavigationBar(@NonNull AppCompatActivity appCompatActivity) {
        WindowManager windowManager = appCompatActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
