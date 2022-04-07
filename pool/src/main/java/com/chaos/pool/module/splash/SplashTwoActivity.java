package com.chaos.pool.module.splash;

import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.chaos.pool.R;
import com.chaos.pool.base.BasePoolActivity;
import com.chaos.pool.module.splash.kit.SplashActivityKit;
import com.chaos.util.java.screen.ScreenUtils;

/**
 * @desc: 闪屏二页
 * @author: zsp
 * @date: 2022/4/2 11:03 上午
 */
public class SplashTwoActivity extends BasePoolActivity {
    private LottieAnimationView splashTwoActivityLav;
    /**
     * 闪屏页配套元件
     */
    private SplashActivityKit splashActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_splash_two;
    }

    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     * @param layoutResId        布局资源 ID
     */
    @Override
    protected void initContentView(Bundle savedInstanceState, int layoutResId) {
        ScreenUtils.hideNavigationWithoutCanShowInScroll(this);
        // 处理 PreviewWindow 背景避图直占内存（过渡绘制）
        getWindow().setBackgroundDrawable(null);
        setContentView(layoutResId);
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        splashTwoActivityLav = findViewById(R.id.splashTwoActivityLav);
    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        splashActivityKit = new SplashActivityKit();
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {

    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        new Handler(getMainLooper()).postDelayed(() -> {
            LottieKit lottieKit = new LottieKit();
            lottieKit.cancelAnimation(splashTwoActivityLav);
            splashActivityKit.execute(SplashTwoActivity.this);
        }, Long.parseLong(getString(R.string.PoolSplashDuration)));
    }
}