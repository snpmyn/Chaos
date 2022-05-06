package com.chaos.pool.module.splash;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.pool.R;
import com.chaos.pool.base.BasePoolActivity;
import com.chaos.pool.module.splash.kit.SplashActivityKit;
import com.chaos.pool.module.splash.kit.SplashTwoActivityKit;
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
     * 闪屏二页配套元件
     */
    private SplashTwoActivityKit splashTwoActivityKit;

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
        // 闪屏页配套元件
        splashActivityKit = new SplashActivityKit();
        // 闪屏二页配套元件
        splashTwoActivityKit = new SplashTwoActivityKit();
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
        splashTwoActivityKit.execute(this, splashTwoActivityLav, splashActivityKit);
    }
}