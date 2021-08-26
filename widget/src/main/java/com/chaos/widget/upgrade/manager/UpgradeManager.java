package com.chaos.widget.upgrade.manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.chaos.util.java.file.FileUtils;
import com.chaos.widget.upgrade.engine.UpgradeEngine;
import com.chaos.widget.upgrade.event.DownloadProgressEvent;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import value.WidgetMagic;

import static com.chaos.widget.upgrade.manager.AppManager.APPLICATION;

/**
 * @decs: 更新管理器
 * @author: 郑少鹏
 * @date: 2018/8/22 9:01
 */
public class UpgradeManager {
    private static final String MIME_TYPE_APK = "application/vnd.android.package-archive";

    private UpgradeManager() {

    }

    /**
     * 监听下载进度
     *
     * @return Observable<DownloadProgressEvent>
     */
    public static Observable<DownloadProgressEvent> getDownloadProgressEventObservable() {
        return RxManager.getDownloadEventObservable();
    }

    /**
     * APK 已下否（下直装）
     *
     * @param applicationId 应用 ID
     * @param path          路径
     * @param version       新 APK 版本
     * @return 下否
     */
    public static boolean isApkDownloaded(String applicationId, String path, String version) {
        File apkFile = StorageManager.getApk(path, version);
        if (apkFile.exists()) {
            installApk(applicationId, apkFile);
            return true;
        }
        return false;
    }

    /**
     * 下新版 APK
     *
     * @param url     路径
     * @param path    路径
     * @param version 新 APK 版本
     * @return Observable<File>
     */
    public static Observable<File> downloadApkFile(final String url, String path, final String version) {
        return Observable.defer(() -> {
            try {
                return Observable.just(Objects.requireNonNull(UpgradeEngine.getInstance().getDownloadApi().downloadFile(url).execute().body(), "must not be null").byteStream());
            } catch (Exception e) {
                return Observable.error(e);
            }
        }).map(inputStream -> StorageManager.saveApk(inputStream, path, version)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 装 APK
     *
     * @param applicationId 应用 ID
     * @param apkFile       APK 文件
     */
    @SuppressLint("QueryPermissionsNeeded")
    public static void installApk(String applicationId, File apkFile) {
        Intent installApkIntent = new Intent();
        installApkIntent.setAction(Intent.ACTION_VIEW);
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(APPLICATION, applicationId, apkFile), MIME_TYPE_APK);
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), MIME_TYPE_APK);
        }
        if (APPLICATION.getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            APPLICATION.startActivity(installApkIntent);
        }
    }

    /**
     * FileProvider 之 auth
     *
     * @return authority
     */
    private static @Nullable String getFileProviderAuthority() {
        try {
            for (ProviderInfo provider : APPLICATION.getPackageManager().getPackageInfo(APPLICATION.getPackageName(), PackageManager.GET_PROVIDERS).providers) {
                if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith(".bga_update.file_provider")) {
                    return provider.authority;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
        }
        return null;
    }

    /**
     * 删前更新下老 APK
     *
     * @param path 路径
     */
    public static void deleteOldApk(String path) {
        // 外部存储删
        File apkDir = new File(path);
        if ((null == apkDir.listFiles()) || (Objects.requireNonNull(apkDir.listFiles(), WidgetMagic.STRING_OBJECTS_REQUIRE_NON_NULL_MESSAGE).length == 0)) {
            return;
        }
        FileUtils.deleteFile(apkDir);
        // 内部存储删
        /*File apkDir = StorageManager.getApkDir();*/
        /*if ( (null==apkDir) ||  (null==apkDir.listFiles()) || (apkDir.listFiles().length == 0)) {*/
        /*return;*/
        /*}*/
        /*FileUtils.deleteFile(apkDir);*/
    }
}
