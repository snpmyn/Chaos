package widget.dialog.kit;

import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chaos.util.java.data.CaesarCipherUtils;
import com.chaos.util.java.sign.SignUtils;
import com.chaos.util.java.toast.ToastKit;
import com.chaos.widget.dialog.bocdialog.kit.DialogKit;
import com.chaos.widget.dialog.bocdialog.lottie.bean.DialogLottieAnimationEnum;
import com.chaos.widget.dialog.bocdialog.message.RightAngleMessageDialog;
import com.chaos.widget.dialog.bocdialog.message.RoundCornerMessageDialog;
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
        DialogKit.getInstance(appCompatActivity).commonLoading(appCompatActivity.getString(R.string.loading), () -> {
            DialogKit.getInstance(appCompatActivity).end();
            ToastKit.showShort(SignUtils.getSignMd5Hex(appCompatActivity, "com.example.chaos") + "||" +
                    SignUtils.getSignSha256Hex(appCompatActivity, "com.example.chaos"));
        });
    }

    /**
     * 可取消加载对话框
     *
     * @param appCompatActivity 活动
     */
    public void canCancelLoadingDialog(AppCompatActivity appCompatActivity) {
        DialogKit.getInstance(appCompatActivity).canCancelLoading(appCompatActivity.getString(R.string.loading), () -> ToastKit.showShort("clickToClose"), () -> ToastKit.showShort("dialogClose"), () -> ToastKit.showShort("backPressed"));
    }

    /**
     * LottieAnimationView 加载对话框
     *
     * @param appCompatActivity 活动
     */
    public void lottieAnimationViewLoadingDialog(AppCompatActivity appCompatActivity) {
        DialogKit.getInstance(appCompatActivity).lottieAnimationViewDialog(DialogLottieAnimationEnum.LOADING_ONE, "chaos", true, () -> {
            DialogKit.getInstance(appCompatActivity).end();
            ToastKit.showShort(SignUtils.getSignMd5Hex(appCompatActivity, "com.example.chaos") + "||" +
                    SignUtils.getSignSha256Hex(appCompatActivity, "com.example.chaos"));
        });
    }

    /**
     * LottieAnimationView 其它对话框
     *
     * @param appCompatActivity 活动
     */
    public void lottieAnimationViewOtherDialog(AppCompatActivity appCompatActivity) {
        DialogKit.getInstance(appCompatActivity).lottieAnimationViewDialog(DialogLottieAnimationEnum.SECOND_SET_RESULT_SUCCESS, null, false, () -> ToastKit.showShort(SignUtils.getSignMd5Hex(appCompatActivity, "com.example.chaos") + "||" +
                SignUtils.getSignSha256Hex(appCompatActivity, "com.example.chaos")));
    }
}
