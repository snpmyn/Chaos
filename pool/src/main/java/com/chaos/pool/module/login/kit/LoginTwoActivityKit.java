package com.chaos.pool.module.login.kit;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.pool.R;
import com.chaos.pool.module.login.listener.LoginTwoActivityListener;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.storage.mmkv.MmkvKit;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.validate.RegularUtils;

/**
 * Created on 2021/9/18
 *
 * @author zsp
 * @desc 登录二页配套元件
 */
public class LoginTwoActivityKit {
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
     * 登录
     *
     * @param appCompatActivity              活动
     * @param editTextPleaseInputPhoneNumber 请输入手机号输入框
     * @param loginTwoActivityListener       登录二页监听
     */
    public void login(AppCompatActivity appCompatActivity, @NonNull EditText editTextPleaseInputPhoneNumber, LoginTwoActivityListener loginTwoActivityListener) {
        String phoneNumber = editTextPleaseInputPhoneNumber.getText().toString();
        // 手机号（精确）
        if (RegularUtils.allMobile(phoneNumber)) {
            loginTwoActivityListener.handleWithPhoneNumber(appCompatActivity, editTextPleaseInputPhoneNumber.getText().toString());
        } else {
            ToastKit.showShort(appCompatActivity.getString(R.string.pleaseInputCorrectPhoneNumber));
        }
    }
}
