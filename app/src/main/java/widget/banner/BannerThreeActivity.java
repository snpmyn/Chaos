package widget.banner;

import android.annotation.SuppressLint;

import com.chaos.widget.banner.view.ConvenientBanner;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import widget.banner.kit.BannerThreeActivityKit;

/**
 * @desc: 轮播三页
 * @author: zsp
 * @date: 2021/10/18 11:01 上午
 */
public class BannerThreeActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bannerThreeActivityCb)
    ConvenientBanner<Object> bannerThreeActivityCb;
    /**
     * 轮播三页配套元件
     */
    private BannerThreeActivityKit bannerThreeActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_banner_three;
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
        bannerThreeActivityKit = new BannerThreeActivityKit();
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
        bannerThreeActivityKit.banner(bannerThreeActivityCb, 3000, 1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bannerThreeActivityCb.autoTurningPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerThreeActivityCb.autoTurningResume();
    }
}