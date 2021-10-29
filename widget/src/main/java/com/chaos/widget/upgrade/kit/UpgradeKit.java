package com.chaos.widget.upgrade.kit;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.chaos.util.java.app.AppManager;
import com.chaos.util.java.data.StringUtils;
import com.chaos.util.java.storage.mmkv.MmkvKit;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.R;
import com.chaos.widget.dialog.materialalertdialog.MyMaterialAlertDialogBuilder;
import com.chaos.widget.upgrade.bean.UpgradeBean;
import com.chaos.widget.upgrade.dialog.DownloadProgressDialog;
import com.chaos.widget.upgrade.listener.UpgradeListener;
import com.chaos.widget.upgrade.manager.UpgradeManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;

import rx.Subscriber;
import value.WidgetConstant;

/**
 * Created on 2018/8/22.
 *
 * @author 郑少鹏
 * @desc 更新配套元件
 */
public class UpgradeKit {
    /**
     * 弱引用
     */
    private final WeakReference<Context> weakReference;
    /**
     * 服务器版本名
     */
    private String serverVersionName;
    /**
     * 下载进度框
     */
    private DownloadProgressDialog downloadProgressDialog;

    /**
     * constructor
     *
     * @param context 上下文
     */
    public UpgradeKit(Context context) {
        this.weakReference = new WeakReference<>(context);
    }

    /**
     * 更新
     *
     * @param applicationId   应用 ID
     * @param path            路径
     * @param upgradeBean     更新
     * @param upgradeListener 更新监听
     * @param restart         重启否
     * @param noUpgradeHint   无更新提示否
     */
    public void upgrade(String applicationId, String path, final @NotNull UpgradeBean upgradeBean, UpgradeListener upgradeListener, boolean restart, boolean noUpgradeHint) {
        final int newVersionCode = upgradeBean.getNewVersionCode();
        String noRemindAnyMoreVersionCode = MmkvKit.defaultMmkv().decodeString(WidgetConstant.NO_REMIND_ANY_MORE);
        // 场景一：无不再提示
        // 场景二：有不再提示且不再提示版不等当前服务器最新版
        // 场景三：重启
        if (StringUtils.areEmpty(noRemindAnyMoreVersionCode) || (Integer.parseInt(noRemindAnyMoreVersionCode) != newVersionCode) || restart) {
            judge(applicationId, path, upgradeBean, upgradeListener, noUpgradeHint);
        }
    }

    /**
     * 判断
     * <p>
     * 场景一：有更新且强制更新。
     * 场景二：有更新且选择更新。
     *
     * @param applicationId   应用 ID
     * @param path            路径
     * @param upgradeBean     更新
     * @param upgradeListener 更新监听
     * @param noUpgradeHint   无更新提示
     */
    private void judge(String applicationId, String path, final @NotNull UpgradeBean upgradeBean, UpgradeListener upgradeListener, boolean noUpgradeHint) {
        int newVersionCode = upgradeBean.getNewVersionCode();
        if (AppManager.versionCode(weakReference.get()) < newVersionCode) {
            CharSequence charSequence;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                charSequence = Html.fromHtml(upgradeBean.getUpdateDescription(), Html.FROM_HTML_MODE_LEGACY);
            } else {
                charSequence = Html.fromHtml(upgradeBean.getUpdateDescription());
            }
            if (upgradeBean.isForceUpdate()) {
                // 强制
                new MyMaterialAlertDialogBuilder(weakReference.get())
                        .setTitle(String.format(weakReference.get().getString(R.string.formatFindNewVersion), upgradeBean.getNewVersionName()))
                        .setMessage(charSequence)
                        .setPositiveButton(weakReference.get().getString(R.string.upgradeNow), (dialog, which) -> {
                            dialog.dismiss();
                            if (upgradeBean.isInnerUpgrade()) {
                                execute(applicationId, path, upgradeBean.getDownloadUrl());
                            } else {
                                upgradeListener.outUpgrade();
                            }
                        }).setCancelable(false).show();
            } else {
                // 选择
                serverVersionName = upgradeBean.getNewVersionName();
                new MyMaterialAlertDialogBuilder(weakReference.get())
                        .setTitle(String.format(weakReference.get().getString(R.string.formatFindNewVersion), upgradeBean.getNewVersionName()))
                        .setMessage(charSequence)
                        .setPositiveButton(weakReference.get().getString(R.string.upgradeNow), (dialog, which) -> {
                            dialog.dismiss();
                            if (upgradeBean.isInnerUpgrade()) {
                                execute(applicationId, path, upgradeBean.getDownloadUrl());
                            } else {
                                upgradeListener.outUpgrade();
                            }
                        })
                        .setNegativeButton(weakReference.get().getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                        .setNeutralButton(weakReference.get().getString(R.string.noRemindAnyMore), (dialog, which) -> {
                            dialog.dismiss();
                            MmkvKit.defaultMmkv().encode(WidgetConstant.NO_REMIND_ANY_MORE, String.valueOf(newVersionCode));
                        }).setCancelable(false).show();
            }
        } else if (noUpgradeHint) {
            ToastKit.showShort(String.format(weakReference.get().getString(R.string.formatAlreadyTheLatestVersion), AppManager.versionName(weakReference.get())));
        }
    }

    /**
     * 执行
     *
     * @param applicationId 应用 ID
     * @param path          路径
     * @param url           统一资源定位符
     */
    private void execute(String applicationId, String path, String url) {
        // 监听下载进度
        UpgradeManager.getDownloadProgressEventObservable().subscribe(downloadProgressEvent -> {
            if ((null != downloadProgressDialog) && downloadProgressDialog.isShowing() && downloadProgressEvent.areNotDownloadFinished()) {
                // 正常 downloadProgressEvent.getTotal()
                downloadProgressDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
            }
        });
        // 删前更新所下旧 APK
        UpgradeManager.deleteOldApk(path);
        // 新版已下直 return
        // 此时无需装 APK（isApkFileDownloaded 已调安装）
        if (UpgradeManager.areApkDownloaded(applicationId, path, serverVersionName)) {
            return;
        }
        // 下新 APK
        UpgradeManager.downloadApkFile(url, path, serverVersionName)
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        showDownloadProgressDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDownloadProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDownloadProgressDialog();
                    }

                    @Override
                    public void onNext(File apkFile) {
                        if (null != apkFile) {
                            UpgradeManager.installApk(applicationId, apkFile);
                        }
                    }
                });
    }

    /**
     * 显下载进度框
     */
    private void showDownloadProgressDialog() {
        if (null == downloadProgressDialog) {
            downloadProgressDialog = new DownloadProgressDialog(weakReference.get());
        }
        downloadProgressDialog.show();
    }

    /**
     * 隐下载进度框
     */
    private void dismissDownloadProgressDialog() {
        if ((null != downloadProgressDialog) && downloadProgressDialog.isShowing() && (null != ActivitySuperviseManager.getInstance().getTopActivityInstance())) {
            downloadProgressDialog.dismiss();
        }
    }
}
