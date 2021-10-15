package widget.module.mine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.chaos.util.java.rxbus.RxBus;
import com.example.chaos.R;
import com.google.android.material.appbar.MaterialToolbar;

import org.jetbrains.annotations.NotNull;

import base.BaseFragment;
import butterknife.BindView;
import value.ChaosRxBusConstant;

/**
 * Created on 2021/2/2
 *
 * @author zsp
 * @desc 帮助反馈碎片
 */
public class HelpFeedbackFragment extends BaseFragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.helpFeedbackFragmentMt)
    MaterialToolbar helpFeedbackFragmentMt;

    public static @NotNull HelpFeedbackFragment newInstance() {
        Bundle bundle = new Bundle();
        HelpFeedbackFragment fragment = new HelpFeedbackFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.fragment_help_feedback;
    }

    /**
     * 第一 Fragment 否
     *
     * @return 第一 Fragment 否
     */
    @Override
    protected boolean areFirstFragment() {
        return false;
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
        return new String[]{"push_browse_account_id", "帮助反馈碎片", "帮助反馈碎片", "HelpFeedbackFragment-key", "HelpFeedbackFragment-value"};
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
     * Fragment 对用户可见时调
     */
    @Override
    protected void visibleToUser() {

    }

    /**
     * 开始加载
     * <p>
     * 某些场景需懒加载，如 FragmentAdapter 懒加载、同级 Fragment 切换懒加载。
     * 库自 0.8 提供 onLazyInitView(Bundle saveInstanceState) 使用。
     */
    @Override
    protected void startLoadOnLazyInitView() {

    }

    /**
     * 开始加载
     * <p>
     * 此处设 Listener、各 Adapter、请求数据等。
     * onDestroyView 释放。
     */
    @Override
    protected void startLoadOnEnterAnimationEnd() {
        initConfiguration();
        setListener();
        startLogic();
    }

    /**
     * Fragment 对用户不可见时调
     */
    @Override
    protected void invisibleToUser() {
        hideSoftInput();
    }

    private void initConfiguration() {

    }

    private void setListener() {
        helpFeedbackFragmentMt.setNavigationOnClickListener(v -> fragmentationSupportActivity.onBackPressed());
    }

    private void startLogic() {

    }

    /**
     * 处理回退事件
     * <p>
     * 返 true 消费该事件，不再向上传递。
     * 返 false 向上最终传递至 Fragment 宿主 Activity。此时宿主 Activity 复写 onBackPressedSupport 则执行，没复写不执行。
     * Fragment 宿主 Activity 之基类复写 onKeyUp 时同执行。
     * MainActivity 于该法处理。
     * SplashActivity 与 LoginActivity 于 BaseActivity 之 onKeyUp 处理。
     *
     * @return boolean
     */
    @Override
    public boolean onBackPressedSupport() {
        pop();
        RxBus.get().post(ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_BOTTOM_NAVIGATION_VIEW, ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_SHOW_BOTTOM_NAVIGATION_VIEW_CODE);
        return true;
    }
}
