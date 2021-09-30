package widget.banner;

import android.annotation.SuppressLint;

import com.example.chaos.R;
import com.youth.banner.Banner;

import base.BaseActivity;
import butterknife.BindView;
import widget.banner.kit.BannerOneActivityKit;

/**
 * @desc: 轮播一页
 * @author: zsp
 * @date: 2021/9/30 2:54 下午
 */
public class BannerOneActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bannerOneActivityBanner)
    Banner bannerOneActivityBanner;
    /**
     * 轮播一页配套元件
     */
    private BannerOneActivityKit bannerOneActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_banner_one;
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
        bannerOneActivityKit = new BannerOneActivityKit();
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
        bannerOneActivityKit.banner(bannerOneActivityBanner);
    }
}