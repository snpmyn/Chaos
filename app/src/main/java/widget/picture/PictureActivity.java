package widget.picture;

import android.annotation.SuppressLint;
import android.view.View;

import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.picture.pulse.PulseView;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.BindView;
import value.ChaosMagic;

/**
 * @desc: 图片页
 * @author: zsp
 * @date: 2021/9/3 3:15 下午
 */
public class PictureActivity extends BaseActivity implements View.OnClickListener, PulseView.PulseListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pictureActivityPv)
    PulseView pictureActivityPv;
    private int counter;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_picture;
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
        pictureActivityPv.setPulseListener(this);
        pictureActivityPv.setOnClickListener(this);
    }

    /**
     * 开始逻辑
     */
    @Override
    protected void startLogic() {

    }

    @Override
    public void onClick(View v) {
        if (counter++ % ChaosMagic.INT_TWO == 0) {
            pictureActivityPv.startPulse();
        } else {
            pictureActivityPv.finishPulse();
        }
    }

    /**
     * OnStart trigger when we start draw pulse.
     */
    @Override
    public void onStartPulse() {
        ToastKit.showShort(getString(R.string.startPulse));
    }

    /**
     * OnFinish trigger when all of pulse models reached their max fraction
     */
    @Override
    public void onFinishPulse() {
        ToastKit.showShort(getString(R.string.finshPulse));
    }
}