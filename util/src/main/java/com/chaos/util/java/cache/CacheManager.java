package com.chaos.util.java.cache;

import android.content.Context;
import android.os.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigDecimal;

import timber.log.Timber;

/**
 * 缓存管理器
 *
 * @author 郑少鹏
 * @date 2018/11/6
 */
public class CacheManager {
    /**
     * 全缓存大小
     *
     * @param context 上下文
     * @return 全缓存大小
     */
    public static @NotNull String totalCacheSize(@NotNull Context context) {
        long cacheSize = folderSize(context.getApplicationContext().getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += folderSize(context.getApplicationContext().getExternalCacheDir());
        }
        return formatSize(cacheSize);
    }

    /**
     * 清全缓存
     *
     * @param context 上下文
     */
    public static void clearAllCache(@NotNull Context context) {
        deleteDir(context.getApplicationContext().getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getApplicationContext().getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if ((null != dir) && dir.isDirectory()) {
            String[] children = dir.list();
            if (null != children) {
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        if (null != dir) {
            return dir.delete();
        } else {
            return false;
        }
    }

    /**
     * 目录大小
     * <p>
     * Context.getExternalFilesDir() --> SDCard/Android/data/应用包名/files/ 目录（通放长存数据）
     * Context.getExternalCacheDir() --> SDCard/Android/data/应用包名/cache/ 目录（通放临缓数据）
     *
     * @param file 文件
     * @return 目录大小
     */
    private static long folderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (null != fileList) {
                for (File aFileList : fileList) {
                    // 下面还有文件
                    size = (aFileList.isDirectory() ? (size + folderSize(aFileList)) : (size + aFileList.length()));
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return size;
    }

    /**
     * 格式化大小
     *
     * @param size 大小
     * @return 格式化大小
     */
    private static @NotNull String formatSize(double size) {
        double kiloByte = (size / 1024);
        if (kiloByte < 1) {
            return "0K";
        }
        double megaByte = (kiloByte / 1024);
        if (megaByte < 1) {
            BigDecimal result1 = BigDecimal.valueOf(kiloByte);
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = (megaByte / 1024);
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(megaByte);
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = (gigaByte / 1024);
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(gigaByte);
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}