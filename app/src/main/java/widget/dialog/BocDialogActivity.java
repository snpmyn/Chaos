package widget.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.chaos.R;

import base.BaseActivity;
import butterknife.ButterKnife;
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
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_boc_dialog);
        ButterKnife.bind(this);
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
            R.id.bocDialogActivityBtnCanCancelLoadingDialog})
    public void onViewClicked(View view) {
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
            default:
                break;
        }
    }
}