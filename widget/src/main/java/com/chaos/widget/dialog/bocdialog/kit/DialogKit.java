package com.chaos.widget.dialog.bocdialog.kit;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.widget.dialog.bocdialog.base.BaseInstanceDialog;
import com.chaos.widget.dialog.bocdialog.loading.CanCancelLoadingDialog;
import com.chaos.widget.dialog.bocdialog.loading.CommonLoadingDialog;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnBackPressedListener;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnClickToCloseListener;
import com.chaos.widget.dialog.bocdialog.loading.listener.OnDialogCloseListener;
import com.chaos.widget.dialog.bocdialog.lottie.LottieAnimationViewDialog;
import com.chaos.widget.dialog.bocdialog.lottie.bean.DialogLottieAnimationEnum;

import java.lang.ref.WeakReference;

/**
 * Created on 2022/6/24
 *
 * @author zsp
 * @desc 对话框配套元件
 */
public class DialogKit {
    private static DialogKit instance;
    private static WeakReference<AppCompatActivity> weakReference;
    private BaseInstanceDialog baseInstanceDialog;

    public static DialogKit getInstance(AppCompatActivity appCompatActivity) {
        if (null == instance) {
            synchronized (DialogKit.class) {
                if (null == instance) {
                    instance = new DialogKit();
                }
            }
        }
        weakReference = new WeakReference<>(appCompatActivity);
        return instance;
    }

    /**
     * 普通加载
     *
     * @param hint                  提示
     * @param onBackPressedListener 回退按压监听
     */
    public void commonLoading(String hint, OnBackPressedListener onBackPressedListener) {
        baseInstanceDialog = new CommonLoadingDialog.Builder(weakReference.get(), 0)
                .setHint(hint)
                .setOnBackPressedListener(onBackPressedListener).build();
        baseInstanceDialog.setCancelable(false);
        baseInstanceDialog.show();
    }

    /**
     * 可取消加载
     *
     * @param hint                   提示
     * @param onClickToCloseListener 点击关闭监听
     * @param onDialogCloseListener  对话框关闭监听
     * @param onBackPressedListener  回退按压监听
     */
    public void canCancelLoading(String hint, OnClickToCloseListener onClickToCloseListener, OnDialogCloseListener onDialogCloseListener, OnBackPressedListener onBackPressedListener) {
        baseInstanceDialog = new CanCancelLoadingDialog.Builder(weakReference.get(), 0)
                .setHint(hint)
                .setOnClickToCloseListener(onClickToCloseListener)
                .setOnDialogCloseListener(onDialogCloseListener)
                .setOnBackPressedListener(onBackPressedListener).build();
        baseInstanceDialog.setCancelable(false);
        baseInstanceDialog.show();
    }

    /**
     * LottieAnimationView
     *
     * @param dialogLottieAnimationEnum 对话框 Lottie 动画枚举
     * @param loading                   加载否
     * @param onBackPressedListener     回退按压监听
     */
    public void lottieAnimationViewDialog(DialogLottieAnimationEnum dialogLottieAnimationEnum, boolean loading, OnBackPressedListener onBackPressedListener) {
        baseInstanceDialog = new LottieAnimationViewDialog.Builder(weakReference.get(), 0)
                .setAnimation(dialogLottieAnimationEnum)
                .setLoading(loading)
                .setOnBackPressedListener(onBackPressedListener).build();
        baseInstanceDialog.setCancelable(false);
        baseInstanceDialog.show();
    }

    /**
     * 取消加载
     */
    public void dismissLoading() {
        if (null != weakReference) {
            weakReference.clear();
        }
        if (null != baseInstanceDialog) {
            if (baseInstanceDialog.isShowing()) {
                baseInstanceDialog.dismiss();
            }
            baseInstanceDialog = null;
        }
    }
}
