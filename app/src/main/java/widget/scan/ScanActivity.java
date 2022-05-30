package widget.scan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chaos.widget.transition.kit.TransitionKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;
import widget.scan.kit.ScanActivityKit;

/**
 * @desc: 扫描页
 * @author: zsp
 * @date: 2021/9/7 2:42 下午
 */
public class ScanActivity extends BaseActivity {
    private ScanActivityKit scanActivityKit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TransitionKit.getInstance().endPageSetting(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 布局资源 ID
     *
     * @return 布局资源 ID
     */
    @Override
    protected int layoutResId() {
        return R.layout.activity_scan;
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
        scanActivityKit = new ScanActivityKit();
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
        scanActivityKit.requestPermission(this);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.scanActivityMbScanQrCode, R.id.scanActivityMbScanBarCode})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // 扫描二维码
            case R.id.scanActivityMbScanQrCode:
                scanActivityKit.scanQrCode(this);
                break;
            // 扫描条形码
            case R.id.scanActivityMbScanBarCode:
                scanActivityKit.scanBarCode(this);
                break;
            default:
                break;
        }
    }
}