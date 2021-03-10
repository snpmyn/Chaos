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
    public static void initMobSms(Application application, String appKey, String appSecret) {
        MobSDK.init(application, appKey, appSecret);
    }
}
