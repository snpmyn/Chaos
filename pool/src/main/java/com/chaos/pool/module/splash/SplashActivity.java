package com.chaos.pool.module.splash;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.chaos.pool.R;
import com.chaos.pool.base.BasePoolActivity;
import com.chaos.pool.module.splash.kit.SplashActivityKit;
import com.chaos.util.java.animation.AnimationManager;
import com.chaos.util.java.screen.ScreenUtils;

/**
 * @desc: 闪屏页
 * @author: zsp
 * @date: 2021/9/16 3:40 下午
 */
public class SplashActivity extends BasePoolActivity {
    TextView splashActivityTv;
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
        return R.layout.activity_splash;
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
        splashActivityTv = findViewById(R.id.splashActivityTv);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        AnimationManager.xyScaleAlphaShow(splashActivityTv, 1000, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler(getMainLooper()).postDelayed(() -> splashActivityKit.execute(SplashActivity.this), 500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}