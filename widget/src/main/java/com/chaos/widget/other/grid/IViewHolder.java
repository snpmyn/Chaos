package com.chaos.widget.other.grid;

import android.view.View;

/**
 * Created on 2020-09-23
 *
 * @author zsp
 * @desc 视图持有者接口
 */
public interface IViewHolder {
    /**
     * 创建内容视图
     *
     * @param position 位置
     * @return 内容视图
     */
    View createContentView(int position);

    /**
     * 获取页面数量
     *
     * @return 页面数量
     */
    int getPagerCount();
}
