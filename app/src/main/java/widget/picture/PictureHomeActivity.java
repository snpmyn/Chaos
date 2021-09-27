package widget.picture;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: 图片主页
 * @author: zsp
 * @date: 2021/9/3 3:15 下午
 */
public class PictureHomeActivity extends BaseActivity {
    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_picture_home;
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
    @OnClick({R.id.pictureHomeActivityMbPicturePulse, R.id.pictureHomeActivityMbPictureResource})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 图片脉动页
            case R.id.pictureHomeActivityMbPicturePulse:
                IntentJump.getInstance().jump(null, this, false, PicturePulseActivity.class);
                break;
            // 图片资源页
            case R.id.pictureHomeActivityMbPictureResource:
                IntentJump.getInstance().jump(null, this, false, PictureResourceActivity.class);
                break;
            default:
                break;
        }
    }
}