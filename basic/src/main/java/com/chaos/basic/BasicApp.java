package com.chaos.basic;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

/**
 * Created on 2021/3/5
 *
 * @author zsp
 * @desc 应用
 * 官方：
 * Base class for those who need to maintain global application state.
 * You can provide your own implementation by specifying its name in your AndroidManifest.xml's <application> tag,
 * which will cause that class to be instantiated for you when the process for your application/package is created.
 * Application 类（基础类）用于维护应用程序全局状态。
 * 你可提供自己的实现，在 AndroidManifest.xml 文件 <application> 标签指定它的名字，
 * 这将引起你的应用进程被创建时 Application 类为你被实例化。
 * <p>
 * Android 系统在每应用程序运行时仅创一 Application 实例，故 Application 可作单例（Singleton）模式一类；
 * 对象生命周期整应用程序最长，等同应用程序生命周期；
 * 全局唯一，不同 Activity、Service 中获实例相同；
 * 数据传递、数据共享、数据缓存等。
 */
public class BasicApp extends Application {
    private static Application instance;

    /**
     * 应用程序创调
     * <p>
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获 Application 单例。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Application 本已单例
        instance = this;
    }

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     * <p>
     * 应用程序对象终止调
     * <p>
     * 不定调。应用程序被内核终止为别应用程序释放资源，将不提醒且不调应用程序对象 onTerminate() 而直接终止进程。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 系统资源匮乏调
     * <p>
     * 通于后台进程已结束且前台应用程序仍缺内存时调，重写该法清缓存或释放非必要资源。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 运行时决定当前应用程序应减内存开销时（通进后台运行）调，含一 level 参数提供请求上下文。
     *
     * @param level 级别
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 与 Activity 不同，配置变时应用程序对象不终止和重启。应用程序用值依赖特定配置则重写该法加载这些值或于应用程序级处理配置值改变。
     *
     * @param newConfig 配置
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static Application getInstance() {
        return instance;
    }
}

