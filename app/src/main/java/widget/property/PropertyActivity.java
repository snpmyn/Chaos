package widget.property;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import widget.property.kit.PropertyActivityKit;

/**
 * @desc: 属性页
 * @author: zsp
 * @date: 2021/9/26 3:40 下午
 */
public class PropertyActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.propertyActivityTv)
    TextView propertyActivityTv;
    /**
     * 属性页配套元件
     */
    private PropertyActivityKit propertyActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_property;
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
        propertyActivityKit = new PropertyActivityKit();
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

    @OnClick({R.id.propertyActivityMbGetProperty})
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.propertyActivityMbGetProperty) {
            propertyActivityKit.getProperty(this, propertyActivityTv);
        }
    }
}