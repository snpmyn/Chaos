package widget.module.one.mine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chaos.util.java.rxbus.RxBus;
import com.chaos.widget.appbarlayout.listener.BaseAppBarLayoutStateChangeListener;
import com.example.chaos.R;
import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

import base.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import value.ChaosRxBusConstant;

/**
 * Created on 2020/12/17
 *
 * @author zsp
 * @desc 我的子碎片
 */
public class MineChildFragment extends BaseFragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mineChildFragmentTvName)
    TextView mineChildFragmentTvName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mineChildFragmentTvNameInMaterialToolbar)
    TextView mineChildFragmentTvNameInMaterialToolbar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mineChildFragmentAbl)
    AppBarLayout mineChildFragmentAbl;

    public static @NotNull MineChildFragment newInstance() {
        Bundle bundle = new Bundle();
        MineChildFragment fragment = new MineChildFragment();
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
        return R.layout.fragment_mine_child;
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
        return new String[]{"push_browse_account_id", "我的子碎片", "我的子碎片", "MineChildFragment-key", "MineChildFragment-value"};
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

    }

    /**
     * EventBus 反注册
     * <p>
     * onCreateView 注册。
     */
    @Override
    protected void eventBusUnregister() {

    }

    private void initConfiguration() {
        mineChildFragmentTvName.setText(MineChildFragment.newInstance().getClass().getSimpleName());
    }

    private void setListener() {
        mineChildFragmentAbl.addOnOffsetChangedListener(new BaseAppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    // 折叠
                    mineChildFragmentTvNameInMaterialToolbar.setText(MineChildFragment.newInstance().getClass().getSimpleName());
                } else {
                    // 中间
                    mineChildFragmentTvNameInMaterialToolbar.setText("");
                }
            }
        });
    }

    private void startLogic() {

    }

    @OnClick({R.id.mineChildFragmentIvHeadPortrait})
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.mineChildFragmentIvHeadPortrait) {
            RxBus.get().post(ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_BOTTOM_NAVIGATION_VIEW, ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_HIDE_BOTTOM_NAVIGATION_VIEW_CODE);
            start(HelpFeedbackFragment.newInstance());
        }
    }
}
