package widget.module.two;

import android.annotation.SuppressLint;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.chaos.util.java.activity.ActivitySuperviseManager;
import com.example.chaos.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import base.BaseTwoActivity;
import butterknife.BindView;

/**
 * @desc: 模块二页
 * @author: zsp
 * @date: 2021/10/15 3:56 下午
 */
public class ModuleTwoActivity extends BaseTwoActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.moduleTwoActivityMt)
    MaterialToolbar moduleTwoActivityMt;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.moduleTwoActivityBnv)
    BottomNavigationView moduleTwoActivityBnv;
    /**
     * NavController
     */
    private NavController navController;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_module_two;
    }

    /**
     * 初始控件
     */
    @Override
    protected void stepUi() {
        setSupportActionBar(moduleTwoActivityMt);
    }

    /**
     * 初始配置
     */
    @Override
    protected void initConfiguration() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.moduleTwoActivityFcv);
        if (null != navHostFragment) {
            navController = navHostFragment.getNavController();
        }
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ActivitySuperviseManager.getInstance().finishActivity(ModuleTwoActivity.class);
            }
        });
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(moduleTwoActivityBnv, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.moduleTwoActivityFcv).navigateUp();
    }
}