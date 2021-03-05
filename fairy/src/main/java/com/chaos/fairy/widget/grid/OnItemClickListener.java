package com.chaos.fairy.widget.grid;

/**
 * Created on 2020-09-25
 *
 * @author zsp
 * @desc 条目点击监听
 */
public interface OnItemClickListener<T extends IGrid> {
    /**
     * 条目点击
     *
     * @param grid     网格
     * @param position 位置
     */
    void onItemClick(T grid, int position);
}
