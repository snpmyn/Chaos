package com.chaos.pgyer.kit;

import androidx.appcompat.app.AppCompatActivity;

import com.pgyer.pgyersdk.PgyerSDKManager;
import com.pgyer.pgyersdk.callback.CheckoutCallBack;
import com.pgyer.pgyersdk.callback.CheckoutVersionCallBack;
import com.pgyer.pgyersdk.callback.PgyUncaughtExceptionHandler;
import com.pgyer.pgyersdk.pgyerenum.Features;

/**
 * Created on 2022/6/22
 *
 * @author zsp
 * @desc Pgyer 配套元件
 */
public class PgyerKit {
    /**
     * 检测软件更新
     *
     * @param appCompatActivity 活动
     */
    public static void checkSoftwareUpdate(AppCompatActivity appCompatActivity) {
        PgyerSDKManager.checkSoftwareUpdate(appCompatActivity);
    }

    /**
     * 检测软件更新
     *
     * @param appCompatActivity       活动
     * @param checkoutVersionCallBack 检测版本回调
     */
    public static void checkSoftwareUpdate(AppCompatActivity appCompatActivity, CheckoutVersionCallBack checkoutVersionCallBack) {
        PgyerSDKManager.checkSoftwareUpdate(appCompatActivity, checkoutVersionCallBack);
    }

    /**
     * 检测版本更新
     * <p>
     * {@link com.pgyer.pgyersdk.model.CheckSoftModel} 参数介绍
     * 蒲公英生成区分历史版本的 build 号
     * int buildBuildVersion
     * 强制更新版本号（未强置更新默空）
     * String forceUpdateVersion
     * 强制更新版本编号
     * String forceUpdateVersionNo
     * 强制更新否
     * boolean needForceUpdate
     * 有新版本否
     * boolean buildHaveNewVersion
     * 应用安装地址
     * String downloadURL
     * 上传包版本编号（默 1）
     * 即编译版本号，编译后同步变更。iOS 中是字符串，Android 中是整数。例如：100、101 等。
     * String buildVersionNo
     * 版本号（默 1.0）
     * 面向用户标识。例如：1.0、1.1 等。
     * String buildVersion
     * 应用短链接
     * String buildShortcutUrl
     * 应用更新说明
     * String buildUpdateDescription
     *
     * @param appCompatActivity 活动
     * @param checkoutCallBack  检测结果回调
     */
    public static void checkVersionUpdate(AppCompatActivity appCompatActivity, CheckoutCallBack checkoutCallBack) {
        PgyerSDKManager.checkVersionUpdate(appCompatActivity, checkoutCallBack);
    }

    /**
     * 设用户数据
     * <p>
     * 须 JSON 字符串格式 {"userId":"001", "userName":"abc"})
     *
     * @param userDate 用户数据
     */
    public static void setUserData(String userDate) {
        PgyerSDKManager.setUserData(userDate);
    }

    /**
     * 允许
     *
     * @param features 特征
     *                 {@link Features#CHECK_UPDATE} 版本自动检测（不设默关）
     *                 {@link Features#APP_LAUNCH_TIME} 启动时间统计
     *                 {@link Features#APP_PAGE_CATON} 页面卡顿统计
     */
    public static void enable(Features features) {
        PgyerSDKManager.enable(features);
    }

    /**
     * 取消
     *
     * @param features 特征
     *                 {@link Features#CHECK_UPDATE} 版本自动检测（不设默关）
     *                 {@link Features#APP_LAUNCH_TIME} 启动时间统计
     *                 {@link Features#APP_PAGE_CATON} 页面卡顿统计
     */
    public static void disable(Features features) {
        PgyerSDKManager.disable(features);
    }

    /**
     * 取消全部
     */
    public static void disableAll() {
        PgyerSDKManager.disableAll();
    }

    /**
     * 设无捕获异常处理器
     * <p>
     * 初始后调
     *
     * @param pgyUncaughtExceptionHandler PgyUncaughtExceptionHandler
     */
    public static void setUncaughtExceptionHandler(PgyUncaughtExceptionHandler pgyUncaughtExceptionHandler) {
        PgyerSDKManager.setUncaughtExceptionHandler(pgyUncaughtExceptionHandler);
    }

    /**
     * 报告异常
     *
     * @param exception 异常
     */
    public static void reportException(Exception exception) {
        PgyerSDKManager.reportException(exception);
    }
}
