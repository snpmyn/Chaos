package com.chaos.util.java.spannablestring;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;

import androidx.annotation.NonNull;

/**
 * Created on 2021/10/12
 *
 * @author zsp
 * @desc SpannableStringUtils
 */
public class SpannableStringUtils {
    /**
     * 居中图像 SpannableString
     *
     * @param content    内容
     * @param startIndex 开始下标
     * @param endIndex   结束下标
     * @param width      宽
     * @param height     高
     * @param drawable   位图
     * @return 居中图像 SpannableString
     */
    @NonNull
    public static SpannableString centerImageSpannableString(String content, int startIndex, int endIndex, int width, int height, @NonNull Drawable drawable) {
        drawable.setBounds(0, 0, (width == 0) ? drawable.getIntrinsicWidth() / 2 : width, (height == 0) ? drawable.getIntrinsicHeight() / 2 : height);
        CenterImageSpan centerImageSpan = new CenterImageSpan(drawable);
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(centerImageSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
