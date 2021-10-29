package com.chaos.widget.choose.pickerview.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.chaos.widget.R;
import com.chaos.widget.choose.pickerview.configure.PickerOptions;
import com.chaos.widget.choose.pickerview.listener.OnDismissListener;
import com.chaos.widget.choose.pickerview.util.PickerViewAnimateUtils;

/**
 * @decs: 精仿 iOS 之 PickerViewController 控件
 * @author: 郑少鹏
 * @date: 2018/4/3 19:18
 */
public class BasePickerView {
    private final Context context;
    private final int animGravity = Gravity.BOTTOM;
    ViewGroup contentContainer;
    PickerOptions pickerOptions;
    /**
     * 通点 View 弹出
     */
    View clickView;
    /**
     * 附加 View 之根 View
     */
    private ViewGroup rootView;
    /**
     * 附加 Dialog 之根 View
     */
    private ViewGroup dialogView;
    private OnDismissListener onDismissListener;
    private boolean dismissing;
    private Animation outAnim;
    private Animation inAnim;
    private boolean areShowing;
    private Dialog mDialog;
    private boolean areAnim = true;
    /**
     * Called when the user touch on black overlay, in order to dismiss the widget.dialog.
     */
    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onCancelableTouchListener = (v, event) -> {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            dismiss();
        }
        return false;
    };
    private final View.OnKeyListener onKeyBackListener = (v, keyCode, event) -> {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == MotionEvent.ACTION_DOWN) && areShowing()) {
            dismiss();
            return true;
        }
        return false;
    };

    BasePickerView(Context context) {
        this.context = context;
    }

    @SuppressLint("InflateParams")
    void initViews() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (areDialog()) {
            // 对话框模式
            dialogView = (ViewGroup) layoutInflater.inflate(R.layout.base_picker_view, null, false);
            // 界面背景透明
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            // 真正加载选择器之父布局
            contentContainer = dialogView.findViewById(R.id.basePickerViewFlTwo);
            // 默左右距屏 30
            // 自定（注销并布局设）
            /*params.leftMargin = 30;*/
            /*params.rightMargin = 30;*/
            contentContainer.setLayoutParams(params);
            // 创对话框
            createDialog();
            // 背景设点击事件（点内容外关界面）
            dialogView.setOnClickListener(view -> dismiss());
        } else {
            // 仅显屏幕下方
            // decorView 是 activity 之根 View（含 contentView 和 titleView）
            if (pickerOptions.decorView == null) {
                pickerOptions.decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
            }
            // 控件添到 decorView 中
            rootView = (ViewGroup) layoutInflater.inflate(R.layout.base_picker_view, pickerOptions.decorView, false);
            rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (pickerOptions.backgroundId != -1) {
                rootView.setBackgroundColor(pickerOptions.backgroundId);
            }
            // 真正加载时间选取器之父布局
            contentContainer = rootView.findViewById(R.id.basePickerViewFlTwo);
            contentContainer.setLayoutParams(params);
        }
        setKeyBackCancelable();
    }

    void initAnim() {
        this.inAnim = getInAnimation();
        this.outAnim = getOutAnimation();
    }

    /**
     * 显示
     *
     * @param view    通哪 View 弹出
     * @param areAnim 动画效果
     */
    public void show(View view, boolean areAnim) {
        this.clickView = view;
        this.areAnim = areAnim;
        show();
    }

    public void show(boolean areAnim) {
        this.areAnim = areAnim;
        show();
    }

    public void show(View view) {
        this.clickView = view;
        show();
    }

    /**
     * 添 View 到根视图
     */
    public void show() {
        if (areDialog()) {
            showDialog();
        } else {
            if (areShowing()) {
                return;
            }
            areShowing = true;
            onAttached(rootView);
            rootView.requestFocus();
        }
    }

    /**
     * show 时调
     *
     * @param view 该 View
     */
    private void onAttached(View view) {
        pickerOptions.decorView.addView(view);
        if (areAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }

    /**
     * 检测该 View 添到根视图
     *
     * @return 视图已存该 View 返 true
     */
    public boolean areShowing() {
        return !areDialog() && ((null != rootView.getParent()) || areShowing);
    }

    public void dismiss() {
        if (areDialog()) {
            dismissDialog();
        } else {
            if (dismissing) {
                return;
            }
            if (areAnim) {
                // 消失动画
                outAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dismissImmediately();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                contentContainer.startAnimation(outAnim);
            } else {
                dismissImmediately();
            }
            dismissing = true;
        }
    }

    private void dismissImmediately() {
        pickerOptions.decorView.post(() -> {
            // 从根视图移除
            pickerOptions.decorView.removeView(rootView);
            areShowing = false;
            dismissing = false;
            if (null != onDismissListener) {
                onDismissListener.onDismiss(BasePickerView.this);
            }
        });
    }

    private Animation getInAnimation() {
        int animationResource = PickerViewAnimateUtils.getAnimationResource(this.animGravity, true);
        return AnimationUtils.loadAnimation(context, animationResource);
    }

    private Animation getOutAnimation() {
        int animationResource = PickerViewAnimateUtils.getAnimationResource(this.animGravity, false);
        return AnimationUtils.loadAnimation(context, animationResource);
    }

    public BasePickerView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    private void setKeyBackCancelable() {
        ViewGroup view;
        if (areDialog()) {
            view = dialogView;
        } else {
            view = rootView;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(onKeyBackListener);
    }

    void setOutSideCancelable(boolean cancelable) {
        if (null != rootView) {
            View view = rootView.findViewById(R.id.basePickerViewFl);
            if (cancelable) {
                view.setOnTouchListener(onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }
    }

    /**
     * 对话框模式可点外部取消
     */
    void setDialogOutSideCancelable() {
        if (null != mDialog) {
            mDialog.setCancelable(pickerOptions.cancelable);
        }
    }

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }

    private void createDialog() {
        if (null != dialogView) {
            mDialog = new Dialog(context, R.style.PickerViewStyle);
            // 不可点外 back 取消
            mDialog.setCancelable(pickerOptions.cancelable);
            mDialog.setContentView(dialogView);
            Window dialogWindow = mDialog.getWindow();
            if (null != dialogWindow) {
                dialogWindow.setWindowAnimations(R.style.PickerViewAnimationStyle);
                // 可改 Bottom
                dialogWindow.setGravity(Gravity.CENTER);
            }
            mDialog.setOnDismissListener(dialog -> {
                if (null != onDismissListener) {
                    onDismissListener.onDismiss(BasePickerView.this);
                }
            });
        }
    }

    private void showDialog() {
        if (null != mDialog) {
            mDialog.show();
        }
    }

    private void dismissDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    public boolean areDialog() {
        return false;
    }
}
