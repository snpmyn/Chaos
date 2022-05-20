package com.chaos.mobsms.policy;

import com.mob.MobSDK;

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
     * @param granted 是否同意隐私协议
     */
    public static void submitPolicyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted);
    }
}
