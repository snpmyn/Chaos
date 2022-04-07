package com.chaos.widget.snackbar;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created on 2019/6/3.
 *
 * @author 郑少鹏
 * @desc Snackbar 配套元件
 */
public class SnackbarKit {
    private static Snackbar snackbar;

    /**
     * 通字符串序列创 Snackbar
     *
     * @param view          视图
     * @param charSequence  字符序列
     * @param areLengthLong 长否
     * @return Snackbar
     */
    private static Snackbar snackbarCreateByCharSequence(View view, CharSequence charSequence, boolean areLengthLong) {
        snackbar = Snackbar.make(view, charSequence, areLengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
        return snackbar;
    }

    /**
     * 通字符串序列创 Snackbar 并显
     *
     * @param view          视图
     * @param charSequence  字符序列
     * @param areLengthLong 长否
     */
    public static void snackbarCreateByCharSequenceAndShow(View view, CharSequence charSequence, boolean areLengthLong) {
        if ((null != snackbar) && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbarCreateByCharSequence(view, charSequence, areLengthLong).show();
    }

    /**
     * 通字符串序列创 Snackbar 附通字符串序列动作
     *
     * @param view                    视图
     * @param createCharSequence      创字符序列
     * @param areLengthLong           长否
     * @param actionCharSequence      动作字符序列
     * @param snackbarOnClickListener Snackbar 点监听
     * @return Snackbar
     */
    private static Snackbar snackbarCreateByCharSequenceWithActionByCharSequence(View view, CharSequence createCharSequence, boolean areLengthLong, CharSequence actionCharSequence, SnackbarOnClickListener snackbarOnClickListener) {
        snackbar = snackbarCreateByCharSequence(view, createCharSequence, areLengthLong).setAction(actionCharSequence, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    /**
     * 通字符串序列创 Snackbar 附通字符串序列动作并显
     *
     * @param view                    视图
     * @param createCharSequence      创字符序列
     * @param areLengthLong           长否
     * @param actionCharSequence      动作字符序列
     * @param snackbarOnClickListener Snackbar 点监听
     */
    public static void snackbarCreateByCharSequenceWithActionByCharSequenceAndShow(View view, CharSequence createCharSequence, boolean areLengthLong, CharSequence actionCharSequence, SnackbarOnClickListener snackbarOnClickListener) {
        if ((null != snackbar) && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbarCreateByCharSequenceWithActionByCharSequence(view, createCharSequence, areLengthLong, actionCharSequence, snackbarOnClickListener).show();
    }

    /**
     * 通资源 ID 创 Snackbar
     *
     * @param view          视图
     * @param resId         资源 ID
     * @param areLengthLong 长否
     * @return Snackbar
     */
    private static Snackbar snackbarCreateByResId(View view, int resId, boolean areLengthLong) {
        snackbar = Snackbar.make(view, resId, areLengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
        return snackbar;
    }

    /**
     * 通资源 ID 创 Snackbar 并显
     *
     * @param view          视图
     * @param resId         资源 ID
     * @param areLengthLong 长否
     */
    public static void snackbarCreateByResIdAndShow(View view, int resId, boolean areLengthLong) {
        if ((null != snackbar) && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbarCreateByResId(view, resId, areLengthLong).show();
    }

    /**
     * 通资源 ID 创 Snackbar 附通资源 ID 动作
     *
     * @param view                    视图
     * @param createResId             创资源 ID
     * @param areLengthLong           长否
     * @param actionResId             动作资源 ID
     * @param snackbarOnClickListener Snackbar 点监听
     * @return Snackbar
     */
    private static Snackbar snackbarCreateByResIdWithActionByResId(View view, int createResId, boolean areLengthLong, int actionResId, SnackbarOnClickListener snackbarOnClickListener) {
        snackbar = snackbarCreateByResId(view, createResId, areLengthLong).setAction(actionResId, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    /**
     * 通资源 ID 创 Snackbar 附通资源 ID 动作并显
     *
     * @param view                    视图
     * @param createResId             创资源 ID
     * @param areLengthLong           长否
     * @param actionResId             动作资源 ID
     * @param snackbarOnClickListener Snackbar 点监听
     */
    public static void snackbarCreateByResIdWithActionByResIdAndShow(View view, int createResId, boolean areLengthLong, int actionResId, SnackbarOnClickListener snackbarOnClickListener) {
        if ((null != snackbar) && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbarCreateByResIdWithActionByResId(view, createResId, areLengthLong, actionResId, snackbarOnClickListener).show();
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
