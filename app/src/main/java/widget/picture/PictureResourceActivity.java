package widget.picture;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chaos.util.java.drawable.DrawableUtils;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.util.java.view.ViewUtils;
import com.example.chaos.R;

import application.App;
import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @desc: 图片资源页
 * @author: zsp
 * @date: 2021/9/26 3:15 下午
 */
public class PictureResourceActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pictureResourceActivityIv)
    ImageView pictureResourceActivityIv;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_picture_resource;
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
        ViewUtils.doubleClickCheck(pictureResourceActivityIv, () -> ToastKit.showShort(getString(R.string.doubleClick)));
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.pictureResourceActivityMbLocalLoad, R.id.pictureResourceActivityMbNetworkLoad})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 本地加载
            case R.id.pictureResourceActivityMbLocalLoad:
                pictureResourceActivityIv.setImageResource(DrawableUtils.getDrawableResIdByName(App.getAppInstance(), "icon_background", R.mipmap.icon));
                break;
            // 网络加载
            case R.id.pictureResourceActivityMbNetworkLoad:
                ToastKit.showShort(getString(R.string.notRealizeYet));
                break;
            default:
                break;
        }
    }
}