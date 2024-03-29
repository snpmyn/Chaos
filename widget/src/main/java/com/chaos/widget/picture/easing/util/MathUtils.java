package com.chaos.widget.picture.easing.util;

import android.graphics.RectF;

import androidx.annotation.NonNull;

/**
 * @decs: MathUtils
 * @author: 郑少鹏
 * @date: 2019/10/26 16:43
 */
public final class MathUtils {
    /**
     * Truncates a float number {@code f} to {@code decimalPlaces}.
     *
     * @param f             The number to be truncated.
     * @param decimalPlaces The amount of decimals that {@code f} will be truncated to.
     * @return A truncated representation of {@code f}.
     */
    public static float truncate(float f, int decimalPlaces) {
        float decimalShift = (float) Math.pow(10, decimalPlaces);
        return Math.round(f * decimalShift) / decimalShift;
    }

    /**
     * Checks whether two {@link RectF} have the same aspect ratio.
     *
     * @param fRectOne The first rect.
     * @param fRectTwo The second rect.
     * @return {@code true} If both rectangles have the same aspect ratio, {@code false} otherwise.
     */
    public static boolean haveSameAspectRatio(RectF fRectOne, RectF fRectTwo) {
        // Reduces precision to avoid problems when comparing aspect ratios.
        float srcRectRatio = MathUtils.truncate(MathUtils.getRectRatio(fRectOne), 3);
        float dstRectRatio = MathUtils.truncate(MathUtils.getRectRatio(fRectTwo), 3);
        // Compares aspect ratios that allows for a tolerance range of [0, 0.01]
        return (Math.abs(srcRectRatio - dstRectRatio) <= 0.01F);
    }

    /**
     * Computes the aspect ratio of a given rect.
     *
     * @param rect The rect to have its aspect ratio computed.
     * @return The rect aspect ratio.
     */
    public static float getRectRatio(@NonNull RectF rect) {
        return rect.width() / rect.height();
    }
}
