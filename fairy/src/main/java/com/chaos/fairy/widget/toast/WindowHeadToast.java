package com.chaos.fairy.widget.toast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaos.fairy.R;
import com.chaos.fairy.value.FairyMagic;
import com.chaos.fairy.widget.toast.listener.OnViewClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created on 2020-09-03
 *
 * @author zsp
 * @desc 窗体头部吐司
 */
public class WindowHeadToast implements View.OnTouchListener {
    private final Context context;
    private final String title;
    private final String content;
    private final Map<String, String> extraMap;
    private int downY;
    private int downX;
    private LinearLayout linearLayout;
    private WindowManager windowManager;
    private OnViewClickListener onViewClickListener;
    private final static int ANIMATION_DURATION = 500;
    private final static int ANIMATION_DISMISS_DELAY = 3000;

    /**
     * constructor
     *
     * @param context  上下文
     * @param title    标题
     * @param content  内容
     * @param extraMap 额外数据
     */
    public WindowHeadToast(Context context, String title, String content, Map<String, String> extraMap) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.extraMap = extraMap;
    }

    /**
     * 初始视图
     */
    @SuppressLint("ClickableViewAccessibility")
    private void stepUi() {
        // 视图
        View view = View.inflate(context, R.layout.window_head_toast, null);
        view.setOnClickListener(v -> {
            if (null != onViewClickListener) {
                onViewClickListener.onViewClick(extraMap);
                onViewClickListener = null;
            }
        });
        // 标题
        TextView windowHeadToastTvTitle = view.findViewById(R.id.windowHeadToastTvTitle);
        windowHeadToastTvTitle.setText(title);
        // 内容
        TextView windowHeadToastTvContent = view.findViewById(R.id.windowHeadToastTvContent);
        windowHeadToastTvContent.setText(content);
        // 窗体所添视图
        linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.addView(view);
        linearLayout.measure(0, 0);
        linearLayout.setOnTouchListener(this);
        // 窗体管理器
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        // 不同系统版本（界限 7.1.1）策略不一，判断版本并设type，否则会引起崩溃。
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wmParams.x = 0;
        wmParams.y = 0;
        // 影响吐司中布局消失时父控件和子控件消失时机一致性，比如设置 -1 后不同步。
        wmParams.format = -3;
        wmParams.alpha = 1.0f;
        wmParams.gravity = Gravity.TOP;
        wmParams.height = linearLayout.getMeasuredHeight();
        windowManager.addView(linearLayout, wmParams);
    }

    /**
     * 动画显示
     */
    private void animationShow() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", -linearLayout.getMeasuredHeight(), 0);
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.start();
    }

    /**
     * 动画消失
     */
    private void animationDismiss() {
        if (null == linearLayout || null == linearLayout.getParent()) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationY", linearLayout.getTranslationY(), -linearLayout.getMeasuredHeight());
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 须动画结束时移除视图从而避免下次因窗体管理器中已存在视图而导致卡死
                if (null != linearLayout && null != linearLayout.getParent()) {
                    windowManager.removeViewImmediate(linearLayout);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
    }

    /**
     * 显示
     */
    public void show() {
        stepUi();
        animationShow();
        new Handler(context.getMainLooper()).postDelayed(this::animationDismiss, ANIMATION_DISMISS_DELAY);
    }

    /**
     * 消失
     */
    public void dismiss() {
        animationDismiss();
    }

    /**
     * 设置视图点击监听
     *
     * @param onViewClickListeners 视图点击监听
     */
    public void setOnViewClickListener(OnViewClickListener onViewClickListeners) {
        this.onViewClickListener = onViewClickListeners;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, @NotNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getRawX();
                int currentY = (int) event.getRawY();
                if (Math.abs(currentX - downX) > FairyMagic.INT_FIFTY || Math.abs(currentY - downY) > FairyMagic.INT_FORTY) {
                    animationDismiss();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 到一定比例后松开手指关闭
                if (Math.abs(linearLayout.getTranslationY()) > linearLayout.getMeasuredHeight() / FairyMagic.FLOAT_ONE_DOT_FIVE) {
                    animationDismiss();
                } else {
                    linearLayout.setTranslationY(0);
                }
                break;
            default:
                break;
        }
        return true;
    }
}
