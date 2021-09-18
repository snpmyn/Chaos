package com.chaos.pool.module.login.kit;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.pool.R;
import com.chaos.pool.module.login.UserAgreementAndPrivacyPolicyActivity;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.intent.IntentJump;
import com.chaos.util.java.intent.IntentVerify;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2021/9/16
 *
 * @author zsp
 * @desc 用户协议和隐私政策页配套元件
 */
public class UserAgreementAndPrivacyPolicyActivityKit {
    /**
     * 设置标题
     *
     * @param webView  TextView
     * @param textView WebView
     */
    public void setTitle(@NotNull WebView webView, TextView textView) {
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                textView.setText(title);
            }
        };
        webView.setWebChromeClient(webChromeClient);
    }

    /**
     * 显示用户协议或隐私政策
     * <p>
     * 用户协议路径默 file:///android_asset/UserAgreement.html。
     * 隐私政策路径默 file:///android_asset/PrivacyPolicy.html。
     *
     * @param appCompatActivity 活动
     * @param webView           WebView
     */
    public void showUserAgreementOrPrivacyPolicy(@NonNull AppCompatActivity appCompatActivity, WebView webView) {
        if (TextUtils.equals(IntentVerify.getStringExtra(appCompatActivity.getIntent(), PoolConstant.USER_AGREEMENT), PoolConstant.USER_AGREEMENT)) {
            webView.loadUrl(appCompatActivity.getString(R.string.poolUserAgreementPath));
        } else if (TextUtils.equals(IntentVerify.getStringExtra(appCompatActivity.getIntent(), PoolConstant.PRIVACY_POLICY), PoolConstant.PRIVACY_POLICY)) {
            webView.loadUrl(appCompatActivity.getString(R.string.poolPrivacyPolicyPath));
        }
    }

    /**
     * 显示用户协议和隐私政策策
     *
     * @param appCompatActivity 活动
     * @param functionalName    功能名称
     */
    public void showUserAgreementAndPrivacyPolicy(AppCompatActivity appCompatActivity, String functionalName) {
        Intent intentUserAgreement = new Intent();
        intentUserAgreement.putExtra(functionalName, functionalName);
        IntentJump.getInstance().jumpWithAnimation(intentUserAgreement, appCompatActivity, false, UserAgreementAndPrivacyPolicyActivity.class, 0, 0);
    }
}
