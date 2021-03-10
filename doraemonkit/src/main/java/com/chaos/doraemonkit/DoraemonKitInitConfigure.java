package com.chaos.doraemonkit;

import android.app.Application;

import com.chaos.util.java.process.ProcessUtils;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.AbstractKit;

import java.util.ArrayList;
import java.util.List;

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
     */
    public static void initDoraemonKit(Application application) {
        List<AbstractKit> abstractKitList = new ArrayList<>();
        DoraemonKit.install(application, abstractKitList, ProcessUtils.getProcessId());
        // H5 任意门功能需要（非必须）
        DoraemonKit.setWebDoorCallback((context, url) -> {
            // 自己 H5 容器打开该链接
            Timber.d(url);
        });
    }
}
