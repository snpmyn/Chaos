package com.chaos.fairy.widget.adapter.listener;

import android.view.View;

/**
 * Created on 2020-09-21
 *
 * @author zsp
 * @desc 条目点击监听
 */
public interface OnItemClickListener {
    /**
     * 条目点击
     *
     * @param view     视图
     * @param position 位置
     */
    void onItemClick(View view, int position);
}
