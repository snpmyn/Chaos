package com.chaos.bugly.configure;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.chaos.bugly.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created on 2018/11/23.
 *
 * @author 郑少鹏
 * @desc Bugly 初始化配置
 * Bugly2.0+ 还支持通 AndroidManifest.xml 配 APP 信息，同于代码配 APP 信息终以代码配为准。
 */
public class BuglyInitConfigure {
    /**
     * 初始化 Bugly
     *
     * @param application            应用
     * @param largeIconId            通知栏大图（R.mipmap.icon_round）
     * @param smallIconId            状态栏小图（R.mipmap.icon）
     * @param defaultBannerId        默 banner 图（R.mipmap.icon）
     * @param canShowUpgradeActivity 可显示更新活动
     * @param appId                  AppID
     */
    public static void initBugly(Application application, int largeIconId, int smallIconId, int defaultBannerId, Class<? extends Activity> canShowUpgradeActivity, String appId) {
        betaSet(largeIconId, smallIconId, defaultBannerId, canShowUpgradeActivity);
        Bugly.init(application, appId, BuildConfig.DEBUG);
    }

    /**
     * Beta 设置
     *
     * @param largeIconId            通知栏大图（R.mipmap.icon_round）
     * @param smallIconId            状态栏小图（R.mipmap.icon）
     * @param defaultBannerId        默 banner 图（R.mipmap.icon）
     * @param canShowUpgradeActivity 可显示更新活动
     */
    private static void betaSet(int largeIconId, int smallIconId, int defaultBannerId, Class<? extends Activity> canShowUpgradeActivity) {
        // true 表 app 启时自动初始升级模块（默 true）
        // false 表不自动初始
        // 考虑 SDK 初始影 app 启速设 false（后面某时刻手调 Beta.init(getApplicationContext(),false)）
        Beta.autoInit = true;
        // true 表初始时自检升级（默 true）
        // false 表不自检升级（需手调 Beta.checkUpgrade()）
        // 60s 内 SDK 不重复向后台请求策略
        Beta.autoCheckUpgrade = true;
        // 升级检查周期 60s（默 0s）
        Beta.upgradeCheckPeriod = 60 * 1000;
        // 启延时 1s（默 3s）
        // APP 启 1s 后初始 SDK 避影 APP 启速
        Beta.initDelay = 1000;
        // 通知栏大图
        Beta.largeIconId = largeIconId;
        // 状态栏小图
        Beta.smallIconId = smallIconId;
        // 更新弹窗默展 banner
        // 后台配 banner 拉取失败显此 banner（默不设展 loading）
        Beta.defaultBannerId = defaultBannerId;
        // 更新资源保存目录为 SD 卡 Download
        // 后续更新资源存此目录
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // 已点确认弹窗于 APP 下次启自检更新时再提（默 true）
        Beta.showInterruptedStrategy = true;
        // 仅 MainActivity 显更新弹窗（其它 Activity 不显）
        // 不设默所有 Activity 都可显弹窗
        Beta.canShowUpgradeActs.add(canShowUpgradeActivity);
        // true 显消息通知（默 true）
        // false 通知栏不显下载进度
        Beta.enableNotification = true;
        // true 时 Wifi 自动下载
        Beta.autoDownloadOnWifi = true;
        // 弹窗显 APK 信息（默 true）
        Beta.canShowApkInfo = true;
        // 开热更新
        Beta.enableHotfix = true;
    }
}
