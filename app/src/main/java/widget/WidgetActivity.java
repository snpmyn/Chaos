package widget;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.choose.ChooseActivity;
import widget.dialog.BocDialogActivity;
import widget.money.MoneyActivity;
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
    @OnClick({R.id.widgetActivityBtnBocDialog, R.id.widgetActivityBtnMoney, R.id.widgetActivityBtnChoose, R.id.widgetActivityBtnSearch})
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
            default:
                break;
        }
    }
}