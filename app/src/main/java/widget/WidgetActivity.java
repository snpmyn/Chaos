package widget;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.banner.BannerHomeActivity;
import widget.choose.ChooseActivity;
import widget.dialog.BocDialogActivity;
import widget.grid.GridActivity;
import widget.lottie.LottieHomeActivity;
import widget.module.ModuleOneActivity;
import widget.module.ModuleTwoActivity;
import widget.money.MoneyActivity;
import widget.picture.PictureHomeActivity;
import widget.property.PropertyActivity;
import widget.pudding.PuddingActivity;
import widget.scan.ScanActivity;
import widget.search.SearchActivity;

/**
 * @desc: 组件页
 * @author: zsp
 * @date: 2021/4/1 2:04 PM
 */
public class WidgetActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_widget;
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
    @OnClick({R.id.widgetActivityBtnBocDialog,
            R.id.widgetActivityBtnMoney,
            R.id.widgetActivityBtnChoose,
            R.id.widgetActivityBtnSearch,
            R.id.widgetActivityBtnPicture,
            R.id.widgetActivityBtnScan,
            R.id.widgetActivityBtnLottie,
            R.id.widgetActivityBtnPudding,
            R.id.widgetActivityBtnGrid,
            R.id.widgetActivityBtnProperty,
            R.id.widgetActivityBtnBanner,
            R.id.widgetActivityBtnModuleOne,
            R.id.widgetActivityBtnModuleTwo})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // BOC 对话框页
            case R.id.widgetActivityBtnBocDialog:
                IntentJump.getInstance().jump(null, this, false, BocDialogActivity.class);
                break;
            // 金额页
            case R.id.widgetActivityBtnMoney:
                IntentJump.getInstance().jump(null, this, false, MoneyActivity.class);
                break;
            // 选择页
            case R.id.widgetActivityBtnChoose:
                IntentJump.getInstance().jump(null, this, false, ChooseActivity.class);
                break;
            // 搜索页
            case R.id.widgetActivityBtnSearch:
                IntentJump.getInstance().jump(null, this, false, SearchActivity.class);
                break;
            // 图片主页
            case R.id.widgetActivityBtnPicture:
                IntentJump.getInstance().jump(null, this, false, PictureHomeActivity.class);
                break;
            // 扫描页
            case R.id.widgetActivityBtnScan:
                IntentJump.getInstance().jump(null, this, false, ScanActivity.class);
                break;
            // Lottie 主页
            case R.id.widgetActivityBtnLottie:
                IntentJump.getInstance().jump(null, this, false, LottieHomeActivity.class);
                break;
            // Pudding 页
            case R.id.widgetActivityBtnPudding:
                IntentJump.getInstance().jump(null, this, false, PuddingActivity.class);
                break;
            // 网格页
            case R.id.widgetActivityBtnGrid:
                IntentJump.getInstance().jump(null, this, false, GridActivity.class);
                break;
            // 属性页
            case R.id.widgetActivityBtnProperty:
                IntentJump.getInstance().jump(null, this, false, PropertyActivity.class);
                break;
            // 轮播主页
            case R.id.widgetActivityBtnBanner:
                IntentJump.getInstance().jump(null, this, false, BannerHomeActivity.class);
                break;
            //  模块一页
            case R.id.widgetActivityBtnModuleOne:
                IntentJump.getInstance().jump(null, this, false, ModuleOneActivity.class);
                break;
            //  模块二页
            case R.id.widgetActivityBtnModuleTwo:
                IntentJump.getInstance().jump(null, this, false, ModuleTwoActivity.class);
                break;
            default:
                break;
        }
    }
}