package com.chaos.widget.snackbar;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2019/6/3.
 *
 * @author 郑少鹏
 * @desc Snackbar 配套元件
 */
public class SnackbarKit {
    /**
     * 通字符串序列创 Snackbar
     *
     * @param view         视图
     * @param charSequence 字符序列
     * @param lengthLong   长否
     * @return Snackbar
     */
    public static @NotNull Snackbar snackbarCreateByCharSequence(View view, CharSequence charSequence, boolean lengthLong) {
        return Snackbar.make(view, charSequence, lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
    }

    /**
     * 通字符串序列创 Snackbar 附通字符串序列动作
     *
     * @param view                    视图
     * @param createCharSequence      创字符序列
     * @param lengthLong              长否
     * @param actionCharSequence      动作字符序列
     * @param snackbarOnClickListener Snackbar 点监听
     * @return Snackbar
     */
    public static @NotNull Snackbar snackbarCreateByCharSequenceWithActionByCharSequence(
            View view, CharSequence createCharSequence, boolean lengthLong,
            CharSequence actionCharSequence, SnackbarOnClickListener snackbarOnClickListener) {
        Snackbar snackbar = snackbarCreateByCharSequence(view, createCharSequence, lengthLong);
        snackbar.setAction(actionCharSequence, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    /**
     * 通资源 ID 创 Snackbar
     *
     * @param view       视图
     * @param resId      资源 ID
     * @param lengthLong 长否
     * @return Snackbar
     */
    public static @NotNull Snackbar snackbarCreateByResId(View view, int resId, boolean lengthLong) {
        return Snackbar.make(view, resId, lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
    }

    /**
     * 通资源 ID 创 Snackbar 附通资源 ID 动作
     *
     * @param view                    视图
     * @param createResId             创资源 ID
     * @param lengthLong              长否
     * @param actionResId             动作资源 ID
     * @param snackbarOnClickListener Snackbar 点监听
     * @return Snackbar
     */
    public static @NotNull Snackbar snackbarCreateByResIdWithActionByResId(
            View view, int createResId, boolean lengthLong,
            int actionResId, SnackbarOnClickListener snackbarOnClickListener) {
        Snackbar snackbar = snackbarCreateByResId(view, createResId, lengthLong);
        snackbar.setAction(actionResId, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    public interface SnackbarOnClickListener {
        /**
         * Snackbar 点监听
         *
         * @param view     视图
         * @param snackbar Snackbar
         */
        void snackbarOnClickListener(View view, Snackbar snackbar);
    }
}
