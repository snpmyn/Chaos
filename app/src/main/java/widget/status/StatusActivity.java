package widget.status;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.widget.status.listener.BaseStatusListener;
import com.chaos.widget.status.manager.StatusManager;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @desc: 状态页
 * @author: zsp
 * @date: 2022/4/7 10:38 上午
 */
public class StatusActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.statusActivityRv)
    RecyclerView statusActivityRv;
    /**
     * 状态管理器
     */
    private StatusManager statusManager;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_status;
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
        statusManager = StatusManager.generate(statusActivityRv, new BaseStatusListener() {
            @Override
            public void setRetryEvent(View retryView) {

            }
        });
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
        statusManager.showEmpty();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.statusActivityMbEmptyView,
            R.id.statusActivityMbLoadingView,
            R.id.statusActivityMbErrorOneView,
            R.id.statusActivityMbErrorTwoView,
            R.id.statusActivityMbErrorThreeView})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 空视图
            case R.id.statusActivityMbEmptyView:
                statusManager.showEmpty();
                break;
            // 加载视图
            case R.id.statusActivityMbLoadingView:
                statusManager.showLoading();
                break;
            // 错误一视图
            case R.id.statusActivityMbErrorOneView:
                statusManager.showRetry(0);
                break;
            // 错误二视图
            case R.id.statusActivityMbErrorTwoView:
                statusManager.showRetry(1);
                break;
            // 错误三视图
            case R.id.statusActivityMbErrorThreeView:
                statusManager.showRetry(2);
                break;
            default:
                break;
        }
    }
}