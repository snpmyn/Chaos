package com.chaos.doraemonkit;

import android.app.Application;

import com.didichuxing.doraemonkit.DoKit;

import timber.log.Timber;

/**
 * Created on 2019/5/6.
 *
 * @author 郑少鹏
 * @desc DoraemonKit 初始化配置
 */
public class DoraemonKitInitConfigure {
    /**
     * 初始化 DoraemonKit
     *
     * @param application 应用
     * @param productId   产品 ID
     *                    Dokit 平台端申请
     * @param debug       调试否
     * @param alwaysShow  显主入口 icon 否
     */
    public static void initDoraemonKit(Application application, String productId, Boolean debug, Boolean alwaysShow) {
        new DoKit.Builder(application)
                .productId(productId)
                .webDoorCallback((context, url) -> {
                    // 自己 H5 容器打开该链接
                    Timber.d(url);
                })
                .debug(debug)
                .alwaysShowMainIcon(alwaysShow)
                .build();
    }
}
