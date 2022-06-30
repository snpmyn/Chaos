package com.chaos.pool.module.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.mobsms.sms.handler.MobSmsHandler;
import com.chaos.mobsms.sms.kit.MobSmsKit;
import com.chaos.mobsms.sms.listener.MobSmsListener;
import com.chaos.pool.R;
import com.chaos.pool.base.BasePoolActivity;
import com.chaos.pool.module.login.kit.LoginActivityKit;
import com.chaos.pool.module.login.kit.UserAgreementAndPrivacyPolicyActivityKit;
import com.chaos.pool.module.login.listener.LoginActivityListener;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.statusbar.StatusBarUtils;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.view.ViewUtils;
import com.chaos.widget.dialog.bocdialog.kit.DialogKit;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import cn.smssdk.EventHandler;

/**
 * @desc: 登录页
 * @author: zsp
 * @date: 2021/9/16 9:37 上午
 */
public class LoginActivity extends BasePoolActivity implements View.OnClickListener, MobSmsListener {
    /**
     * 登录页监听
     */
    private static LoginActivityListener loginActivityListener;
    private MaterialToolbar loginActivityMt;
    private EditText loginActivityEtPleaseInputPhoneNumber;
    private ImageView loginActivityIvPhoneNumberClear;
    private EditText loginActivityEtPleaseInputVerificationCode;
    private TextView loginActivityTvGetVerificationCode;
    private MaterialButton loginActivityMbLogin;
    private TextView loginActivityTvUserAgreement;
    private TextView loginActivityTvPrivacyPolicy;
    /**
     * 短信 SDK 操作回调
     */
    private EventHandler eventHandler;
    /**
     * MobSMS 处理者
     */
    private MobSmsHandler mobSmsHandler;
    /**
     * 计时器
     */
    private CountDownTimer countDownTimer;
    /**
     * 用户协议和隐私政策页配套元件
     */
    private UserAgreementAndPrivacyPolicyActivityKit userAgreementAndPrivacyPolicyActivityKit;
    /**
     * 登录页配套元件
     */
    private LoginActivityKit loginActivityKit;

    /**
     * 设登录页监听
     *
     * @param loginActivityListener 登录页监听
     */
    public static void setLoginActivityListener(LoginActivityListener loginActivityListener) {
        LoginActivity.loginActivityListener = loginActivityListener;
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     * @param layoutResId        布局资源 ID
     */
    @Override
    protected void initContentView(Bundle savedInstanceState, int layoutResId) {
        StatusBarUtils.statusBarLight(this, R.color.pageBackground);
        setContentView(layoutResId);
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        loginActivityMt = findViewById(R.id.loginActivityMt);
        loginActivityEtPleaseInputPhoneNumber = findViewById(R.id.loginActivityEtPleaseInputPhoneNumber);
        loginActivityIvPhoneNumberClear = findViewById(R.id.loginActivityIvPhoneNumberClear);
        loginActivityEtPleaseInputVerificationCode = findViewById(R.id.loginActivityEtPleaseInputVerificationCode);
        loginActivityTvGetVerificationCode = findViewById(R.id.loginActivityTvGetVerificationCode);
        loginActivityMbLogin = findViewById(R.id.loginActivityMbLogin);
        loginActivityTvUserAgreement = findViewById(R.id.loginActivityTvUserAgreement);
        loginActivityTvPrivacyPolicy = findViewById(R.id.loginActivityTvPrivacyPolicy);
    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        // 短信 SDK 操作回调
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mobSmsHandler.sendMessage(msg);
            }
        };
        // 注册回调接口
        MobSmsKit.getInstanceByDcl().registerEventHandler(eventHandler);
        // MobSMS 处理者
        mobSmsHandler = new MobSmsHandler(getMainLooper(), this);
        mobSmsHandler.setMobSmsListener(this);
        // 计时器
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                loginActivityTvGetVerificationCode.setEnabled(false);
                loginActivityTvGetVerificationCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.fontHint));
                loginActivityTvGetVerificationCode.setText(String.format(getString(R.string.formatRecapVerificationCode), millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                loginActivityTvGetVerificationCode.setEnabled(true);
                loginActivityTvGetVerificationCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.purple_500));
                loginActivityTvGetVerificationCode.setText(R.string.getVerificationCode);
            }
        };
        // 用户协议和隐私政策页配套元件
        userAgreementAndPrivacyPolicyActivityKit = new UserAgreementAndPrivacyPolicyActivityKit();
        // 登录页配套元件
        loginActivityKit = new LoginActivityKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        // MaterialToolbar
        loginActivityMt.setNavigationOnClickListener(v -> finish());
        // 手机号
        loginActivityEtPleaseInputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    ViewUtils.showView(loginActivityIvPhoneNumberClear);
                    loginActivityTvGetVerificationCode.setEnabled(true);
                    loginActivityTvGetVerificationCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.purple_500));
                } else {
                    ViewUtils.hideView(loginActivityIvPhoneNumberClear, View.INVISIBLE);
                    loginActivityTvGetVerificationCode.setEnabled(false);
                    loginActivityTvGetVerificationCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.fontHint));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 控件
        loginActivityIvPhoneNumberClear.setOnClickListener(this);
        // 验证码
        loginActivityEtPleaseInputVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    loginActivityMbLogin.setEnabled(true);
                    loginActivityMbLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.fontInput));
                    loginActivityMbLogin.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.purple_500));
                } else {
                    loginActivityMbLogin.setEnabled(false);
                    loginActivityMbLogin.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.fontHint));
                    loginActivityMbLogin.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.pool_login_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 控件
        loginActivityTvGetVerificationCode.setOnClickListener(this);
        loginActivityMbLogin.setOnClickListener(this);
        loginActivityTvUserAgreement.setOnClickListener(this);
        loginActivityTvPrivacyPolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.loginActivityIvPhoneNumberClear) {
            // 清手机号
            loginActivityEtPleaseInputPhoneNumber.setText("");
        } else if (id == R.id.loginActivityTvGetVerificationCode) {
            // 获取验证码
            loginActivityKit.getVerificationCode(this, loginActivityEtPleaseInputPhoneNumber);
        } else if (id == R.id.loginActivityMbLogin) {
            // 登录
            DialogKit.getInstance(this).commonLoading(getString(R.string.loginTwo), null);
            MobSmsKit.getInstanceByDcl().submitVerificationCode("86", loginActivityEtPleaseInputPhoneNumber.getText().toString(), loginActivityEtPleaseInputVerificationCode.getText().toString());
        } else if (id == R.id.loginActivityTvUserAgreement) {
            // 用户协议
            userAgreementAndPrivacyPolicyActivityKit.showUserAgreementAndPrivacyPolicy(this, PoolConstant.USER_AGREEMENT);
        } else if (id == R.id.loginActivityTvPrivacyPolicy) {
            // 政策隐私
            userAgreementAndPrivacyPolicyActivityKit.showUserAgreementAndPrivacyPolicy(this, PoolConstant.PRIVACY_POLICY);
        }
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        loginActivityKit.phoneNumberPreShow(loginActivityEtPleaseInputPhoneNumber);
    }

    /**
     * 智能验证
     */
    @Override
    public void smartVerification() {
        ToastKit.showShort(getString(R.string.intelligentVerificationPasses));
        loginActivityListener.handleWithPhoneNumber(this, loginActivityEtPleaseInputPhoneNumber.getText().toString());
    }

    /**
     * 提验证码事件
     */
    @Override
    public void eventSubmitVerificationCode() {
        loginActivityListener.handleWithPhoneNumber(this, loginActivityEtPleaseInputPhoneNumber.getText().toString());
    }

    /**
     * 获验证码事件
     */
    @Override
    public void eventGetVerificationCode() {
        countDownTimer.start();
        DialogKit.getInstance(this).end();
        ToastKit.showShort(getString(R.string.authenticationCodeAlreadySend));
    }

    /**
     * 结果错误
     */
    @Override
    public void resultError() {
        DialogKit.getInstance(this).end();
    }

    /**
     * 传 EditText 的 ID
     * 没传入 EditText 不处理
     *
     * @return ID 数组
     */
    @Override
    protected int[] hideSoftByEditViewIds() {
        return new int[]{R.id.loginActivityEtPleaseInputPhoneNumber, R.id.loginActivityEtPleaseInputVerificationCode};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginActivityKit.onDestroy(eventHandler, mobSmsHandler, countDownTimer);
    }
}