package com.chaos.util.java.storage.mmkv;

import android.app.Application;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVContentChangeNotification;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/**
 * Created on 2019/9/9.
 *
 * @author 郑少鹏
 * @desc MMKV 初始化配置
 */
public class MmkvInitConfigure {
    /**
     * 初始 MMKV
     *
     * @param application                   应用
     * @param debug                         调试模式
     * @param mmkvHandler                   MMKV 处理
     * @param mmkvContentChangeNotification MMKV 内容改变通知
     */
    public static void initMmkv(@NotNull Application application, boolean debug, MMKVHandler mmkvHandler, MMKVContentChangeNotification mmkvContentChangeNotification) {
        String dir = application.getFilesDir().getAbsolutePath() + "/mmkv";
        String rootDir = MMKV.initialize(application, dir, libName -> ReLinker.loadLibrary(application, libName), MMKVLogLevel.LevelInfo);
        Timber.d("mmkv root: %s", rootDir);
        MMKV.setLogLevel(debug ? MMKVLogLevel.LevelInfo : MMKVLogLevel.LevelNone);
        MMKV.registerHandler(mmkvHandler);
        MMKV.registerContentChangeNotify(mmkvContentChangeNotification);
    }

    /**
     * 退出
     * <p>
     * 官方示例于 Activity之onDestroy() 调。
     */
    public static void exit() {
        MMKV.onExit();
    }
}
