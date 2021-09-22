package com.chaos.litepool.application;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.chaos.basic.BaseApp;
import com.chaos.litepool.R;
import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.chaos.util.java.listener.AppListener;
import com.chaos.util.java.log.LogUtils;
import com.chaos.util.java.storage.mmkv.MmkvInitConfigure;
import com.chaos.widget.status.manager.StatusManager;
import com.tencent.mmkv.MMKVContentChangeNotification;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created on 2021/9/3
 *
 * @author zsp
 * @desc 应用
 */
public class LitePoolApp extends BaseApp implements MMKVHandler, MMKVContentChangeNotification {
    /**
     * 应用程序创调
     * <p>
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获 Application 单例。
     */
    @Override
    public void onCreate() {
        Timber.d("onCreate");
        super.onCreate();
        StatusManager.BASE_LOADING_LAYOUT_ID = R.layout.status_loading_two;
        StatusManager.BASE_EMPTY_LAYOUT_ID = R.layout.status_empty;
        StatusManager.BASE_RETRY_LAYOUT_ID = R.layout.status_retry;
        // 初始化配置
        initConfiguration();
    }

    /**
     * 单例
     * <p>
     * Application 本已单例。
     *
     * @return 单例
     */
    @Override
    protected Application instance() {
        return this;
    }

    /**
     * 调试否
     *
     * @return 调试否
     */
    @Override
    protected Boolean debug() {
        return true;
    }

    /**
     * 集成 MobSms 否
     *
     * @return 集成 MobSms 否
     */
    @Override
    protected Boolean ensembleMobSms() {
        return false;
    }

    /**
     * 权限集
     * <p>
     * 默动获 Manifest.permission.READ_EXTERNAL_STORAGE。
     *
     * @return 权限集
     */
    @Override
    protected List<String> permissionList() {
        List<String> list = new ArrayList<>(1);
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        return list;
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // 日志工具类
        LogUtils.Builder.initConfiguration(true, true, true, true);
        // MMKV
        MmkvInitConfigure.initMmkv(this, debug(), this, this);
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

    @Override
    public void onContentChangedByOuterProcess(String mmapID) {
        Timber.i("[content changed] %s", mmapID);
    }

    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public boolean wantLogRedirecting() {
        return true;
    }

    @Override
    public void mmkvLog(@NonNull MMKVLogLevel level, String file, int line, String function, String message) {
        String log = ("< " + file + " : " + line + " :: " + function + " > " + message);
        switch (level) {
            case LevelDebug:
                Timber.d("[redirect logging MMKV] %s", log);
                break;
            case LevelInfo:
                Timber.i("[redirect logging MMKV] %s", log);
                break;
            case LevelWarning:
                Timber.w("[redirect logging MMKV] %s", log);
                break;
            case LevelError:
            case LevelNone:
                Timber.e("[redirect logging MMKV] %s", log);
                break;
            default:
                break;
        }
    }
}

