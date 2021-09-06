package com.chaos.widget.keyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;

import com.chaos.util.java.keyboard.KeyboardUtils;
import com.chaos.widget.R;

import value.WidgetMagic;

/**
 * Created on 2021/7/13
 *
 * @author zsp
 * @desc 金额输入键盘
 */
public class MoneyInputEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnClickListener, MoneyInputKeyboardView.OnKeyboardListener, View.OnFocusChangeListener {
    private MoneyInputDialog moneyInputDialog;
    private View scrollView;
    private int scrollDy;
    private Scroller scroller;
    private String currency;
    private Dialog dialog;
    private float textSize = 0;

    public MoneyInputEditText(Context context) {
        this(context, null);
    }

    public MoneyInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.scroller = new Scroller(context);
        initView(context, attrs);
    }

    public MoneyInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        KeyboardUtils.closeKeyboard(context, this);
        KeyboardUtils.hideUnderline(this);
        moneyInputDialog = new MoneyInputDialog(context);
        setOnClickListener(this);
        setOnFocusChangeListener(this);
        moneyInputDialog.setCanceledOnTouchOutside(false);
        moneyInputDialog.setOnKeyboardListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoneyInputEditText);
        int left = typedArray.getInt(R.styleable.MoneyInputEditText_moneyInputEditTextMaxLeftNum, 10 + 1 + 1);
        int right = typedArray.getInt(R.styleable.MoneyInputEditText_moneyInputEditTextMaxRightNum, 2);
        currency = typedArray.getString(R.styleable.MoneyInputEditText_moneyInputEditTextCurrency);
        // 去光标
        setCursorVisible(false);
        moneyInputDialog.setMaxLeftNumber(left);
        moneyInputDialog.setMaxRightNumber(right);
        moneyInputDialog.moneyInputKeyboardView.setCurrency(currency);
        typedArray.recycle();
        setFocusable(true);
    }

    public void setTxtSize(float size) {
        textSize = size;
        setTextSize(size);
    }

    public String getInputMoney() {
        return moneyInputDialog.getInputMoney();
    }

    public void setInputMoney(String money) {
        moneyInputDialog.setInputMoney(money);
    }

    public void setMaxLeftNumber(int left) {
        moneyInputDialog.setMaxLeftNumber(left);
    }

    public void setMaxRightNumber(int right) {
        moneyInputDialog.setMaxRightNumber(right);
    }

    public void setScrollView(View view) {
        scrollView = view;
    }

    public void setScrollView(View view, Dialog dialog) {
        scrollView = view;
        this.dialog = dialog;
    }

    public void clearText() {
        moneyInputDialog.clear();
    }

    public void setKeyBoardListener(MoneyInputDialog.OnKeyboardListener listener) {
        moneyInputDialog.setOnKeyboardListener(listener);
    }

    public void setOnKeyboardCompleteListener(MoneyInputDialog.OnKeyboardCompleteListener listener) {
        moneyInputDialog.setOnKeyboardCompleteListener(listener);
    }

    public void setCanCanceled(boolean canCanceled) {
        moneyInputDialog.setCanCancel(canCanceled);
    }

    public void setKeyBackListener(MoneyInputDialog.OnKeyBackListener onKeyBackListener) {
        moneyInputDialog.setOnKeyBackListener(onKeyBackListener);
    }

    public void showInputDialog() {
        moneyInputDialog.setFilterZero(false);
        moneyInputDialog.showDialog();
    }

    public void cancel() {
        if ((null != moneyInputDialog) && moneyInputDialog.isShowing()) {
            moneyInputDialog.cancel();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        requestFocus();
        moneyInputDialog.showDialog();
        moneyInputDialog.setFilterZero(false);
        String formatMoney = moneyInputDialog.getFormatMoney();
        if (textSize != 0.0) {
            setTextSize(textSize);
        } else {
            if (formatMoney.length() >= WidgetMagic.INT_TEN) {
                setTextSize(30);
            } else {
                setTextSize(48);
            }
        }
        if (TextUtils.isEmpty(formatMoney)) {
            setText(formatMoney);
        } else {
            setText("￥" + formatMoney);
        }
        if (isDialog()) {
            int keyBoardDialogHeight = moneyInputDialog.getDialogHeight();
            dialogScrollUp(getScrollerY(keyBoardDialogHeight));
        } else {
            scrollUp();
        }
    }

    private boolean isDialog() {
        return null != dialog;
    }

    private void scrollUp() {
        if (null != scrollView) {
            int[] location = new int[2];
            int dialogHeight, inputViewHeight, screenHeight;
            getLocationOnScreen(location);
            dialogHeight = moneyInputDialog.getDialogHeight();
            inputViewHeight = location[1] + getHeight();
            screenHeight = getResources().getDisplayMetrics().heightPixels;
            scrollDy = (inputViewHeight + dialogHeight + 20) - screenHeight;
            if (scrollDy > 0) {
                scroller.startScroll(0, 0, 0, scrollDy, 500);
                postInvalidate();
            }
        }
    }

    private void scrollDown() {
        if (null != scrollView) {
            scroller.startScroll(0, scrollDy, 0, -scrollDy, 500);
            postInvalidate();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            scrollView.scrollTo(x, y);
            postInvalidate();
        }
        super.computeScroll();
    }

    public void setCurrency(String currency) {
        moneyInputDialog.moneyInputKeyboardView.setCurrency(currency);
        this.currency = currency;
    }

    private int getScrollerY(int keyBoardHeight) {
        int scrollDy;
        int inputViewHeight, screenHeight;
        int[] location = new int[2];
        scrollView.getLocationOnScreen(location);
        inputViewHeight = location[1] + scrollView.getHeight();
        screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        scrollDy = ((inputViewHeight + keyBoardHeight) - screenHeight);
        return scrollDy;
    }

    public void dialogScrollUp(int scrollerY) {
        if ((scrollerY > 0) && (null != dialog.getWindow())) {
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = -scrollerY;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    public void dialogScrollDown() {
        if (null != dialog.getWindow()) {
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = 0;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onNumberChange(String number) {
        moneyInputDialog.setFilterZero(false);
        String formatMoney = moneyInputDialog.getFormatMoney();
        if (textSize != 0.0) {
            setTextSize(textSize);
        } else {
            if (formatMoney.length() >= WidgetMagic.INT_TEN) {
                setTextSize(30);
            } else {
                setTextSize(48);
            }
        }
        if (TextUtils.isEmpty(formatMoney)) {
            setText(formatMoney);
        } else {
            setText("￥" + formatMoney);
        }
    }

    @Override
    public void onKeyboardCancel(String formatNumber) {
        if (isDialog()) {
            dialogScrollDown();
        } else {
            scrollDown();
        }
    }

    @Override
    public void onKeyboardComplete(String formatNumber) {
        if (null != moneyInputDialog.getOnKeyboardCompleteListener()) {
            moneyInputDialog.getOnKeyboardCompleteListener().onKeyboardComplete(moneyInputDialog.getInputMoney2());
        }
        if (isDialog()) {
            dialogScrollDown();
        } else {
            scrollDown();
        }
    }
}

