package com.chaos.jpush.kit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.jpush.JpushDisplayActivity;
import com.chaos.jpush.value.JpushConstant;
import com.chaos.kotlin.Pudding;
import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.chaos.util.java.intent.IntentJump;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;

/**
 * Created on 2021/9/7
 *
 * @author zsp
 * @desc JpushMessageReceiver 配套元件
 */
public class JpushMessageReceiverKit {
    /**
     * OnMessage 执行
     *
     * @param appCompatActivity 活动
     * @param customMessage     自定消息
     */
    public void onMessageExecute(AppCompatActivity appCompatActivity, CustomMessage customMessage) {
        Pudding.create(appCompatActivity, choco -> {
            choco.setTitle(customMessage.title);
            choco.setTitleTypeface(Typeface.DEFAULT_BOLD);
            choco.setText(customMessage.message);
            return null;
        }).show();
    }

    /**
     * OnNotifyMessageOpened 执行
     *
     * @param notificationMessage 通知消息
     */
    public void onNotifyMessageOpenedExecute(NotificationMessage notificationMessage) {
        Activity activity = ActivitySuperviseManager.getInstance().getTopActivityInstance();
        if (null != activity) {
            // 上报用户通知被打开
            JpushKit.reportNotificationOpened(activity.getApplicationContext(), notificationMessage.msgId);
            // 跳转
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(JpushConstant.NOTIFICATION_TITLE, notificationMessage.notificationTitle);
            intent.putExtra(JpushConstant.NOTIFICATION_CONTENT, notificationMessage.notificationContent);
            IntentJump.getInstance().jump(intent, activity, false, JpushDisplayActivity.class);
        }
    }
}
