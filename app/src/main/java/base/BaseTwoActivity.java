package base;

import android.os.Bundle;

import com.chaos.pool.base.BasePoolTwoActivity;

import butterknife.ButterKnife;

/**
 * Created on 2021/10/22
 *
 * @author zsp
 * @desc BaseTwoActivity
 */
public abstract class BaseTwoActivity extends BasePoolTwoActivity {
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
