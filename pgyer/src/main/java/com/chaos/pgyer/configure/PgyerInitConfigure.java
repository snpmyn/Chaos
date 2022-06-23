package com.chaos.pgyer.configure;

import android.app.Application;

import com.pgyer.pgyersdk.PgyerSDKManager;
import com.pgyer.pgyersdk.pgyerenum.Features;

/**
 * Created on 2022/6/22
 *
 * @author zsp
 * @desc Pgyer 初始化配置
 */
public class PgyerInitConfigure {
    /**
     * 初始化 Pgyer
     *
     * @param application  应用
     * @param frontJsToken String
     * @param apiKey       String
     * @param features     特征
     *                     {@link Features#CHECK_UPDATE} 版本自动检测（不设默关）
     *                     {@link Features#APP_LAUNCH_TIME} 启动时间统计
     *                     {@link Features#APP_PAGE_CATON} 页面卡顿统计
     */
    public static void initPgyer(Application application, String frontJsToken, String apiKey, Features features) {
        new PgyerSDKManager.Init()
                .setContext(application)
                .setFrontJSToken(frontJsToken)
                .setApiKey(apiKey)
                .enable(features)
                .start();
    }
}
