package widget.module;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.chaos.util.java.rxbus.RxBus;
import com.chaos.util.java.rxbus.annotation.Subscribe;
import com.chaos.util.java.rxbus.annotation.Tag;
import com.chaos.util.java.rxbus.thread.EventThread;
import com.chaos.util.java.view.ViewUtils;
import com.example.chaos.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import base.BaseActivity;
import base.BaseFragment;
import butterknife.BindView;
import timber.log.Timber;
import value.ChaosRxBusConstant;
import widget.module.homepage.HomePageFragment;
import widget.module.mine.MineFragment;

/**
 * @desc: 模块一页
 * @author: zsp
 * @date: 2021/10/13 2:31 下午
 */
public class ModuleOneActivity extends BaseActivity implements BaseFragment.OnBackToFirstListener {
    /**
     * Fragment
     */
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    private final BaseFragment[] supportFragments = new BaseFragment[2];
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.moduleOneActivityBnv)
    BottomNavigationView mainActivityBnv;
    private int prePosition;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_module_one;
    }

    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     * @param layoutResId        布局资源 ID
     */
    @Override
    protected void initContentView(Bundle savedInstanceState, int layoutResId) {
        super.initContentView(savedInstanceState, layoutResId);
        RxBus.get().register(this);
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        mainActivityBnv.setItemIconTintList(null);
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
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void setListener() {
        mainActivityBnv.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottomNavigationViewMenuHomePage:
                    showHideFragmentExecute(0, prePosition);
                    break;
                case R.id.bottomNavigationViewMenuMine:
                    showHideFragmentExecute(1, prePosition);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        fragmentShow();
    }

    /**
     * Fragment 显示
     */
    private void fragmentShow() {
        BaseFragment firstFragment = findFragment(HomePageFragment.class);
        if (null == firstFragment) {
            supportFragments[FIRST] = HomePageFragment.newInstance();
            supportFragments[SECOND] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.moduleOneActivityFl, FIRST, supportFragments[FIRST], supportFragments[SECOND]);
        } else {
            // 此处库已做 Fragment 恢复（无需额外处理，无重叠问题）
            // 此处需拿到 mFragments 引用
            supportFragments[FIRST] = firstFragment;
            supportFragments[SECOND] = findFragment(MineFragment.class);
        }
    }

    private void clickShow(int position, int prePosition) {
        Timber.d("show %s hide %s ", position, prePosition);
    }

    public void showHideFragmentExecute(int position, int prePosition) {
        clickShow(position, prePosition);
        showHideFragment(supportFragments[position], supportFragments[prePosition]);
        this.prePosition = position;
    }

    /**
     * 回第一 Fragment
     */
    @Override
    public void onBackToFirstFragment() {
        mainActivityBnv.getMenu().findItem(R.id.bottomNavigationViewMenuHomePage).setChecked(true);
        showHideFragmentExecute(0, prePosition);
        prePosition = 0;
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_BOTTOM_NAVIGATION_VIEW)})
    public void mainActivityBottomNavigationView(@NotNull Integer integer) {
        switch (integer) {
            // 隐底导航视图
            case ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_HIDE_BOTTOM_NAVIGATION_VIEW_CODE:
                ViewUtils.hideView(mainActivityBnv, View.GONE);
                break;
            // 显底导航视图
            case ChaosRxBusConstant.MODULE_ONE_ACTIVITY_$_SHOW_BOTTOM_NAVIGATION_VIEW_CODE:
                ViewUtils.showView(mainActivityBnv);
                break;
            default:
                break;
        }
    }

    /**
     * onBackPressedSupport
     * <p>
     * 回调时机为 Activity 回退栈内 Fragment 数小等 1 时默 finish Activity。
     * 尽量复写该法而非 onBackPress() 保 SupportFragment 内 onBackPressedSupport() 回退事件正常执行。
     */
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}