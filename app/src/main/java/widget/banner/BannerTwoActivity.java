package widget.banner;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.kotlin.widget.banner.view.BannerView;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import widget.banner.kit.BannerTwoActivityKit;

/**
 * @desc: 轮播二页
 * @author: zsp
 * @date: 2021/9/30 2:55 下午
 */
public class BannerTwoActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bannerTwoActivityBvInterval)
    BannerView bannerTwoActivityBvInterval;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bannerTwoActivityBvSmooth)
    BannerView bannerTwoActivityBvSmooth;
    /**
     * 轮播二页配套元件
     */
    private BannerTwoActivityKit bannerTwoActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_banner_two;
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
        bannerTwoActivityKit = new BannerTwoActivityKit();
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
        bannerTwoActivityKit.stepBannerData();
        bannerTwoActivityKit.intervalBanner(bannerTwoActivityBvInterval);
        bannerTwoActivityKit.smoothBanner(bannerTwoActivityBvSmooth);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.bannerTwoActivityMbClear, R.id.bannerTwoActivityMbAdd})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 清空
            case R.id.bannerTwoActivityMbClear:
                bannerTwoActivityKit.integerList.clear();
                bannerTwoActivityBvInterval.doRecreate();
                bannerTwoActivityBvSmooth.doRecreate();
                break;
            // 添加
            case R.id.bannerTwoActivityMbAdd:
                bannerTwoActivityKit.integerList.add(R.drawable.pulse_view);
                bannerTwoActivityBvInterval.doRecreate();
                bannerTwoActivityBvSmooth.doRecreate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bannerTwoActivityBvInterval.startAutoScroll();
        bannerTwoActivityBvSmooth.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerTwoActivityBvInterval.stopAutoScroll();
        bannerTwoActivityBvSmooth.stopAutoScroll();
    }
}