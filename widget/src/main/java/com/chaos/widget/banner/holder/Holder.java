package com.chaos.widget.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * @desc: 持有者
 * @author: zsp
 * @date: 2021/10/15 5:11 下午
 */
public interface Holder<T> {
    /**
     * 创视图
     *
     * @param context 上下文
     * @return 视图
     */
    View createView(Context context);

    /**
     * 更新 UI
     *
     * @param context  上下文
     * @param position 位置
     * @param data     数据
     */
    void updateUi(Context context, int position, T data);
}