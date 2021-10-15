package widget.module.mine;

import android.os.Bundle;
import android.view.View;

import com.example.chaos.R;

import org.jetbrains.annotations.NotNull;

import base.BaseFragment;
import widget.module.mine.fragment.MineChildFragment;

/**
 * Created on 2020/12/14
 *
 * @author zsp
 * @desc 我的碎片
 */
public class MineFragment extends BaseFragment {
    public static @NotNull MineFragment newInstance() {
        Bundle bundle = new Bundle();
        MineFragment fragment = new MineFragment();
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
        return R.layout.fragment_mine;
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
        return new String[]{"push_browse_account_id", "我的碎片", "我的碎片", "MineFragment-key", "MineFragment-value"};
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
        if (null == findChildFragment(MineChildFragment.class)) {
            loadRootFragment(R.id.mineFragmentFl, MineChildFragment.newInstance());
        }
    }

    /**
     * 开始加载
     * <p>
     * 此处设 Listener、各 Adapter、请求数据等。
     * onDestroyView 释放。
     */
    @Override
    protected void startLoadOnEnterAnimationEnd() {

    }

    /**
     * Fragment 对用户不可见时调
     */
    @Override
    protected void invisibleToUser() {

    }
}
