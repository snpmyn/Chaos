package widget.scan.kit;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.chaos.scan.ScanBarCodeActivity;
import com.chaos.scan.ScanQrCodeActivity;
import com.chaos.scan.listener.ScanCodeListener;
import com.chaos.util.java.intent.IntentJump;
import com.chaos.util.java.toast.ToastKit;
import com.example.chaos.R;
import com.permissionx.guolindev.PermissionX;

/**
 * Created on 2021/9/7
 *
 * @author zsp
 * @desc 扫描页配套元件
 */
public class ScanActivityKit {
    /**
     * 请求权限
     *
     * @param fragmentActivity FragmentActivity
     */
    public void requestPermission(FragmentActivity fragmentActivity) {
        PermissionX.init(fragmentActivity)
                .permissions(Manifest.permission.CAMERA)
                .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(deniedList, fragmentActivity.getString(R.string.suggestToAllowThesePermissions), fragmentActivity.getString(R.string.agree), fragmentActivity.getString(R.string.cancel)))
                .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList, fragmentActivity.getString(R.string.youNeedToAllowNecessaryPermissionInSettingManually), fragmentActivity.getString(R.string.ok), fragmentActivity.getString(R.string.cancel)))
                .explainReasonBeforeRequest()
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        requestPermission(fragmentActivity);
                    }
                });
    }

    /**
     * 扫描二维码
     *
     * @param appCompatActivity 活动
     */
    public void scanQrCode(AppCompatActivity appCompatActivity) {
        ScanQrCodeActivity.setScanCodeListener(new ScanCodeListener() {
            @Override
            public void onScanQrCodeSuccess(String result) {
                ToastKit.showShort(result);
            }

            @Override
            public void onScanQrCodeOpenCameraError() {
                ToastKit.showShort(appCompatActivity.getString(R.string.errorOpeningTheCamera));
            }
        });
        IntentJump.getInstance().jumpWithAnimation(null, appCompatActivity, false, ScanQrCodeActivity.class, 0, 0);
    }

    /**
     * 扫描条形码
     *
     * @param appCompatActivity 活动
     */
    public void scanBarCode(AppCompatActivity appCompatActivity) {
        ScanBarCodeActivity.setScanCodeListener(new ScanCodeListener() {
            @Override
            public void onScanQrCodeSuccess(String result) {
                ToastKit.showShort(result);
            }

            @Override
            public void onScanQrCodeOpenCameraError() {
                ToastKit.showShort(appCompatActivity.getString(R.string.errorOpeningTheCamera));
            }
        });
        IntentJump.getInstance().jumpWithAnimation(null, appCompatActivity, false, ScanBarCodeActivity.class, 0, 0);
    }
}
