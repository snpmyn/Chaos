package com.chaos.mobsms.sms.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.chaos.mobsms.sms.errorcode.MobSmsErrorCode;

import org.json.JSONObject;

import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import timber.log.Timber;

/**
 * Created on 2019/7/4.
 *
 * @author 郑少鹏
 * @desc MobSMS 处理者
 */
public class MobSmsHandler extends Handler {
    private final Context context;
    private MobSmsHandlerEventListener mobSmsHandlerEventListener;

    /**
     * constructor
     *
     * @param looper  The looper, must not be null.
     *                Use the provided {@link Looper} instead of the default one.
     * @param context Context
     */
    public MobSmsHandler(@NonNull Looper looper, Context context) {
        super(looper);
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == SMSSDK.RESULT_COMPLETE) {
            // 回调成 result-1
            Timber.d("验证码状 回调成%s%s", event, result);
            if (data instanceof Boolean && (Boolean) data) {
                mobSmsHandlerEventListener.smartVerification();
                return;
            }
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                // 提验证码成 event-1
                Timber.d("验证码状 提成%s%s", event, result);
                mobSmsHandlerEventListener.eventSubmitVerificationCode();
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                // 获验证码成 event-2
                Timber.d("验证码状 获成%s%s", event, result);
                mobSmsHandlerEventListener.eventGetVerificationCode();
            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                // 返支持发送验证码国家列表 event-3
                Timber.d("验证码状 支持发送验证码国家列表%s%s", event, result);
            }
        } else {
            mobSmsHandlerEventListener.resultError();
            try {
                ((Throwable) data).printStackTrace();
                Throwable throwable = (Throwable) data;
                String message = throwable.getMessage();
                if (null != message) {
                    JSONObject object = new JSONObject(message);
                    MobSmsErrorCode.errorMessageShow(context, object.optInt("status"));
                }
            } catch (Exception e) {
                SMSLog.getInstance().w(e);
            }
        }
    }

    public interface MobSmsHandlerEventListener {
        /**
         * 智能验证
         */
        void smartVerification();

        /**
         * 提验证码事件
         */
        void eventSubmitVerificationCode();

        /**
         * 获验证码事件
         */
        void eventGetVerificationCode();

        /**
         * 结果错误
         */
        void resultError();
    }

    public void setMobSmsHandlerEventListener(MobSmsHandlerEventListener mobSmsHandlerEventListener) {
        this.mobSmsHandlerEventListener = mobSmsHandlerEventListener;
    }
}
