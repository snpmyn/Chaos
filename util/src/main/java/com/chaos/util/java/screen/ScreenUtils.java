package com.chaos.util.java.screen;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created on 2019/3/6.
 *
 * @author 郑少鹏
 * @desc 屏幕工具类
 */
public class ScreenUtils {
    private static WindowManager windowManager;

    /**
     * 窗口管理器
     *
     * @param context 上下文
     * @return 窗口管理器
     */
    private static WindowManager getWindowManager(Context context) {
        if (null == windowManager) {
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * 屏宽（像素）
     *
     * @param context 上下文
     * @return 屏宽（像素）
     */
    public static int screenWidth(@NotNull Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 屏高（像素）
     *
     * @param context 上下文
     * @return 屏高（像素）
     */
    public static int screenHeight(@NotNull Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * Setting window background alpha.
     *
     * @param appCompatActivity 活动
     * @param alpha             透明度
     */
    public static void setWindowBackgroundAlpha(AppCompatActivity appCompatActivity, float alpha) {
        WeakReference<Activity> weakReference = new WeakReference<>(appCompatActivity);
        Window window = weakReference.get().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

    /**
     * 隐藏导航栏且滑动中可显示
     *
     * @param appCompatActivity 活动
     */
    public static void hideNavigationWithCanShowInScroll(@NotNull AppCompatActivity appCompatActivity) {
        View decorView = appCompatActivity.getWindow().getDecorView();
        int visibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(visibility);
    }

    /**
     * 隐藏导航栏且滑动中不可显示
     *
     * @param appCompatActivity 活动
     */
    public static void hideNavigationWithoutCanShowInScroll(AppCompatActivity appCompatActivity) {
        WeakReference<Activity> weakReference = new WeakReference<>(appCompatActivity);
        Window window = weakReference.get().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(layoutParams);
    }
}