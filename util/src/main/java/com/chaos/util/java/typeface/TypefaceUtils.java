package com.chaos.util.java.typeface;

import android.content.Context;
import android.graphics.Typeface;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2021/4/15
 *
 * @author zsp
 * @desc Typeface 工具类
 */
public class TypefaceUtils {
    /**
     * SansBold
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansBold(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
    }

    /**
     * SansBoldItalic
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansBoldItalic(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-BoldItalic.ttf");
    }

    /**
     * SansExtraBold
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansExtraBold(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-ExtraBold.ttf");
    }

    /**
     * SansExtraBoldItalic
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansExtraBoldItalic(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-ExtraBoldItalic.ttf");
    }

    /**
     * SansItalic
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansItalic(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Italic.ttf");
    }

    /**
     * SansLight
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansLight(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
    }

    /**
     * SansLightItalic
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansLightItalic(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-LightItalic.ttf");
    }

    /**
     * SansRegular
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansRegular(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    /**
     * SansSemibold
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansSemibold(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Semibold.ttf");
    }

    /**
     * SansSemiboldItalic
     *
     * @param context 上下文
     * @return Typeface
     */
    public static Typeface sansSemiboldItalic(@NotNull Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-SemiboldItalic.ttf");
    }
}
