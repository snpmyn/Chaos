package widget.banner;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: 轮播主页
 * @author: zsp
 * @date: 2021/9/30 2:49 下午
 */
public class BannerHomeActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_banner_home;
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
    @OnClick({R.id.bannerHomeActivityMbBannerOne, R.id.bannerHomeActivityMbBannerTwo})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 轮播一页
            case R.id.bannerHomeActivityMbBannerOne:
                IntentJump.getInstance().jump(null, this, false, BannerOneActivity.class);
                break;
            // 轮播二页
            case R.id.bannerHomeActivityMbBannerTwo:
                IntentJump.getInstance().jump(null, this, false, BannerTwoActivity.class);
                break;
            default:
                break;
        }
    }
}