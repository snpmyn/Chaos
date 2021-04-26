package com.chaos.util.java.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import value.UtilMagic;

/**
 * Created on 2018/12/21.
 *
 * @author 郑少鹏
 * @desc ViewUtils
 */
public class ViewUtils {
    private static boolean alreadyClick;
    private static long clickTime;

    /**
     * 显示视图
     *
     * @param view 视图
     */
    public static void showView(@NotNull View view) {
        int visibility = view.getVisibility();
        if ((visibility == View.GONE) || (visibility == View.INVISIBLE)) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏视图
     *
     * @param view  视图
     * @param state 状态
     */
    public static void hideView(@NotNull View view, int state) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(state);
        }
    }

    /**
     * 视图高
     *
     * @param view 视图
     * @return 视图高
     */
    public static int viewHeight(@NotNull View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 视图宽
     *
     * @param view 视图
     * @return 视图宽
     */
    public static int viewWidth(@NotNull View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    /**
     * 设布局高
     *
     * @param view   视图
     * @param height 高
     */
    public static void setViewHeight(@NotNull View view, int height) {
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
        if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 双重点击检测
     *
     * @param view                  视图
     * @param onDoubleClickListener 双重点击监听
     */
    public static void doubleClickCheck(@NotNull View view, final OnDoubleClickListener onDoubleClickListener) {
        view.setOnClickListener(view1 -> {
            if (alreadyClick) {
                if (((System.currentTimeMillis() - clickTime) < UtilMagic.INT_TWO_HUNDRED) && (null != onDoubleClickListener)) {
                    onDoubleClickListener.onDoubleClick();
                }
                alreadyClick = false;
            } else {
                clickTime = System.currentTimeMillis();
                alreadyClick = true;
            }
        });
    }

    /**
     * 双重点击监听
     */
    public interface OnDoubleClickListener {
        /**
         * 双重点击
         */
        void onDoubleClick();
    }
}
