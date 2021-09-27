package com.chaos.pool.module.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chaos.pool.R;
import com.chaos.pool.base.BasePoolActivity;
import com.chaos.pool.module.login.kit.LoginTwoActivityKit;
import com.chaos.pool.module.login.kit.UserAgreementAndPrivacyPolicyActivityKit;
import com.chaos.pool.module.login.listener.LoginTwoActivityListener;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.statusbar.StatusBarUtils;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * @desc: 登录二页
 * @author: zsp
 * @date: 2021/9/17 4:31 下午
 */
public class LoginTwoActivity extends BasePoolActivity implements View.OnClickListener {
    /**
     * 登录二页监听
     */
    private static LoginTwoActivityListener loginTwoActivityListener;
    private MaterialToolbar loginTwoActivityMt;
    private EditText loginTwoActivityEtPleaseInputPhoneNumber;
    private ImageView loginTwoActivityIvPhoneNumberClear;
    private TextView loginTwoActivityTvLogin;
    private TextView loginTwoActivityTvUserAgreement;
    private TextView loginTwoActivityTvPrivacyPolicy;
    /**
     * 用户协议和隐私政策页配套元件
     */
    private UserAgreementAndPrivacyPolicyActivityKit userAgreementAndPrivacyPolicyActivityKit;
    /**
     * 登录二页配套元件
     */
    private LoginTwoActivityKit loginTwoActivityKit;

    /**
     * 设登录二页监听
     *
     * @param loginTwoActivityListener 登录二页监听
     */
    public static void setLoginTwoActivityListener(LoginTwoActivityListener loginTwoActivityListener) {
        LoginTwoActivity.loginTwoActivityListener = loginTwoActivityListener;
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_login_two;
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
        loginTwoActivityMt = findViewById(R.id.loginTwoActivityMt);
        loginTwoActivityEtPleaseInputPhoneNumber = findViewById(R.id.loginTwoActivityEtPleaseInputPhoneNumber);
        loginTwoActivityIvPhoneNumberClear = findViewById(R.id.loginTwoActivityIvPhoneNumberClear);
        loginTwoActivityTvLogin = findViewById(R.id.loginTwoActivityTvLogin);
        loginTwoActivityTvUserAgreement = findViewById(R.id.loginTwoActivityTvUserAgreement);
        loginTwoActivityTvPrivacyPolicy = findViewById(R.id.loginTwoActivityTvPrivacyPolicy);
    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        // 用户协议和隐私政策页配套元件
        userAgreementAndPrivacyPolicyActivityKit = new UserAgreementAndPrivacyPolicyActivityKit();
        // 登录二页配套元件
        loginTwoActivityKit = new LoginTwoActivityKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        // MaterialToolbar
        loginTwoActivityMt.setNavigationOnClickListener(v -> finish());
        // 手机号
        loginTwoActivityEtPleaseInputPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    loginTwoActivityTvLogin.setEnabled(true);
                    loginTwoActivityTvLogin.setTextColor(ContextCompat.getColor(LoginTwoActivity.this, R.color.fontInput));
                    loginTwoActivityTvLogin.setBackground(ContextCompat.getDrawable(LoginTwoActivity.this, R.drawable.purple_500_solid_semi_circle));
                } else {
                    loginTwoActivityTvLogin.setEnabled(false);
                    loginTwoActivityTvLogin.setTextColor(ContextCompat.getColor(LoginTwoActivity.this, R.color.fontHint));
                    loginTwoActivityTvLogin.setBackground(ContextCompat.getDrawable(LoginTwoActivity.this, R.drawable.pool_login_button_background_solid_semi_circle));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 控件
        loginTwoActivityIvPhoneNumberClear.setOnClickListener(this);
        loginTwoActivityTvLogin.setOnClickListener(this);
        loginTwoActivityTvUserAgreement.setOnClickListener(this);
        loginTwoActivityTvPrivacyPolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.loginTwoActivityIvPhoneNumberClear) {
            // 清手机号
            loginTwoActivityEtPleaseInputPhoneNumber.setText("");
        } else if (id == R.id.loginTwoActivityTvLogin) {
            // 登录
            loginTwoActivityKit.login(this, loginTwoActivityEtPleaseInputPhoneNumber, loginTwoActivityListener);
        } else if (id == R.id.loginTwoActivityTvUserAgreement) {
            // 用户协议
            userAgreementAndPrivacyPolicyActivityKit.showUserAgreementAndPrivacyPolicy(this, PoolConstant.USER_AGREEMENT);
        } else if (id == R.id.loginTwoActivityTvPrivacyPolicy) {
            // 政策隐私
            userAgreementAndPrivacyPolicyActivityKit.showUserAgreementAndPrivacyPolicy(this, PoolConstant.PRIVACY_POLICY);
        }
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        loginTwoActivityKit.phoneNumberPreShow(loginTwoActivityEtPleaseInputPhoneNumber);
    }

    /**
     * 传 EditText 的 ID
     * 没传入 EditText 不处理
     *
     * @return ID 数组
     */
    @Override
    protected int[] hideSoftByEditViewIds() {
        return new int[]{R.id.loginTwoActivityEtPleaseInputPhoneNumber};
    }
}