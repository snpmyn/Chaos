package com.chaos.util.java.timber.configure;

import com.chaos.util.java.timber.debugtree.ReleaseTree;
import com.chaos.util.java.timber.debugtree.ThreadAwareDebugTree;

import timber.log.Timber;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc timber 初始化配置
 */
public class TimberInitConfigure {
    /**
     * 初始化 timber
     *
     * @param debug 调试模式
     */
    public static void initTimber(boolean debug) {
        if (debug) {
            Timber.plant(new ThreadAwareDebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
