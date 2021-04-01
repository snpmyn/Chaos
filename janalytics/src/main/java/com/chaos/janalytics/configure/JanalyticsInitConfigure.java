package com.chaos.janalytics.configure;

import android.app.Application;

import com.chaos.janalytics.kit.JanalyticsKit;

/**
 * Created on 2019/9/4.
 *
 * @author 郑少鹏
 * @desc 极光统计初始化配置
 */
public class JanalyticsInitConfigure {
    /**
     * 初始化极光统计
     *
     * @param application 应用
     * @param debug       调试否
     */
    public static void initJanalytics(Application application, boolean debug) {
        // 调试模式
        JanalyticsKit.setDebugMode(debug);
        // 初始
        JanalyticsKit.init(application);
        // 开 CrashLog 日志上报
        JanalyticsKit.initCrashHandler(application);
    }
}
