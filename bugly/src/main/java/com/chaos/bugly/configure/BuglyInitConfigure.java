package com.chaos.bugly.configure;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.chaos.bugly.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

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
     * @param canShowUpgradeActivity 可显示更新活动
     * @param upgradeDialogLayoutId  升级对话框 UI 布局 ID
     * @param tipsDialogLayoutId     tip 弹窗 UI 布局 ID
     * @param appId                  AppID
     * @param debug                  调试否
     *                               勿用 BuildConfig.DEBUG (true 或 false 或自定常量)
     */
    public static void initBugly(Application application, Class<? extends Activity> canShowUpgradeActivity, int upgradeDialogLayoutId, int tipsDialogLayoutId, String appId, boolean debug) {
        betaSet(canShowUpgradeActivity, upgradeDialogLayoutId, tipsDialogLayoutId);
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(application);
        // int i 对应 int crashType
        // String s 对应 String errorType
        // String s1 对应 String errorMessage
        // String s2 对应 String errorStack
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public synchronized Map<String, String> onCrashHandleStart(int i, String s, String s1, String s2) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                String x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(application);
                map.put("x5crashInfo", x5CrashInfo);
                return map;
            }

            @Override
            public synchronized byte[] onCrashHandleStart2GetExtraDatas(int i, String s, String s1, String s2) {
                try {
                    return "Extra data.".getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return null;
                }
            }
        });
        Bugly.init(application, appId, debug, userStrategy);
    }

    /**
     * Beta 设置
     *
     * @param canShowUpgradeActivity 可显示更新活动
     * @param upgradeDialogLayoutId  升级对话框 UI 布局 ID
     * @param tipsDialogLayoutId     tip 弹窗 UI 布局 ID
     */
    private static void betaSet(Class<? extends Activity> canShowUpgradeActivity, int upgradeDialogLayoutId, int tipsDialogLayoutId) {
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
        Beta.largeIconId = R.mipmap.icon_round;
        // 状态栏小图
        Beta.smallIconId = R.mipmap.icon;
        // 更新弹窗默展 banner
        // 后台配 banner 拉取失败显此 banner（默不设展 loading）
        Beta.defaultBannerId = R.mipmap.icon;
        // 更新资源保存目录为 SD 卡 Download
        // 后续更新资源存此目录
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // 已点确认弹窗于 APP 下次启自检更新时再提（默 true）
        Beta.showInterruptedStrategy = true;
        // 仅 MainActivity 显更新弹窗（其它 Activity 不显）
        // 不设默所有 Activity 都可显弹窗
        Beta.canShowUpgradeActs.add(canShowUpgradeActivity);
        // 自定升级对话框 UI 布局
        // 注意：保持接口统一，需于指定控件以下方式设 tag，否影响正常使用。
        // 特性图片：beta_upgrade_banner，如：android:tag="beta_upgrade_banner"
        // 标题：beta_title，如：android:tag="beta_title"
        // 升级信息：beta_upgrade_info 如： android:tag="beta_upgrade_info"
        // 更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
        // 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
        // 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
        if (upgradeDialogLayoutId != 0) {
            Beta.upgradeDialogLayoutId = upgradeDialogLayoutId;
        }
        // 自定 tip 弹窗 UI 布局
        // 注意：保持接口统一，需于指定控件以下方式设 tag，否影响正常使用。
        // 标题：beta_title，如：android:tag="beta_title"
        // 提示信息：beta_tip_message 如： android:tag="beta_tip_message"
        // 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
        // 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
        if (tipsDialogLayoutId != 0) {
            Beta.tipsDialogLayoutId = tipsDialogLayoutId;
        }
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
