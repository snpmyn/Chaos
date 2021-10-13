package com.chaos.pool.module.splash.kit;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.chaos.mobsms.policy.MobPolicyKit;
import com.chaos.pool.R;
import com.chaos.pool.application.PoolApp;
import com.chaos.pool.module.login.kit.UserAgreementAndPrivacyPolicyActivityKit;
import com.chaos.pool.module.splash.listener.SplashActivityListener;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.chaos.util.java.net.NetManager;
import com.chaos.util.java.storage.mmkv.MmkvKit;
import com.chaos.widget.dialog.customdialog.BaseDialog;
import com.chaos.widget.dialog.customdialog.BaseViewConvertListener;
import com.chaos.widget.dialog.customdialog.CustomDialog;
import com.chaos.widget.dialog.customdialog.ViewHolder;
import com.chaos.widget.dialog.materialalertdialog.MyMaterialAlertDialogBuilder;
import com.mob.OperationCallback;
import com.permissionx.guolindev.PermissionX;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2021/9/16
 *
 * @author zsp
 * @desc 闪屏页配套元件
 */
public class SplashActivityKit {
    private static SplashActivityListener splashActivityListener;
    private BaseDialog baseDialog;

    /**
     * 执行
     *
     * @param appCompatActivity 活动
     */
    public void execute(AppCompatActivity appCompatActivity) {
        if (MmkvKit.defaultMmkv().decodeBool(PoolConstant.USER_AGREEMENT_AND_PRIVACY_POLICY)) {
            requestPermissions(appCompatActivity);
        } else if (null == baseDialog) {
            userAgreementAndPrivacyPolicy(appCompatActivity);
        }
    }

    /**
     * 用户协议和隐私政策
     *
     * @param appCompatActivity 活动
     */
    private void userAgreementAndPrivacyPolicy(@NotNull AppCompatActivity appCompatActivity) {
        baseDialog = CustomDialog.init()
                .setLayoutId(R.layout.dialog_user_agreement_and_privacy_policy)
                .setBaseViewConvertListener(new BaseViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseDialog dialog) {
                        TextView dialogUserAgreementAndPrivacyPolicyTvUserAgreementAndPrivacyPolicyContent = holder.getView(R.id.dialogUserAgreementAndPrivacyPolicyTvUserAgreementAndPrivacyPolicyContent);
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(dialogUserAgreementAndPrivacyPolicyTvUserAgreementAndPrivacyPolicyContent.getText().toString());
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                showUserAgreementAndPrivacyPolicy(appCompatActivity, PoolConstant.USER_AGREEMENT);
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(ContextCompat.getColor(appCompatActivity, R.color.pool_user_agreement_and_privacy_policy_text_in_dialog));
                                ds.setUnderlineText(false);
                            }
                        }, 50, 56, 0);
                        spannableStringBuilder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                showUserAgreementAndPrivacyPolicy(appCompatActivity, PoolConstant.PRIVACY_POLICY);
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(ContextCompat.getColor(appCompatActivity, R.color.pool_user_agreement_and_privacy_policy_text_in_dialog));
                                ds.setUnderlineText(false);
                            }
                        }, 57, 63, 0);
                        dialogUserAgreementAndPrivacyPolicyTvUserAgreementAndPrivacyPolicyContent.setMovementMethod(LinkMovementMethod.getInstance());
                        dialogUserAgreementAndPrivacyPolicyTvUserAgreementAndPrivacyPolicyContent.setText(spannableStringBuilder, TextView.BufferType.SPANNABLE);
                        // 不同意并退出
                        TextView dialogUserAgreementAndPrivacyPolicyTvNotAgreeAngLoginOut = holder.getView(R.id.dialogUserAgreementAndPrivacyPolicyTvNotAgreeAngLoginOut);
                        dialogUserAgreementAndPrivacyPolicyTvNotAgreeAngLoginOut.setOnClickListener(v -> {
                            dialog.dismiss();
                            appCompatActivity.finish();
                        });
                        // 同意
                        TextView dialogUserAgreementAndPrivacyPolicyTvAgree = holder.getView(R.id.dialogUserAgreementAndPrivacyPolicyTvAgree);
                        dialogUserAgreementAndPrivacyPolicyTvAgree.setOnClickListener(v -> {
                            dialog.dismiss();
                            if (!NetManager.isNetConnected(appCompatActivity)) {
                                new MyMaterialAlertDialogBuilder(appCompatActivity).setTitle(R.string.hint).setMessage(R.string.currentNoNetwork)
                                        .setPositiveButton(R.string.loginOutToRetry, (dialog1, which) -> {
                                            dialog1.dismiss();
                                            appCompatActivity.finish();
                                        }).setCancelable(false).show();
                                return;
                            }
                            if (PoolApp.getEnsembleMobSms()) {
                                MobPolicyKit.submitPolicyGrantResult(true, new OperationCallback<Void>() {
                                    @Override
                                    public void onComplete(Void aVoid) {
                                        handle(appCompatActivity);
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        new MyMaterialAlertDialogBuilder(appCompatActivity).setTitle(R.string.hint).setMessage(throwable.getMessage())
                                                .setPositiveButton(R.string.loginOutToRetry, (dialog12, which) -> {
                                                    dialog12.dismiss();
                                                    appCompatActivity.finish();
                                                }).setCancelable(false).show();
                                    }
                                });
                            } else {
                                handle(appCompatActivity);
                            }
                        });
                    }
                })
                .setWidth(300)
                .setOutCancel(false)
                .setAnimStyle(R.style.DefaultAnimation)
                .show(appCompatActivity.getSupportFragmentManager());
    }

    /**
     * 显示用户协议和隐私政策策
     *
     * @param appCompatActivity 活动
     * @param functionalName    功能名称
     */
    public void showUserAgreementAndPrivacyPolicy(AppCompatActivity appCompatActivity, String functionalName) {
        UserAgreementAndPrivacyPolicyActivityKit userAgreementAndPrivacyPolicyActivityKit = new UserAgreementAndPrivacyPolicyActivityKit();
        userAgreementAndPrivacyPolicyActivityKit.showUserAgreementAndPrivacyPolicy(appCompatActivity, functionalName);
    }

    /**
     * 处理
     *
     * @param appCompatActivity 活动
     */
    private void handle(AppCompatActivity appCompatActivity) {
        MmkvKit.defaultMmkv().encode(PoolConstant.USER_AGREEMENT_AND_PRIVACY_POLICY, true);
        requestPermissions(appCompatActivity);
    }

    /**
     * 请求权限
     * <p>
     * 极光统计用到，建议授予。
     * "android.permission.READ_PHONE_STATE"
     * "android.permission.WRITE_EXTERNAL_STORAGE"
     * "android.permission.READ_EXTERNAL_STORAGE"
     * "android.permission.ACCESS_FINE_LOCATION"
     * 详参 {@link com.chaos.jpush.kit.JpushKit#requestPermission(Context)}。
     * <p>
     * Bmob 允许读取手机状态（用于创建 BmobInstallation）
     * "android.permission.READ_PHONE_STATE"
     *
     * @param fragmentActivity FragmentActivity
     */
    private void requestPermissions(FragmentActivity fragmentActivity) {
        PermissionX.init(fragmentActivity)
                .permissions(PoolApp.getPermissionList())
                .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(deniedList, fragmentActivity.getString(R.string.coreFundamentalAreBasedOnThePermission), fragmentActivity.getString(R.string.agree), fragmentActivity.getString(R.string.cancel)))
                .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList, fragmentActivity.getString(R.string.youNeedToAllowNecessaryPermissionInSettingManually), fragmentActivity.getString(R.string.ok), fragmentActivity.getString(R.string.cancel)))
                .explainReasonBeforeRequest()
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        splashActivityListener.distribute((AppCompatActivity) fragmentActivity);
                    } else {
                        ActivitySuperviseManager.getInstance().appExit();
                    }
                });
    }

    /**
     * 设闪屏页监听
     *
     * @param splashActivityListener 闪屏页监听
     */
    public void setSplashActivityListener(SplashActivityListener splashActivityListener) {
        SplashActivityKit.splashActivityListener = splashActivityListener;
    }
}
