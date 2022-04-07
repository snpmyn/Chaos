package widget.lottie;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.lottie.LottieKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;

/**
 * @desc: LottieComposition 页
 * @author: zsp
 * @date: 2021/9/18 10:08 上午
 */
public class LottieCompositionActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lottieCompositionActivityLavOne)
    LottieAnimationView lottieCompositionActivityLavOne;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lottieCompositionActivityLavTwo)
    LottieAnimationView lottieCompositionActivityLavTwo;
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
        return R.layout.activity_lottie_composition;
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
        lottieKit.useWithLottieTaskFromAsset(this, lottieCompositionActivityLavOne, "EmojiWink.json", ValueAnimator.INFINITE, null);
        // 二
        lottieKit.useWithLottieTaskFromRawResId(this, lottieCompositionActivityLavTwo, R.raw.jolly_walker, ValueAnimator.INFINITE, null);
    }
}