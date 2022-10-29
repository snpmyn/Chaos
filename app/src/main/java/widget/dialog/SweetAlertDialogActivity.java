package widget.dialog;

import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.widget.dialog.sweetalertdialog.kit.SweetAlertDialogKit;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: SweetAlertDialog 页
 * @author: zsp
 * @date: 2022/8/15 4:06 PM
 */
public class SweetAlertDialogActivity extends BaseActivity {
    @Override
    protected int layoutResId() {
        return R.layout.activity_sweet_alert_dialog;
    }

    @Override
    protected void stepUi() {

    }

    @Override
    protected void initConfiguration() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void startLogic() {

    }

    @OnClick({R.id.sweetAlertDialogActivityMbUseOne})
    public void onViewClicked(@NonNull View view) {
        if (view.getId() == R.id.sweetAlertDialogActivityMbUseOne) {
            SweetAlertDialogKit.getInstance().stepDialog(this, this.getClass().getSimpleName(), this.getClass().getSimpleName(), getString(R.string.ensure), getString(R.string.cancel), R.drawable.ic_widgets_purple_500_24dp);
        }
    }
}