package com.chaos.fairy.utils.dimension;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2020-08-31
 *
 * @author zsp
 * @desc 尺寸工具类
 */
public class DimensionUtils {
    public static int dipToPx(@NotNull Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
