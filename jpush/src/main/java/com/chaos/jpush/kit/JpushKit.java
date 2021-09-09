package com.chaos.jpush.kit;

import android.app.Activity;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * Created on 2019/6/27.
 *
 * @author 郑少鹏
 * @desc JpushKit
 */
public class JpushKit {
    /**
     * 调试模式
     * <p>
     * init 接口前调避部分日志无打印。
     * 多进程建于自定 Application 之 onCreate 调。
     *
     * @param debug true 打印 debug 级日志；false 打印 warning 级日志
     */
    public static void setDebugMode(boolean debug) {
        JPushInterface.setDebugMode(debug);
    }

    /**
     * 初始
     * <p>
     * 初始 JPush 推送服务。
     * 建于自定 Application 之 onCreate 调。
     * 暂不初始 JPush SDK 时不 init 且应用初始时 stopPush。
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        JPushInterface.init(context);
    }

    /**
     * 停推送
     * <p>
     * JPush SDK 默开推送服务。
     * 调停推送服务 API 停极光推送服务后需极光推送服务时调恢复推送服务 API。
     * <p>
     * 该功能是一完全本地状操作，即停推送服务状不存至服务器。
     * 停推送服务后卸装应用，JPush SDK 恢复正常默认行为。
     * 该功能似网络中断，即推送服务停止期间所推消息，恢复推送服务后若仍于保留时长范围，客户端会收到离线消息。
     * <p>
     * 完全停 JPush 推送服务：
     * 收不到推送消息；
     * 极光推送其它 API 无效；
     * 不可 JPushInterface.init 而 resumePush 恢复。
     *
     * @param context 应用上下文
     */
    public static void stopPush(Context context) {
        JPushInterface.stopPush(context);
    }

    /**
     * 恢复推送
     * <p>
     * 恢复 JPush 推送服务。
     *
     * @param context 应用上下文
     */
    public static void resumePush(Context context) {
        JPushInterface.resumePush(context);
    }

    /**
     * 推送被停
     * <p>
     * 1.5.2+ 支持。
     *
     * @param context 应用上下文
     * @return 推送被停
     */
    public static boolean arePushStopped(Context context) {
        return JPushInterface.isPushStopped(context);
    }

    /**
     * RegistrationID
     * <p>
     * 1.6.0+ 支持。
     * <p>
     * 应用程序对应 RegistrationID。
     * 仅应用程序成功注册至 JPush 服务器才返对应值，否空字符串。
     * <p>
     * SDK 初注册成功后通于自定 Receiver 监听 Action - cn.jpush.android.intent.REGISTRATION 获对应 RegistrationID。
     * 注册成功后亦可 {@link JPushInterface#getRegistrationID(Context)} 获。
     *
     * @param context 应用上下文
     * @return RegistrationID
     */
    public static String getRegistrationId(Context context) {
        return JPushInterface.getRegistrationID(context);
    }

    /**
     * 获焦
     * <p>
     * 1.6.0+ 支持。
     * <p>
     * 统计“用户使用时长”、“活跃用户”、“用户打开次数”并报至服务器，Portal 展示。
     * 所有 Activity 之 onResume 调。
     *
     * @param activity 当前所在 Activity
     */
    public static void onResume(final Activity activity) {
        JPushInterface.onResume(activity);
    }

    /**
     * 失焦
     * <p>
     * 1.6.0+ 支持。
     * <p>
     * 统计“用户使用时长”、“活跃用户”、“用户打开次数”并报至服务器，Portal 展示。
     * 所有 Activity 之 onPause 调。
     *
     * @param activity 当前所在 Activity
     */
    public static void onPause(final Activity activity) {
        JPushInterface.onPause(activity);
    }

    /**
     * 上报通知被打开
     * <p>
     * Android SDK 1.6.1+ 支持。
     * <p>
     * 上报用户通知栏被打开或用户自定消息被展示等客户端需统计事件。
     *
     * @param context 应用上下文
     * @param msgId   所推每条消息和通知对应的唯一 ID（msgId 来源于发消息和通知的 Extra 字段 JPushInterface.EXTRA_MSG_ID，参考接收推送消息 Receiver）
     */
    public static void reportNotificationOpened(Context context, String msgId) {
        JPushInterface.reportNotificationOpened(context, msgId);
    }

    /**
     * 申请权限
     * <p>
     * 2.1.0+ 支持。
     * <p>
     * Android 6.0+ 需请求一些权限使统计更精准、功能更丰富。
     * <p>
     * "android.permission.READ_PHONE_STATE"
     * "android.permission.WRITE_EXTERNAL_STORAGE"
     * "android.permission.READ_EXTERNAL_STORAGE"
     * "android.permission.ACCESS_FINE_LOCATION"
     *
     * @param context 当前 Activity 上下文
     */
    public static void requestPermission(Context context) {
        JPushInterface.requestPermission(context);
    }

    /**
     * 省电模式
     * <p>
     * 3.0.9+ 支持。
     * <p>
     * JPush SDK 开 / 关省电模式（默关）
     *
     * @param context 当前 Activity 上下文
     * @param enable  true 开；false 关
     */
    public static void setPowerSaveMode(Context context, boolean enable) {
        JPushInterface.setPowerSaveMode(context, enable);
    }

    /**
     * 连接状
     * <p>
     * 1.6.3+ 支持。
     * <p>
     * 可用该功能获当前 Push 服务连接状。
     * <p>
     * 连接状变（连 / 断）时发一广播。
     * 可于自定 Receiver 监听 cn.jpush.android.intent.CONNECTION 获变状，亦可通 API 主动获。
     *
     * @param context 应用上下文
     * @return 连接状
     */
    @Deprecated
    public static boolean getConnectionState(Context context) {
        return JPushInterface.getConnectionState(context);
    }
}
