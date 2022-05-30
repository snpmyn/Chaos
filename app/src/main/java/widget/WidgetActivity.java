package widget;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.transition.kit.TransitionKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import widget.kit.WidgetActivityKit;

/**
 * @desc: 组件页
 * @author: zsp
 * @date: 2021/4/1 2:04 PM
 */
public class WidgetActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.widgetActivityRv)
    RecyclerView widgetActivityRv;
    /**
     * 组件页配套元件
     */
    private WidgetActivityKit widgetActivityKit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TransitionKit.getInstance().startPageSetting(this);
        TransitionKit.getInstance().endPageSetting(this);
        super.onCreate(savedInstanceState);
    }

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
        widgetActivityKit = new WidgetActivityKit();
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
        widgetActivityKit.display(this, widgetActivityRv);
    }
}