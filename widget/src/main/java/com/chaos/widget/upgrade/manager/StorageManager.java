package com.chaos.widget.upgrade.manager;

import com.chaos.util.java.file.FileUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;

import static com.chaos.widget.upgrade.manager.AppManager.APPLICATION;

/**
 * @decs: 存储管理器
 * @author: 郑少鹏
 * @date: 2018/8/22 8:57
 */
class StorageManager {
    private static final String DIR_NAME = "upgrade_apk";

    private StorageManager() {

    }

    /**
     * APK 目录
     *
     * @return 目录
     */
    static File getApkDir() {
        return APPLICATION.getExternalFilesDir(DIR_NAME);
    }

    /**
     * 存 APK
     *
     * @param inputStream 输入流
     * @param path        路径
     * @param version     版本
     * @return 文件
     */
    static @Nullable File saveApk(InputStream inputStream, String path, String version) {
        File file = getApk(path, version);
        if (FileUtils.writeFile(file, inputStream)) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * APK
     *
     * @param path    路径
     * @param version 版本
     * @return APK
     */
    static @NotNull File getApk(String path, String version) {
        String appName;
        try {
            appName = APPLICATION.getPackageManager().getPackageInfo(APPLICATION.getPackageName(), 0).applicationInfo.loadLabel(APPLICATION.getPackageManager()).toString();
        } catch (Exception e) {
            // 系统 API 之 getPackageName() 获包名（该异常不会发生）
            appName = "";
        }
        // 内部存储
        /*return new File(getApkDir(), appName + "_v" + version + ".apk");*/
        // 外部存储
        File file = new File(path + appName + "_v" + version + ".apk");
        if ((null != file.getParentFile()) && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}
