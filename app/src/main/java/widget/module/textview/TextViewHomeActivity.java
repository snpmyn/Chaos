package widget.module.textview;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: TextView 主页
 * @author: zsp
 * @date: 2022/3/23 4:11 下午
 */
public class TextViewHomeActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_text_view_home;
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

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.textViewHomeActivityMbHorizontalTextView, R.id.textViewHomeActivityMbVerticalTextView})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 水平 TextView 页
            case R.id.textViewHomeActivityMbHorizontalTextView:
                IntentJump.getInstance().jump(null, this, false, HorizontalTextViewActivity.class);
                break;
            // 垂直 TextView 页
            case R.id.textViewHomeActivityMbVerticalTextView:
                IntentJump.getInstance().jump(null, this, false, VerticalTextViewActivity.class);
                break;
            default:
                break;
        }
    }
}