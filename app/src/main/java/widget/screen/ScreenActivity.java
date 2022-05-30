package widget.screen;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.screen.kit.ScreenActivityKit;

/**
 * @desc: 筛选页
 * @author: zsp
 * @date: 2022/5/26 2:42 下午
 */
public class ScreenActivity extends BaseActivity {
    /**
     * 筛选页配套元件
     */
    private ScreenActivityKit screenActivityKit;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_screen;
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
        screenActivityKit = new ScreenActivityKit();
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

    @OnClick({R.id.screenActivityMb})
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.screenActivityMb) {
            screenActivityKit.screen(this);
        }
    }
}