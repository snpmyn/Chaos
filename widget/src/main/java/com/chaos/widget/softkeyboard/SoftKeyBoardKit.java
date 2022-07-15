package com.chaos.widget.softkeyboard;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import value.WidgetMagic;

/**
 * Created on 2022/7/10
 *
 * @author zsp
 * @desc 软键盘监配套元件
 */
public class SoftKeyBoardKit {
    /**
     * 根视图
     */
    private static View rootView;
    /**
     * 根视图可见高
     */
    private static int rootViewVisibleHeight;

    /**
     * 执行
     *
     * @param appCompatActivity            活动
     * @param onSoftKeyBoardChangeListener 软键盘变化监听
     */
    public static void execute(@NonNull AppCompatActivity appCompatActivity, final OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        rootView = appCompatActivity.getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            int visibleHeight = rect.height();
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight;
                return;
            }
            if (rootViewVisibleHeight == visibleHeight) {
                return;
            }
            if ((rootViewVisibleHeight - visibleHeight) > WidgetMagic.INT_TWO_HUNDRED) {
                if (null != onSoftKeyBoardChangeListener) {
                    onSoftKeyBoardChangeListener.softKeyBoardShow();
                }
                rootViewVisibleHeight = visibleHeight;
                return;
            }
            if ((visibleHeight - rootViewVisibleHeight) > WidgetMagic.INT_TWO_HUNDRED) {
                if (null != onSoftKeyBoardChangeListener) {
                    onSoftKeyBoardChangeListener.softKeyBoardHide();
                }
                rootViewVisibleHeight = visibleHeight;
            }
        });
    }
}
