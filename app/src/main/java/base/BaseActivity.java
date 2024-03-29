package base;

import android.os.Bundle;

import com.chaos.pool.base.BasePoolActivity;

import butterknife.ButterKnife;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc BaseActivity
 */
public abstract class BaseActivity extends BasePoolActivity {
    /**
     * 加载视图
     *
     * @param savedInstanceState 状态保存
     * @param layoutResId        布局资源 ID
     */
    @Override
    protected void initContentView(Bundle savedInstanceState, int layoutResId) {
        setContentView(layoutResId);
        ButterKnife.bind(this);
    }
}
