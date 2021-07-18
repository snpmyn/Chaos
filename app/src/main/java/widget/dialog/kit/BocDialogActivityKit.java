package widget.dialog.kit;

import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chaos.util.java.data.CaesarCipherUtils;
import com.chaos.util.java.sign.SignUtils;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.dialog.bocdialog.loading.CanCancelLoadingDialog;
import com.chaos.widget.dialog.bocdialog.loading.CommonLoadingDialog;
import com.chaos.widget.dialog.message.RightAngleMessageDialog;
import com.chaos.widget.dialog.message.RoundCornerMessageDialog;
import com.example.chaos.R;

/**
 * Created on 2021/4/1
 *
 * @author zsp
 * @desc BOC 对话框页配套元件
 */
public class BocDialogActivityKit {
    /**
     * 直角消息对话框（无标题）
     *
     * @param appCompatActivity 活动
     */
    public void rightAngleMessageDialogWithNoTitle(AppCompatActivity appCompatActivity) {
        RightAngleMessageDialog rightAngleMessageDialog = new RightAngleMessageDialog.Builder(appCompatActivity, 0)
                .setTitle(null)
                .setTitleColor(ContextCompat.getColor(appCompatActivity, R.color.fontInput))
                .setContent(appCompatActivity.getString(R.string.example))
                .setContentColor(ContextCompat.getColor(appCompatActivity, R.color.fontHint))
                .setContentHorizontalCenter()
                .setLeftButtonText(appCompatActivity.getString(R.string.cancel))
                .setRightButtonText(appCompatActivity.getString(R.string.ensure))
                .setLeftButtonDefaultSelect()
                .setOnRightAngleMessageDialogLeftButtonClickListener((view, rightAngleMessageDialog1) -> {
                    rightAngleMessageDialog1.handle(rightAngleMessageDialog1.getClass());
                    ToastKit.showShort(appCompatActivity.getString(R.string.cancel));
                })
                .setOnRightAngleMessageDialogRightButtonClickListener((view, rightAngleMessageDialog12) -> {
                    rightAngleMessageDialog12.handle(rightAngleMessageDialog12.getClass());
                    ToastKit.showShortWithGravity(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10) + "||"
                            + CaesarCipherUtils.decrypt(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10), 10), Gravity.CENTER_VERTICAL);
                }).build();
        rightAngleMessageDialog.show();
    }

    /**
     * 直角消息对话框（有标题）
     *
     * @param appCompatActivity 活动
     */
    public void rightAngleMessageDialogWithTitle(AppCompatActivity appCompatActivity) {
        RightAngleMessageDialog rightAngleMessageDialog = new RightAngleMessageDialog.Builder(appCompatActivity, 0)
                .setTitle(appCompatActivity.getString(R.string.messageDialog))
                .setTitleColor(ContextCompat.getColor(appCompatActivity, R.color.fontInput))
                .setContent(appCompatActivity.getString(R.string.example))
                .setContentColor(ContextCompat.getColor(appCompatActivity, R.color.fontHint))
                .setContentHorizontalCenter()
                .setLeftButtonText(appCompatActivity.getString(R.string.cancel))
                .setRightButtonText(appCompatActivity.getString(R.string.ensure))
                .setRightButtonDefaultSelect()
                .setOnRightAngleMessageDialogLeftButtonClickListener((view, rightAngleMessageDialog1) -> {
                    rightAngleMessageDialog1.handle(rightAngleMessageDialog1.getClass());
                    ToastKit.showLong(appCompatActivity.getString(R.string.cancel));
                })
                .setOnRightAngleMessageDialogRightButtonClickListener((view, rightAngleMessageDialog12) -> {
                    rightAngleMessageDialog12.handle(rightAngleMessageDialog12.getClass());
                    ToastKit.showLongWithGravity(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10) + " || "
                            + CaesarCipherUtils.decrypt(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10), 10), Gravity.CENTER_VERTICAL);
                }).build();
        rightAngleMessageDialog.show();
    }

    /**
     * 圆角消息对话框（无标题）
     *
     * @param appCompatActivity 活动
     */
    public void roundCornerMessageDialogWithNoTitle(AppCompatActivity appCompatActivity) {
        RoundCornerMessageDialog roundCornerMessageDialog = new RoundCornerMessageDialog.Builder(appCompatActivity, 0)
                .setTitle(null)
                .setTitleColor(ContextCompat.getColor(appCompatActivity, R.color.fontInput))
                .setContent(appCompatActivity.getString(R.string.example))
                .setContentColor(ContextCompat.getColor(appCompatActivity, R.color.fontHint))
                .setContentHorizontalCenter()
                .setLeftButtonText(appCompatActivity.getString(R.string.cancel))
                .setRightButtonText(appCompatActivity.getString(R.string.ensure))
                .setLeftButtonDefaultSelect()
                .setOnRoundCornerMessageDialogLeftButtonClickListener((view, roundCornerMessageDialog1) -> {
                    roundCornerMessageDialog1.handle(roundCornerMessageDialog1.getClass());
                    ToastKit.showShort(appCompatActivity.getString(R.string.cancel));
                })
                .setOnRoundCornerMessageDialogRightButtonClickListener((view, roundCornerMessageDialog12) -> {
                    roundCornerMessageDialog12.handle(roundCornerMessageDialog12.getClass());
                    ToastKit.showShort(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10) + "||"
                            + CaesarCipherUtils.decrypt(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10), 10));
                }).build();
        roundCornerMessageDialog.show();
    }

    /**
     * 圆角消息对话框（有标题）
     *
     * @param appCompatActivity 活动
     */
    public void roundCornerMessageDialogWithTitle(AppCompatActivity appCompatActivity) {
        RoundCornerMessageDialog roundCornerMessageDialog = new RoundCornerMessageDialog.Builder(appCompatActivity, 0)
                .setTitle(appCompatActivity.getString(R.string.messageDialog))
                .setTitleColor(ContextCompat.getColor(appCompatActivity, R.color.fontInput))
                .setContent(appCompatActivity.getString(R.string.example))
                .setContentColor(ContextCompat.getColor(appCompatActivity, R.color.fontHint))
                .setContentHorizontalCenter()
                .setLeftButtonText(appCompatActivity.getString(R.string.cancel))
                .setRightButtonText(appCompatActivity.getString(R.string.ensure))
                .setRightButtonDefaultSelect()
                .setOnRoundCornerMessageDialogLeftButtonClickListener((view, roundCornerMessageDialog1) -> {
                    roundCornerMessageDialog1.handle(roundCornerMessageDialog1.getClass());
                    ToastKit.showShort(appCompatActivity.getString(R.string.cancel));
                })
                .setOnRoundCornerMessageDialogRightButtonClickListener((view, roundCornerMessageDialog12) -> {
                    roundCornerMessageDialog12.handle(roundCornerMessageDialog12.getClass());
                    ToastKit.showShort(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10) + "||"
                            + CaesarCipherUtils.decrypt(CaesarCipherUtils.encrypt(appCompatActivity.getString(R.string.ensure), 10), 10));
                }).build();
        roundCornerMessageDialog.setCancelable(false);
        roundCornerMessageDialog.show();
    }

    /**
     * 普通加载对话框
     *
     * @param appCompatActivity 活动
     */
    public void commonLoadingDialog(AppCompatActivity appCompatActivity) {
        CommonLoadingDialog commonLoadingDialog = new CommonLoadingDialog.Builder(appCompatActivity, 0)
                .setHint(appCompatActivity.getString(R.string.loading))
                .setOnBackPressedListener(() -> ToastKit.showShort(SignUtils.getSignMd5Hex(appCompatActivity, "com.example.chaos") + "||" +
                        SignUtils.getSignSha256Hex(appCompatActivity, "com.example.chaos"))).build();
        commonLoadingDialog.setCancelable(false);
        commonLoadingDialog.show();
    }

    /**
     * 可取消加载对话框
     *
     * @param appCompatActivity 活动
     */
    public void canCancelLoadingDialog(AppCompatActivity appCompatActivity) {
        CanCancelLoadingDialog canCancelLoadingDialog = new CanCancelLoadingDialog.Builder(appCompatActivity, 0)
                .setHint(appCompatActivity.getString(R.string.loading))
                .setOnClickToCloseListener(() -> ToastKit.showShort("clickToClose"))
                .setOnDialogCloseListener(() -> ToastKit.showShort("dialogClose"))
                .setOnBackPressedListener(() -> ToastKit.showShort("backPressed")).build();
        canCancelLoadingDialog.setCancelable(false);
        canCancelLoadingDialog.show();
    }
}
