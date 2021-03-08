package com.chaos.widget.other.grid;

import androidx.annotation.DrawableRes;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @des 网格接口
 */
public interface IGrid {
    /**
     * 获取标题
     *
     * @return 标题
     */
    String getTitle();

    /**
     * 获取整型图标 ID
     *
     * @return 整型图标 ID
     */
    @DrawableRes
    int getIntIconId();

    /**
     * 获取字符串图标 ID
     *
     * @return 字符串图标 ID
     */
    String getStringIconId();
}
