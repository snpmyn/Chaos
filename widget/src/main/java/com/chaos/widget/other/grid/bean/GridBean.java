package com.chaos.widget.other.grid.bean;

import androidx.annotation.DrawableRes;

import com.chaos.widget.other.grid.IGrid;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc 网格
 */
public class GridBean implements IGrid {
    private final String id;
    private final String title;
    @DrawableRes
    private final int intIconId;
    private final String stringIconId;
    private final String description;

    /**
     * constructor
     *
     * @param id           ID
     * @param title        标题
     * @param intIconId    整型资源 ID
     * @param stringIconId 字符串型资源 ID
     * @param description  描述
     */
    public GridBean(String id, String title, int intIconId, String stringIconId, String description) {
        this.id = id;
        this.title = title;
        this.intIconId = intIconId;
        this.stringIconId = stringIconId;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    /**
     * 获取标题
     *
     * @return 标题
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * 获取整型图标 ID
     *
     * @return 整型图标 ID
     */
    @Override
    public int getIntIconId() {
        return intIconId;
    }

    /**
     * 获取字符串图标 ID
     *
     * @return 字符串图标 ID
     */
    @Override
    public String getStringIconId() {
        return stringIconId;
    }

    public String getDescription() {
        return description;
    }
}

