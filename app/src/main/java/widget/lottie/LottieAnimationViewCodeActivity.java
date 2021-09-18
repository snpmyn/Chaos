package widget.lottie;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;

/**
 * @desc: LottieAnimationViewCode 页
 * @author: zsp
 * @date: 2021/9/18 10:08 上午
 */
public class LottieAnimationViewCodeActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lottieAnimationViewCodeActivityOne)
    LottieAnimationView lottieAnimationViewCodeActivityOne;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lottieAnimationViewCodeActivityTwo)
    LottieAnimationView lottieAnimationViewCodeActivityTwo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lottieAnimationViewCodeActivityThree)
    LottieAnimationView lottieAnimationViewCodeActivityThree;
    /**
     * Lottie 配套元件
     */
    private LottieKit lottieKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_lottie_animation_view_code;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {

    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        lottieKit = new LottieKit();
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
        execute();
    }

    /**
     * 执行
     */
    private void execute() {
        // 一
        lottieKit.useWithAsset(lottieAnimationViewCodeActivityOne, "camera.json", ValueAnimator.INFINITE);
        // 二
        lottieKit.useWithRaw(lottieAnimationViewCodeActivityTwo, R.raw.hamburger_arrow, ValueAnimator.INFINITE);
        // 三
        lottieKit.useWithAssetAndImageAsset(lottieAnimationViewCodeActivityThree, "splash.json", "images_splash/", ValueAnimator.INFINITE);
    }
}