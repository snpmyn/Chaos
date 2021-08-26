package widget.choose;

import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.choose.provincialandurbanlinkage.ProvincialAndUrbanLinkage;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.choose.kit.ChooseActivityKit;

/**
 * @desc: 选择页
 * @author: zsp
 * @date: 2021/8/24 3:28 下午
 */
public class ChooseActivity extends BaseActivity {
    private ChooseActivityKit chooseActivityKit;
    private ProvincialAndUrbanLinkage provincialAndUrbanLinkage;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_choose;
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
        chooseActivityKit = new ChooseActivityKit();
        provincialAndUrbanLinkage = new ProvincialAndUrbanLinkage(this);
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

    @OnClick({R.id.widgetActivityBtnProvincialAndUrbanLinkage})
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.widgetActivityBtnProvincialAndUrbanLinkage) {
            chooseActivityKit.provincialAndUrbanLinkage(provincialAndUrbanLinkage, ToastKit::showShort);
        }
    }
}