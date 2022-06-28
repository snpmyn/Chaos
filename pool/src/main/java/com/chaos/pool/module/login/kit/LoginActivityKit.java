package com.chaos.pool.module.login.kit;

import android.os.CountDownTimer;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.mobsms.sms.handler.MobSmsHandler;
import com.chaos.mobsms.sms.kit.MobSmsKit;
import com.chaos.pool.R;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.storage.mmkv.MmkvKit;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.validate.RegularUtils;
import com.chaos.widget.dialog.bocdialog.kit.DialogKit;
import com.chaos.widget.dialog.materialalertdialog.MyMaterialAlertDialogBuilder;

import cn.smssdk.EventHandler;

/**
 * Created on 2021/9/16
 *
 * @author zsp
 * @desc 登录页配套元件
 */
public class LoginActivityKit {
    /**
     * 手机号预显示
     *
     * @param editTextPleaseInputPhoneNumber 请输入手机号输入框
     */
    public void phoneNumberPreShow(@NonNull EditText editTextPleaseInputPhoneNumber) {
        editTextPleaseInputPhoneNumber.setText(MmkvKit.defaultMmkv().decodeString(PoolConstant.LOGIN_$_PHONE_NUMBER));
        editTextPleaseInputPhoneNumber.setSelection(editTextPleaseInputPhoneNumber.getText().length());
    }

    /**
     * 获取验证码
     *
     * @param appCompatActivity              活动
     * @param editTextPleaseInputPhoneNumber 请输入手机号输入框
     */
    public void getVerificationCode(AppCompatActivity appCompatActivity, @NonNull EditText editTextPleaseInputPhoneNumber) {
        String phoneNumber = editTextPleaseInputPhoneNumber.getText().toString().replace(" ", "");
        // 手机号（精确）
        if (RegularUtils.allMobile(phoneNumber)) {
            new MyMaterialAlertDialogBuilder(appCompatActivity)
                    .setTitle(R.string.getAuthenticationCodeHint)
                    .setMessage(phoneNumber)
                    .setPositiveButton(R.string.agree, (dialog, which) -> {
                        dialog.dismiss();
                        DialogKit.getInstance(appCompatActivity).commonLoading(appCompatActivity.getString(R.string.getting), null);
                        MobSmsKit.getInstanceByDcl().getVerificationCode("86", phoneNumber, null, null);
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss()).show();
        } else {
            ToastKit.showShort(appCompatActivity.getString(R.string.pleaseInputCorrectPhoneNumber));
        }
    }

    /**
     * 销毁
     *
     * @param eventHandler   短信 SDK 操作回调
     * @param mobSmsHandler  MobSMS 处理者
     * @param countDownTimer 计时器
     */
    public void onDestroy(EventHandler eventHandler, @NonNull MobSmsHandler mobSmsHandler, CountDownTimer countDownTimer) {
        // 注销回调接口
        MobSmsKit.getInstanceByDcl().unregisterEventHandler(eventHandler);
        // 移 Handler
        mobSmsHandler.removeCallbacksAndMessages(null);
        // 计时器
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }
}
