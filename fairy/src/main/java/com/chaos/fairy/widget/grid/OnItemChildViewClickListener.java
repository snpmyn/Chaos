package com.chaos.fairy.widget.grid;

/**
 * Created on 2020-09-25
 *
 * @author zsp
 * @desc 条目子视图点击监听
 */
public interface OnItemChildViewClickListener<T extends IGrid> {
    /**
     * 条目子视图点击
     *
     * @param grid     网格
     * @param position 位置
     */
    void onItemChildViewClick(T grid, int position);
}
