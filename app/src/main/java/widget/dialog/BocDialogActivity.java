package widget.dialog;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.dialog.kit.BocDialogActivityKit;

/**
 * @desc: BOC 对话框页
 * @author: zsp
 * @date: 2021/4/1 5:03 PM
 */
public class BocDialogActivity extends BaseActivity {
    private BocDialogActivityKit bocDialogActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_boc_dialog;
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
        bocDialogActivityKit = new BocDialogActivityKit();
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
    @OnClick({R.id.bocDialogActivityBtnRightAngleMessageDialogWithNoTitle,
            R.id.bocDialogActivityBtnRightAngleMessageDialogWithTitle,
            R.id.bocDialogActivityBtnRoundCornerMessageDialogWithNoTitle,
            R.id.bocDialogActivityBtnRoundCornerMessageDialogWithTitle,
            R.id.bocDialogActivityBtnCommonLoadingDialog,
            R.id.bocDialogActivityBtnCanCancelLoadingDialog,
            R.id.bocDialogActivityBtnLottieAnimationViewLoadingDialog,
            R.id.bocDialogActivityBtnLottieAnimationViewOtherDialog})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 直角消息对话框（无标题）
            case R.id.bocDialogActivityBtnRightAngleMessageDialogWithNoTitle:
                bocDialogActivityKit.rightAngleMessageDialogWithNoTitle(this);
                break;
            // 直角消息对话框（有标题）
            case R.id.bocDialogActivityBtnRightAngleMessageDialogWithTitle:
                bocDialogActivityKit.rightAngleMessageDialogWithTitle(this);
                break;
            // 圆角消息对话框（无标题）
            case R.id.bocDialogActivityBtnRoundCornerMessageDialogWithNoTitle:
                bocDialogActivityKit.roundCornerMessageDialogWithNoTitle(this);
                break;
            // 圆角消息对话框（有标题）
            case R.id.bocDialogActivityBtnRoundCornerMessageDialogWithTitle:
                bocDialogActivityKit.roundCornerMessageDialogWithTitle(this);
                break;
            // 普通加载对话框
            case R.id.bocDialogActivityBtnCommonLoadingDialog:
                bocDialogActivityKit.commonLoadingDialog(this);
                break;
            // 可取消加载对话框
            case R.id.bocDialogActivityBtnCanCancelLoadingDialog:
                bocDialogActivityKit.canCancelLoadingDialog(this);
                break;
            // LottieAnimationView 加载对话框
            case R.id.bocDialogActivityBtnLottieAnimationViewLoadingDialog:
                bocDialogActivityKit.lottieAnimationViewLoadingDialog(this);
                break;
            // LottieAnimationView 其它对话框
            case R.id.bocDialogActivityBtnLottieAnimationViewOtherDialog:
                bocDialogActivityKit.lottieAnimationViewOtherDialog(this);
                break;
            default:
                break;
        }
    }
}