package com.chaos.mobsms.sms.configure;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created on 2019/4/23.
 *
 * @author 郑少鹏
 * @desc MobSMS 初始化配置
 */
public class MobSmsInitConfigure {
    /**
     * 初始化 MobSMS
     *
     * @param application 应用
     */
    public static void initMobSms(Application application) {
        MobSDK.init(application);
    }

    /**
     * 初始化 MobSMS
     *
     * @param application 应用
     * @param appKey      AppKey
     * @param appSecret   AppSecret
     */
    public static void initMobSms(Application application, String appKey, String appSecret) {
        MobSDK.init(application, appKey, appSecret);
    }
}
