package com.chaos.widget.dialog.bocdialog.result;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.chaos.util.java.density.DensityUtils;
import com.chaos.widget.R;
import com.chaos.widget.dialog.bocdialog.base.BaseInstanceDialog;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnBackPressedListener;

/**
 * Created on 2022/4/6
 *
 * @author zsp
 * @desc LottieAnimationView 结果对话框
 */
public class LottieAnimationViewResultDialog extends BaseInstanceDialog {
    private LottieAnimationView lottieAnimationViewLoadingAndResultDialogLav;
    private long duration;
    private OnBackPressedListener onBackPressedListener;

    /**
     * constructor
     *
     * @param context        上下文
     * @param selfThemeResId 自身主题资源 ID
     */
    private LottieAnimationViewResultDialog(Context context, int selfThemeResId) {
        super(context, selfThemeResId);
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.dialog_lottie_animation_view_loading_and_result;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        lottieAnimationViewLoadingAndResultDialogLav = view.findViewById(R.id.lottieAnimationViewLoadingAndResultDialogLav);
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
        lottieKit.useWithAsset(lottieAnimationViewLoadingAndResultDialogLav, assetName, ValueAnimator.INFINITE, null);
    }

    /**
     * 设置回退按压监听
     *
     * @param onBackPressedListener 回退按压监听
     */
    private void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void show() {
        super.show();
        new Handler(Looper.getMainLooper()).postDelayed(this::dismiss, duration);
    }

    public static class Builder {
        private final LottieAnimationViewResultDialog lottieAnimationViewResultDialog;

        public Builder(Context context, int selfThemeResId) {
            this.lottieAnimationViewResultDialog = new LottieAnimationViewResultDialog(context, selfThemeResId);
        }

        public Builder setAnimation(String assetName) {
            lottieAnimationViewResultDialog.setAnimation(assetName);
            return this;
        }

        public Builder setDuration(long duration) {
            lottieAnimationViewResultDialog.duration = duration;
            return this;
        }

        public Builder setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
            lottieAnimationViewResultDialog.setOnBackPressedListener(onBackPressedListener);
            return this;
        }

        public LottieAnimationViewResultDialog build() {
            return lottieAnimationViewResultDialog;
        }
    }
}

