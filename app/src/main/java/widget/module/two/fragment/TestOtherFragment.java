package widget.module.two.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.chaos.R;

import base.BaseTwoFragment;
import butterknife.OnClick;
import widget.module.two.kit.TestOtherFragmentKit;

/**
 * Created on 2021/10/21
 *
 * @author zsp
 * @desc 测试其它碎片
 */
public class TestOtherFragment extends BaseTwoFragment {
    /**
     * 测试其它碎片配套元件
     */
    private TestOtherFragmentKit testOtherFragmentKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.fragment_test_other;
    }

    /**
     * 极光分析浏览事件参数
     * <p>
     * browseId – 浏览内容 ID（自定）
     * browseName – 浏览内容名（标题等，非空）
     * browseType – 浏览内容类型（如热点、汽车、财经等）
     * extMapKey – 扩展参数键
     * extMapValue – 扩展参数值
     *
     * @return 极光分析浏览事件参数
     */
    @Override
    protected String[] janalyticsBrowseEventParams() {
        return new String[]{"push_browse_account_id", "测试其它碎片", "测试其它碎片", "TestOtherFragment-key", "TestOtherFragment-value"};
    }

    /**
     * EventBus 注册
     * <p>
     * onDestroyView 反注册。
     */
    @Override
    protected void eventBusRegister() {

    }

    /**
     * 初始控件
     * <p>
     * 此处仅设 Toolbar 标题、返回箭头等轻量 UI 操作。
     *
     * @param view 视图
     */
    @Override
    protected void stepUi(View view) {

    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        testOtherFragmentKit = new TestOtherFragmentKit();
    }

    /**
     * 初始数据
     */
    @Override
    protected void initData() {

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
        testOtherFragmentKit.handle(this);
    }

    /**
     * EventBus 反注册
     * <p>
     * onCreateView 注册。
     */
    @Override
    protected void eventBusUnregister() {

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.testOneFragmentTvBackToTestOne)
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.testOneFragmentTvBackToTestOne) {
            Navigation.findNavController(baseView).navigateUp();
        }
    }
}

