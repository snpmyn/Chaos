package com.chaos.pool.module.splash.kit;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.chaos.pool.R;
import com.chaos.pool.value.PoolConstant;
import com.chaos.util.java.storage.mmkv.MmkvKit;

/**
 * Created on 2022/5/6
 *
 * @author zsp
 * @desc 闪屏二页配套元件
 */
public class SplashTwoActivityKit {
    /**
     * 执行
     *
     * @param appCompatActivity   活动
     * @param lottieAnimationView 控件
     * @param splashActivityKit   闪屏页配套元件
     */
    public void execute(AppCompatActivity appCompatActivity, LottieAnimationView lottieAnimationView, SplashActivityKit splashActivityKit) {
        String assetName = MmkvKit.defaultMmkv().decodeString(PoolConstant.SPLASH_$_ANIMATION);
        if (MmkvKit.defaultMmkv().decodeBool(PoolConstant.SPLASH_$_USE_DEFAULT_ANIMATION) || TextUtils.isEmpty(assetName)) {
            assetName = "lottie_animation_pool_splash";
        }
        LottieKit lottieKit = new LottieKit();
        lottieKit.useWithAsset(lottieAnimationView, assetName + ".json", ValueAnimator.INFINITE, null);
        new Handler(appCompatActivity.getMainLooper()).postDelayed(() -> {
            LottieKit lottieKit1 = new LottieKit();
            lottieKit1.cancelAnimation(lottieAnimationView);
            splashActivityKit.execute(appCompatActivity);
        }, Long.parseLong(appCompatActivity.getString(R.string.PoolSplashDuration)));
    }
}
