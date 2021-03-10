package com.chaos.mobsms.policy;

import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.mob.PrivacyPolicy;

import java.util.Locale;

/**
 * Created on 2021/1/7
 *
 * @author zsp
 * @desc Mob 政策配套元件
 */
public class MobPolicyKit {
    /**
     * 提交政策授予结果
     * <p>
     * 该接口必须接入，否或造无法用 MobTech 各 SDK 提供的相关服务。
     * <p>
     * 开发者将用户隐私授权结果作为下面接口头参传递给 SDK 即可。
     * 开发者自己制定调用位置，需调 SDK 前调。
     *
     * @param granted           是否同意隐私协议
     * @param operationCallback 操作回调（可 null）
     */
    public static void submitPolicyGrantResult(boolean granted, OperationCallback<Void> operationCallback) {
        MobSDK.submitPolicyGrantResult(granted, operationCallback);
    }

    /**
     * 获取隐私政策
     * <p>
     * 同步方法，不可在主线程调用。
     * 默用当前系统语言。
     *
     * @param type 隐私协议类型（富文本 / URL）
     *             MobSDK.POLICY_TYPE_URL 获取隐私协议 URL 地址（通过 web 页面展示隐私协议）
     *             MobSDK.POLICY_TYPE_TXT 获取隐私协议完整内容（通过富文本展示隐私协议）
     */
    private void getPrivacyPolicy(int type) {
        MobSDK.getPrivacyPolicy(type);
    }

    /**
     * 获取隐私政策
     * <p>
     * 同步方法，不可在主线程调用。
     *
     * @param type   隐私协议类型（富文本 / URL）
     *               MobSDK.POLICY_TYPE_URL 获取隐私协议 URL 地址（通过 web 页面展示隐私协议）
     *               MobSDK.POLICY_TYPE_TXT 获取隐私协议完整内容（通过富文本展示隐私协议）
     * @param locale 指定隐私协议语言（null 用当前系统语言）
     *               locale 可用 java.util.Locale 提供的值，也可据当前系统语言自动选择。目前仅支持中英文，其它 locale 下返英文版隐私协议。
     */
    private void getPrivacyPolicyWithLocal(int type, Locale locale) {
        MobSDK.getPrivacyPolicy(type, locale);
    }

    /**
     * 获取隐私政策
     * <p>
     * 异步方法。
     * 默用当前系统语言。
     *
     * @param type             隐私协议类型（富文本 / URL）
     *                         MobSDK.POLICY_TYPE_URL 获取隐私协议 URL 地址（通过 web 页面展示隐私协议）
     *                         MobSDK.POLICY_TYPE_TXT 获取隐私协议完整内容（通过富文本展示隐私协议）
     * @param onPolicyListener 监听器
     */
    private void getPrivacyPolicyAsync(int type, PrivacyPolicy.OnPolicyListener onPolicyListener) {
        MobSDK.getPrivacyPolicyAsync(type, onPolicyListener);
    }

    /**
     * 获取隐私政策
     * <p>
     * 异步方法。
     *
     * @param type             隐私协议类型（富文本 / URL）
     *                         MobSDK.POLICY_TYPE_URL 获取隐私协议 URL 地址（通过 web 页面展示隐私协议）
     *                         MobSDK.POLICY_TYPE_TXT 获取隐私协议完整内容（通过富文本展示隐私协议）
     * @param locale           指定隐私协议语言（null 用当前系统语言）
     *                         locale 可用 java.util.Locale 提供的值，也可据当前系统语言自动选择。目前仅支持中英文，其它 locale 下返英文版隐私协议。
     * @param onPolicyListener 监听器
     */
    private void getPrivacyPolicyAsyncWithLocal(int type, Locale locale, PrivacyPolicy.OnPolicyListener onPolicyListener) {
        MobSDK.getPrivacyPolicyAsync(type, locale, onPolicyListener);
    }
}
