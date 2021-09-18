package widget.lottie;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: Lottie 主页
 * @author: zsp
 * @date: 2021/9/18 9:58 上午
 */
public class LottieHomeActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_lottie_home;
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

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.lottieHomeActivityMbLottieAnimationViewXml,
            R.id.lottieHomeActivityMbLottieAnimationViewCode,
            R.id.lottieHomeActivityMbLottieComposition})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // LottieAnimationViewXml 页
            case R.id.lottieHomeActivityMbLottieAnimationViewXml:
                IntentJump.getInstance().jump(null, this, false, LottieAnimationViewXmlActivity.class);
                break;
            // LottieAnimationViewCode 页
            case R.id.lottieHomeActivityMbLottieAnimationViewCode:
                IntentJump.getInstance().jump(null, this, false, LottieAnimationViewCodeActivity.class);
                break;
            // LottieComposition 页
            case R.id.lottieHomeActivityMbLottieComposition:
                IntentJump.getInstance().jump(null, this, false, LottieCompositionActivity.class);
                break;
            default:
                break;
        }
    }
}