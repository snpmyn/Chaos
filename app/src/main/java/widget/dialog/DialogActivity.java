package widget.dialog;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.chaos.util.java.intent.IntentJump;
import com.example.chaos.R;

import base.BaseActivity;
import butterknife.OnClick;

/**
 * @desc: 对话框页
 * @author: zsp
 * @date: 2022/8/15 4:06 PM
 */
public class DialogActivity extends BaseActivity {
    @Override
    protected int layoutResId() {
        return R.layout.activity_dialog;
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

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.dialogActivityMbBocDialog, R.id.dialogActivityMbSweetAlertDialog})
    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            // BOC 对话框页
            case R.id.dialogActivityMbBocDialog:
                IntentJump.getInstance().jump(null, this, false, BocDialogActivity.class);
                break;
            // SweetAlertDialog 页
            case R.id.dialogActivityMbSweetAlertDialog:
                IntentJump.getInstance().jump(null, this, false, SweetAlertDialogActivity.class);
                break;
            default:
                break;
        }
    }
}