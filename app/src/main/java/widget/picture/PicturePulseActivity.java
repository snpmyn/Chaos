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
 * @desc: 图片脉动页
 * @author: zsp
 * @date: 2021/9/26 3:13 下午
 */
public class PicturePulseActivity extends BaseActivity implements View.OnClickListener, PulseView.PulseListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.picturePulseActivityPv)
    PulseView picturePulseActivityPv;
    private int counter;

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_picture_pulse;
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
        picturePulseActivityPv.setPulseListener(this);
        picturePulseActivityPv.setOnClickListener(this);
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
            picturePulseActivityPv.startPulse();
        } else {
            picturePulseActivityPv.finishPulse();
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
        ToastKit.showShort(getString(R.string.finishPulse));
    }
}