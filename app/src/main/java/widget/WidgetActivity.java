package widget;

import android.view.View;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.dialog.BocDialogActivity;

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

    @OnClick({R.id.widgetActivityBtnBocDialog})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.widgetActivityBtnBocDialog) {
            IntentJump.getInstance().jump(null, this, false, BocDialogActivity.class);
        }
    }
}