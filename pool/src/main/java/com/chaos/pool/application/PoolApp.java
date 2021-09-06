package com.chaos.pool.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.chaos.basic.BasicApp;
import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.chaos.util.java.listener.AppListener;
import com.chaos.util.java.log.LogUtils;

import timber.log.Timber;

/**
 * Created on 2021/9/3
 *
 * @author zsp
 * @desc 应用
 */
public class PoolApp extends BasicApp {
    /**
     * 应用程序创调
     * <p>
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获 Application 单例。
     */
    @Override
    public void onCreate() {
        Timber.d("onCreate");
        super.onCreate();
        // 初始化配置
        initConfiguration();
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // 日志工具类
        LogUtils.Builder.initConfiguration(true, true, true, true);
        // 应用监听
        AppListener.getInstance().initConfiguration(this);
        // 全局监听 Activity 生命周期
        registerActivityListener();
    }

    /**
     * Activity 全局监听
     */
    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                // 添监听到创事件 Activity 至集合
                ActivitySuperviseManager.getInstance().pushActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                // 移监听到销事件 Activity 出集合
                ActivitySuperviseManager.getInstance().removeActivity(activity);
            }
        });
    }
}

