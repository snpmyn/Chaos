package com.chaos.widget.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.chaos.widget.R;

import timber.log.Timber;

/**
 * Created on 2021/7/13
 *
 * @author zsp
 * @desc 金额输入对话框
 */
public class MoneyInputDialog extends Dialog {
    public MoneyInputKeyboardView moneyInputKeyboardView;
    private boolean canCancel = true;
    private boolean filterZeroWhenInput = true;
    private OnKeyBackListener onKeyBackListener;
    private OnKeyboardListener onKeyboardListener;
    private OnKeyboardCompleteListener onKeyboardCompleteListener;

    public MoneyInputDialog(Context context) {
        super(context, R.style.MoneyInputDialogStyle);
        moneyInputKeyboardView = new MoneyInputKeyboardView(context);
        setOnCancelListener(dialog -> {
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyboardDismissBeforeFormat();
            }
            moneyInputKeyboardView.setFilterZero(false);
            String formatNumber = moneyInputKeyboardView.getFormatNumber();
            moneyInputKeyboardView.getOnKeyboardListener().onKeyboardCancel(formatNumber);
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyboardDismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(moneyInputKeyboardView.getKeyBoardView());
        setCanceledOnTouchOutside(canCancel);
        Window window = getWindow();
        if (null != window) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = 560;
            layoutParams.gravity = Gravity.BOTTOM;
        }
    }

    public void setOnKeyboardListener(OnKeyboardListener onKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener;
    }

    public void setOnKeyBackListener(OnKeyBackListener onKeyBackListener) {
        this.onKeyBackListener = onKeyBackListener;
    }

    public OnKeyboardCompleteListener getOnKeyboardCompleteListener() {
        return onKeyboardCompleteListener;
    }

    public void setOnKeyboardCompleteListener(OnKeyboardCompleteListener onKeyboardCompleteListener) {
        this.onKeyboardCompleteListener = onKeyboardCompleteListener;
    }

    public String getInputMoney() {
        return moneyInputKeyboardView.getInputMoney();
    }

    public void setInputMoney(String money) {
        moneyInputKeyboardView.setInputMoney(money);
    }

    public String getInputMoney2() {
        return moneyInputKeyboardView.getInputMoneyWithTwelveLength();
    }

    public String getFormatMoney() {
        return moneyInputKeyboardView.getFormatNumber();
    }

    public void setFilterZero(boolean filterZero) {
        moneyInputKeyboardView.setFilterZero(filterZero);
    }

    public void setFilterZeroWhenInput(boolean filterZeroWhenInput) {
        this.filterZeroWhenInput = filterZeroWhenInput;
    }

    public void setOnKeyboardListener(MoneyInputKeyboardView.OnKeyboardListener onKeyboardListener) {
        moneyInputKeyboardView.setOnKeyboardListener(onKeyboardListener);
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        setCanceledOnTouchOutside(canCancel);
        if (!canCancel) {
            setOnKeyListener((dialog, keyCode, event) -> {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    if (null != onKeyBackListener) {
                        onKeyBackListener.keyBack();
                    }
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * 设金额左边最大位数
     */
    public void setMaxLeftNumber(int maxLeftNumber) {
        moneyInputKeyboardView.setMoneyLeftMaxNumber(maxLeftNumber);
    }

    /**
     * 设金额右边最大位数
     */
    public void setMaxRightNumber(int maxRightNumber) {
        moneyInputKeyboardView.setMoneyRightMaxNumber(maxRightNumber);
    }

    /**
     * 获取对话框高度
     */
    public int getDialogHeight() {
        float dpi = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpi * 261);
    }

    public void showDialog() {
        try {
            show();
            moneyInputKeyboardView.setFilterZero(filterZeroWhenInput);
            if (null != onKeyboardListener) {
                onKeyboardListener.onKeyboardShow();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public void clear() {
        moneyInputKeyboardView.clear();
    }

    /**
     * 键返监听
     */
    public interface OnKeyBackListener {
        /**
         * 键返
         */
        void keyBack();
    }

    /**
     * 键盘监听
     */
    public interface OnKeyboardListener {
        /**
         * 键盘显示
         */
        void onKeyboardShow();

        /**
         * 键盘消失
         */
        void onKeyboardDismiss();

        /**
         * 格式化前消失
         */
        void onKeyboardDismissBeforeFormat();
    }

    /**
     * 键盘完成监听
     */
    public interface OnKeyboardCompleteListener {
        /**
         * 键盘完成
         *
         * @param formatNumber 格式化金额
         */
        void onKeyboardComplete(String formatNumber);
    }
}

