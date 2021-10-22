package com.chaos.widget.dialog.bocdialog.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.chaos.util.java.density.DensityUtils;
import com.chaos.widget.R;
import com.chaos.widget.dialog.bocdialog.base.BaseInstanceDialog;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnBackPressedListener;

/**
 * Created on 2021/10/21
 *
 * @author zsp
 * @desc LottieAnimationView 加载对话框
 */
public class LottieAnimationViewLoadingDialog extends BaseInstanceDialog {
    private LottieAnimationView lottieAnimationViewLoadingDialogLav;
    private OnBackPressedListener onBackPressedListener;

    /**
     * constructor
     *
     * @param context        上下文
     * @param selfThemeResId 自身主题资源 ID
     */
    private LottieAnimationViewLoadingDialog(Context context, int selfThemeResId) {
        super(context, selfThemeResId);
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.dialog_lottie_animation_view_loading;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        lottieAnimationViewLoadingDialogLav = view.findViewById(R.id.lottieAnimationViewLoadingDialogLav);
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {

    }

    /**
     * 初始数据
     */
    @Override
    protected void initData() {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = DensityUtils.dipToPxByFloat(context, 80);
        layoutParams.height = DensityUtils.dipToPxByFloat(context, 80);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (null != onBackPressedListener) {
            onBackPressedListener.backPressed();
            onBackPressedListener = null;
        }
    }

    /**
     * 设置动画
     *
     * @param assetName 资产名
     *                  如 "camera.json"
     */
    private void setAnimation(String assetName) {
        if (TextUtils.isEmpty(assetName)) {
            return;
        }
        LottieKit lottieKit = new LottieKit();
        lottieKit.useWithAsset(lottieAnimationViewLoadingDialogLav, assetName, ValueAnimator.INFINITE);
    }

    /**
     * 设置回退按压监听
     *
     * @param onBackPressedListener 回退按压监听
     */
    private void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public static class Builder {
        private final LottieAnimationViewLoadingDialog lottieAnimationViewLoadingDialog;

        public Builder(Context context, int selfThemeResId) {
            this.lottieAnimationViewLoadingDialog = new LottieAnimationViewLoadingDialog(context, selfThemeResId);
        }

        public Builder setAnimation(String assetName) {
            lottieAnimationViewLoadingDialog.setAnimation(assetName);
            return this;
        }

        public Builder setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
            lottieAnimationViewLoadingDialog.setOnBackPressedListener(onBackPressedListener);
            return this;
        }

        public LottieAnimationViewLoadingDialog build() {
            return lottieAnimationViewLoadingDialog;
        }
    }
}
